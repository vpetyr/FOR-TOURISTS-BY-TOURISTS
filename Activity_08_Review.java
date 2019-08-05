package com.example.fortouristsbytourists;

import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_08_Review extends AppCompatActivity {
    String key;
    String uName;
    String review;
    String attrName; // attraction name/title
    float newRating = -1;
    DatabaseReference ref_attr, ref_rev, ref_users;
    Attractions attraction;
//    User user;
    Review oneReview;
//    ArrayList <User> userList = new ArrayList<>();
    ArrayList <Review> reviewList = new ArrayList<>();
    ArrayList <Attractions> attrList = new ArrayList<>();

    TextView textView0802, // userName
             textView0805, // attraction name/title
             textView0804; // category of attraction
    EditText editText0801; // write a review
    RatingBar ratingBar0801;
    Button btnLogout, btnAngry;  // btnAngry - for users review
    RecyclerView recView0806;
    int k = 0;
    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_08_review);

        btnLogout = findViewById(R.id.button0805);
        btnAngry = findViewById(R.id.button0806);       // collecting/create a review

        textView0802 = findViewById(R.id.textView0802); // userName
        recView0806 = findViewById(R.id.rec_review);    // reviewItem for users rating ---> made with recycler
        textView0805 = findViewById(R.id.textView0805); // Attraction Name
        textView0804 = findViewById(R.id.textView0804); // category of attraction

        editText0801 = findViewById(R.id.editText0801); //for a new review


        key = getIntent().getStringExtra("key");   // for database key
        uName = getIntent().getStringExtra("uName"); textView0802.setText(uName);
        attrName = getIntent().getStringExtra("attrTitle"); textView0805.setText(attrName);
        category = getIntent().getIntExtra("category",4);

        switch (category){    //attraction category (attractions, museums, parks)
            case 4: textView0804.setText("Attractions in London:");break;
            case 5: textView0804.setText("Museums in London:");break;
            case 6: textView0804.setText("Parks and Green Places:");break;
        }

        switch (category) {   //database choice
            case 4: ref_attr = FirebaseDatabase.getInstance().getReference("attractions"); break;  //DB attractions
            case 5: ref_attr = FirebaseDatabase.getInstance().getReference("museums"); break;  //DB museums
            case 6: ref_attr = FirebaseDatabase.getInstance().getReference("parks"); break;  //DB parks
        }
        ref_users = FirebaseDatabase.getInstance().getReference("users"); //DB users
        ref_rev = FirebaseDatabase.getInstance().getReference("reviews"); //DB reviews

        //selecting all reviews from the 'reviews' table for the attraction title field being chosen
        Query query1 = FirebaseDatabase.getInstance().getReference("reviews")
                .orderByChild("attrTitle")
                .equalTo(textView0805.getText().toString());
        query1.addListenerForSingleValueEvent(revListener);

        ratingBar0801 = findViewById(R.id.ratingBar0801);
        ratingBar0801.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                newRating = ratingBar0801.getRating();
                Toast myToast = Toast.makeText(getApplicationContext(),"Your rating is: " +newRating,Toast.LENGTH_LONG );
                myToast.setGravity(Gravity.CENTER,0,0);
                myToast.show();

            }
        });
    }

    public void angryButton(View v){  // write a review in DB (table reviews)
        boolean success = true;
        if (newRating == -1){  //if rating was not chosen
            success = false;
        }
        review = editText0801.getText().toString();
        if (review.length() == 0){  //if review was not filled up
            success = false;
        }
        if (success) {   //writing the review if the information is correct
            Review myReview = new Review(attrName, review, uName, newRating);
            //and put into DB
            String id = ref_rev.push().getKey();   //get a key from the review table
            ref_rev.child(id).setValue(myReview);  //write the review in review table
            editText0801.setText("");              // cleaning the editText field for the next review
            Toast.makeText(this, "Successfully added...", Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, "Invalid information...", Toast.LENGTH_LONG).show();
        }
    }

    public void logoutButton(View v){
        if (v.getId() == R.id.button0805) {
            Intent back2Activity01 = new Intent(this, Activity_01_Main.class);
            startActivity(back2Activity01);
        }

    }

    // next: transferring the review table from database in arrayList
    ValueEventListener revListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            reviewList.clear();
            for (DataSnapshot dss: dataSnapshot.getChildren()) {

                long n=dataSnapshot.getChildrenCount();
                oneReview = dss.getValue(Review.class);

                reviewList.add(oneReview);
                k++;
                if (k == n) {initRecyclerReview();} // when the whole database has been transferred ---> initialising the RecyclerView
                }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void initRecyclerReview(){  // initialising the RecyclerViewAdapter for the review list
        RecyclerView recyclerView = findViewById(R.id.rec_review);
        ReviewAdapter adapter = new ReviewAdapter(this, reviewList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}