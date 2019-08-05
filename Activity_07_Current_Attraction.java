package com.example.fortouristsbytourists;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Activity_07_Current_Attraction extends AppCompatActivity implements View.OnClickListener {
    // one activity for all three categories
    ImageView imageView0701, //image for the attraction
              logo_1; //imageView0702;
    TextView userName, //textView0701
             textView0702, //"Attractions in London" (Attractions, Museums, Parks)
             textView0703, // attraction name
             textView0704; // attraction description
    Button btn0701, // Logout
           btn0702, // Book a Tour
           btn0703; // Reviews
    Intent back2Activity01;
    String uName;
    String url, imageName, bookingURL;
    String key; // for the database key
    StorageReference sref; // Firebase picture-Database
    DatabaseReference ref;
    Attractions attraction = new Attractions();

    int category = 5; //4 - attractions, 5 - museums, 6 - parks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_07_current_attraction);

        back2Activity01=new Intent(this,Activity_01_Main.class);
        btn0701 = findViewById(R.id.button0701); btn0701.setOnClickListener(this);  // Logout
        btn0702 = findViewById(R.id.button0702); btn0702.setOnClickListener(this);  // Book a Tour
        btn0703 = findViewById(R.id.button0703); btn0703.setOnClickListener(this);  // Reviews
//        logo_1 = findViewById(R.id.imageView0702);
        imageView0701 = findViewById(R.id.imageView0701);  //image for the current attraction
        userName = findViewById(R.id.textView0701);
        textView0702 = findViewById(R.id.textView0702);  // "Attractions in London" (Attractions, Museums, Parks)
        textView0703 = findViewById(R.id.textView0703);  // attraction name
        textView0704 = findViewById(R.id.textView0704);textView0704.setMovementMethod(new ScrollingMovementMethod()); // attraction description

        Intent takeFromMemory = getIntent();
        uName = takeFromMemory.getStringExtra("uName");

        url = takeFromMemory.getStringExtra("url");
        imageName = takeFromMemory.getStringExtra("imageName");

        userName.setText(takeFromMemory.getStringExtra("uName"));
        category = takeFromMemory.getIntExtra("category", 4);

        switch (category){  //attraction category
            case 4: textView0702.setText("Attractions in London:");break;
            case 5: textView0702.setText("Museums in London:");break;
            case 6: textView0702.setText("Parks and Green Places:");break;
        }

        switch (category){  //database choice
            case 4: ref = FirebaseDatabase.getInstance().getReference("attractions"); break;
            case 5: ref = FirebaseDatabase.getInstance().getReference("museums"); break;
            case 6: ref = FirebaseDatabase.getInstance().getReference("parks"); break;
        }

        textView0703.setText(imageName);  // attraction name (title)
        sref = FirebaseStorage.getInstance().getReference("images"); // sref (from the StorageReference type) connection with the "images" storage
        key = imageName.replaceAll(" ","");  // preparing the key (for picture

        switch (category) {  //choice of object from the selected database by key
            case 4: ref = FirebaseDatabase.getInstance().getReference("attractions/" + key);break;
            case 5: ref = FirebaseDatabase.getInstance().getReference("museums/" + key);break;
            case 6: ref = FirebaseDatabase.getInstance().getReference("parks/" + key);break;
        }

        Picasso.get()                   // taking the picture
                .load(url)              // from FireDataBase
                .into(imageView0701);   // and put it into the Activity View

        DataSnapshot dataSnapshot;

        ref.addListenerForSingleValueEvent(new ValueEventListener() {   // get one only record from Database - description for the attraction
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                attraction = dataSnapshot.getValue(Attractions.class);
                String description = attraction.getDescription();
                bookingURL = attraction.getBookingURL();
                textView0704.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button0701: startActivity(back2Activity01); break;
            case R.id.button0702: bookATour(bookingURL); break;
            case R.id.button0703: makeReview(); break;
        }
    }

    public void bookATour(String bookingURL){
        if (attraction.isCanBook()) {       // if the attraction has web page (by admins)
            Intent nextPage = new Intent(this, Activity_10_www.class);
            nextPage.putExtra("bookingURL", bookingURL);
            startActivity(nextPage);
        }else {
            Toast myToast0 = Toast.makeText(getApplicationContext(),"There is no attraction URL", Toast.LENGTH_LONG );
            myToast0.setGravity(Gravity.CENTER,0,225);
            myToast0.show();
        }
    }

    public void makeReview(){
        Intent nextPage=new Intent(this, Activity_08_Review.class);
 //       nextPage.putExtra("key",buf);
        nextPage.putExtra("key",key); //new key for database (title)
        nextPage.putExtra("uName",uName);  //userName
        nextPage.putExtra("attrTitle", imageName);      //attraction Title
        nextPage.putExtra("category",category);
        startActivity(nextPage);
    }

}