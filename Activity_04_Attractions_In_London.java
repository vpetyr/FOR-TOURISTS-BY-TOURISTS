package com.example.fortouristsbytourists;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Activity_04_Attractions_In_London extends AppCompatActivity implements View.OnClickListener {

    Button btn0405; //Logout
    String uName;
    TextView userName;
    Intent intent01, intentView;
    int n; // number of an attraction
    int k = 0;
    DataSnapshot dataSnapshot;
    Attractions attraction;
    final List<Attractions> listAtr = new ArrayList<>(); // Attractions objects arrayList

    private static final String TAG = "Activity_04_Attractions"; // for testing in Logcat
    private ArrayList<String> mNames = new ArrayList<>();        // attraction title
    private ArrayList<String> mImageUrls = new ArrayList<>();    // arrayList with image urls
    private ArrayList<String> arrKey = new ArrayList<>();        // arrayList with database keys (attractions)
    private ArrayList<String> mLocation = new ArrayList<>();     // arrayList with attraction locations

    StorageReference sref;
    DatabaseReference ref;
    String url, description;

    Intent toAttraction07;
    String uName3;
    Integer category; // the category is the attraction type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_04_attractions_in_london);

        toAttraction07=new Intent(this,Activity_07_Current_Attraction.class);

        btn0405 = (Button) findViewById(R.id.button0405); btn0405.setOnClickListener(this); // ---> Logout

        userName = (TextView) findViewById(R.id.textView0401);
        Intent getMemory = getIntent();
        uName = getMemory.getStringExtra("uName2");
        userName.setText(uName); uName3 = uName;
        category = getMemory.getIntExtra("category",4);  // the category is the attraction type: 4 - Attractions

        sref = FirebaseStorage.getInstance().getReference("images");    // sref (from the StorageReference type) connection with the "images" storage
        ref = FirebaseDatabase.getInstance().getReference("attractions"); // ref (from the DatabaseReference type) connection with the "attractions" database

        ValueEventListener listener = new ValueEventListener() { // transferring the database to its corresponding arrayLists

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dss: dataSnapshot.getChildren()) {
                    attraction  = dss.getValue(Attractions.class);
                    listAtr.add(attraction);
                    mNames.add(attraction.getTitle());
                    mImageUrls.add(attraction.getImageURL());
                    mLocation.add(attraction.getLocation());
                    arrKey.add(dataSnapshot.getKey());  // arrayList <key> - for Attractions
                    url = listAtr.get(k).getImageURL();
                    long n2 = dataSnapshot.getChildrenCount();
                    k++;
                    if (k == n2) {          // when the whole database has been transferred
                        initRecyclerView(uName3); // initialising the RecyclerView
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
            private void printResult() {
                Log.d("arrID", listAtr.get(k).getImageURL());
            }

        };

        ref.addListenerForSingleValueEvent(listener);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button0405) logout01();
    }

    public void logout01() {
        intent01 = new Intent(this, Activity_01_Main.class);
        startActivity(intent01);
    }
    public void attraction(int n) {
        intentView.putExtra("n", n);
    }

    private void initRecyclerView(String uName3){ // initialising the RecyclerViewAdapter for the corresponding category.
        // all three activities (3,4,5) are using one RecyclerViewAdapter.
        // That is why the category has been given to the adapter as a parameter.
        RecyclerView recyclerView = null;
        switch (category){
            case 4: recyclerView = findViewById(R.id.r_view_attractions);break;
            //case 5: recyclerView = findViewById(R.id.r_view_museums);break;
            //case 6: recyclerView = findViewById(R.id.r_view_parks);break;
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls, arrKey, mLocation, uName3, category);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}