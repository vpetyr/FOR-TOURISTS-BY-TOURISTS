package com.example.fortouristsbytourists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_03_Best_Places_To_Visit extends AppCompatActivity implements View.OnClickListener {

    //Many buttons in Activity 3, that is why implementing interface was preferred.
    Button btn0304, btn0305, btn0306, btn0307, btn0308;
    String uName, uName2,uName3;
    TextView userName;
    Intent intent01, intent04, intent05, intent06, intent09;
    int category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_best_places_to_visit);

        btn0304 = (Button) findViewById(R.id.button0304); btn0304.setOnClickListener(this); // ---> Attraction
        btn0305 = (Button) findViewById(R.id.button0305); btn0305.setOnClickListener(this); // ---> Museums
        btn0306 = (Button) findViewById(R.id.button0306); btn0306.setOnClickListener(this); // ---> Parks and Green Places
        btn0307 = (Button) findViewById(R.id.button0307); btn0307.setOnClickListener(this); // ---> Add New Attraction
        btn0308 = (Button) findViewById(R.id.button0308); btn0308.setOnClickListener(this); // ---> Logout

        userName = (TextView) findViewById(R.id.textView0301);
        Intent getMemory = getIntent();
        uName = getMemory.getStringExtra("email");
        userName.setText(uName);
        uName2 = uName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0304: attractions(); break;
            case R.id.button0305: museums(); break;
            case R.id.button0306: parks(); break;
            case R.id.button0307: newAttraction(); break;
            case R.id.button0308: logout01(); break;
        }
    }
    public void attractions() {
        intent04 = new Intent(this, Activity_04_Attractions_In_London.class);
        intent04.putExtra("uName2", uName2);
        category = 4;intent04.putExtra("category",category);
        startActivity(intent04);

    }
    public void museums() {
        intent05 = new Intent(this, Activity_05_Museums_In_London.class);
        intent05.putExtra("uName2", uName2);
        category = 5;intent05.putExtra("category",category);
        startActivity(intent05);
    }
    public void parks() {
        intent06 = new Intent(this, Activity_06_Parks_and_Green_Places.class);
        intent06.putExtra("uName2", uName2);
        category = 6;intent06.putExtra("category",category);
        startActivity(intent06);
    }
    public void newAttraction() {
        intent09 = new Intent(this, Activity_11_Upload_Picture.class);
        intent09.putExtra("uName3", uName2);
        startActivity(intent09);
    }
    public void logout01() {
        intent01 = new Intent(this, Activity_01_Main.class);
        startActivity(intent01);
    }
}