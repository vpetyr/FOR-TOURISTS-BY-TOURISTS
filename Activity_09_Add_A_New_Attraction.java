package com.example.fortouristsbytourists;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Adding new attraction in selected category, after picture being uploaded
 */
public class Activity_09_Add_A_New_Attraction extends AppCompatActivity {
    TextView tv0902,  // userName
             tv0901,  // attractionName, will be used as primary key in database
             tv0903;  // "Picture Uploaded"
    EditText et0904,  // Location (City etc.)
             et0905;  // description
    Spinner  spinner0901; // attraction/museums/parks
    Button   btnLogout, btnAdd; //using XML 'onClick' tab
    String userName, storageUrl, attrName, location;
    String atrChoice [] = {"Select Category","Attractions", "Museums", "Parks and Green Spaces"};
    Attractions attraction = new Attractions();
    int attrType = 0;  //category
    DatabaseReference ref;
    boolean success;  //flag for filling the fields

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_09_add_a_new_attraction);

        // the picture is already uploaded, followed by data receiving from the repository
        tv0902 = findViewById(R.id.textView0902);   //userName
        userName = getIntent().getStringExtra("userName");
        tv0902.setText(userName);
        storageUrl = getIntent().getStringExtra("URL");
        attrName = getIntent().getStringExtra("attrName");
        tv0901 = findViewById(R.id.textView0901); tv0901.setText(attrName);
        tv0903 = findViewById(R.id.textView0903); tv0903.setText("Photo Uploaded");

        // filling up the editText fields
        et0904 = findViewById(R.id.editText0904);
        et0905 = findViewById(R.id.editText0905);
        btnAdd = findViewById(R.id.button0902);

        spinner0901 = findViewById(R.id.spinner0901); // choosing the attraction category (Attractions, Museums, Parks...)
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(Activity_09_Add_A_New_Attraction.this, android.R.layout.simple_spinner_item, atrChoice);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner0901.setAdapter(adapterSpinner);

        spinner0901.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:         //by default, it is nothing selected yet
                        attrType = 0;
                        Toast myToast0 = Toast.makeText(getApplicationContext(),"Select a Category", Toast.LENGTH_LONG );
                        myToast0.setGravity(Gravity.CENTER,0,225);
                        myToast0.show();
 //                     spinner0901.setBackgroundColor(Color.RED);
                        break;
                    case 1:     // "Attractions" selection
                        Toast myToast1 = Toast.makeText(getApplicationContext(),"Category Attractions Selected", Toast.LENGTH_LONG );
                        myToast1.setGravity(Gravity.CENTER,0,225);
                        myToast1.show();
                        attrType = 1;
                        break;
                    case 2:     // "Museums" selection
                        Toast myToast2 = Toast.makeText(getApplicationContext(),"Category Museums Selected", Toast.LENGTH_LONG );
                        myToast2.setGravity(Gravity.CENTER,0,225);
                        myToast2.show();
                        attrType = 2;
                        break;
                    case 3:     // "Parks_and_Green_Places" selection
                        Toast myToast3 = Toast.makeText(getApplicationContext(),"Category Parks Selected", Toast.LENGTH_LONG );
                        myToast3.setGravity(Gravity.CENTER,0,225);
                        myToast3.show();
                        attrType = 3;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void logoutButton(View v){   // using XML 'onClick' tab
        if (v.getId()==R.id.button0901) {
            Intent back2Activity01 = new Intent(this, Activity_01_Main.class);
            startActivity(back2Activity01);
        }

    }

    public void addAtraction(View v){   //using XML 'onClick' tab
        try {
            if (v.getId() == R.id.button0902 && (attrType == 1 || attrType == 2 || attrType == 3)) {
                // if (buttonAdd selected AND database category selected)
                addNewAttraction();
            }
        }catch (MyException ex2) {
            Toast myToast2 = Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG);
            myToast2.setGravity(Gravity.CENTER, 0, 0);
            myToast2.show();

        }
    }

    public void addNewAttraction() throws MyException{ //throwing an exception if any field is incorrect
        do {    // filling the fields until incorrect
            success = true;     //flag for success; if anywhere incorrect, shutting the flag "down"

            attraction.setCanBook(false); //no bookingURL
            attraction.setDescription(et0905.getText().toString()); //description
            if (attraction.getDescription().length() == 0) {
                success = false; throw new MyException();
            }
            attraction.setTitle(attrName);
            attraction.setImageURL(storageUrl);
            attraction.setBookingURL("");
            location = et0904.getText().toString();

            attraction.setLocation(location);
            if (attraction.getLocation().length() == 0) {
                success = false; throw new MyException();
            }

            if (attrType == 0){success=false;
                throw new MyException("Attraction Type '0' ...");
 //               Toast.makeText(getApplicationContext(),"Select category",Toast.LENGTH_LONG).show();
            }

            if (!success) {
                Toast myToast2 = Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG);
                myToast2.setGravity(Gravity.CENTER, 0, 0);
                myToast2.show();
                throw new MyException();
            }

        }while (!success);

        switch (attrType){  //database choice
            case 1: ref = FirebaseDatabase.getInstance().getReference("attractions"); break; //DB attractions
            case 2: ref = FirebaseDatabase.getInstance().getReference("museums"); break;     //DB museums
            case 3: ref = FirebaseDatabase.getInstance().getReference("parks"); break;       //DB parks
        }


        String id=attraction.getTitle().replaceAll(" ","");  //generating a key ---> the title is with no spaces!!!
        ref.child(id).setValue(attraction);        // storing an attraction in database
        Intent back2BestPlaces = new Intent(this, Activity_03_Best_Places_To_Visit.class); //back to Best_Places Activity
        back2BestPlaces.putExtra("email", userName);
        startActivity(back2BestPlaces);
    }

}