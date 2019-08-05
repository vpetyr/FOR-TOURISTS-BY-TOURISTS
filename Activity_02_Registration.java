package com.example.fortouristsbytourists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_02_Registration extends AppCompatActivity {
    // Executing a service to insert the user registration information into database.

    DatabaseReference ref;
    User user;

    Button btn0201;  // register
    String fName, sName, eMail, pass1, pass2;
    EditText fn, sn, email, pas1, pas2;

    Intent intent01;

    boolean correct() { // checking the user fields for correct information
        boolean flag = true;
        if (fName.length() == 0) flag = false;
        if (sName.length() == 0) flag = false;
        if (eMail.length() == 0) flag = false;
        if (pass1.length() == 0) flag = false;
        if (pass2.length() == 0) flag = false;
        if (!pass1.equals(pass2)) flag = false;
        if (!eMail.contains("@")) flag = false;
        // etc. If the email address contains an '@' sign.
        return flag;
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02_registration);

        btn0201 = (Button)findViewById(R.id.button0201);  // register
        fn = (EditText)findViewById(R.id.editText0202);
        sn = (EditText)findViewById(R.id.editText0203);
        email = (EditText)findViewById(R.id.editText0204);
        pas1 = (EditText)findViewById(R.id.editText0205);
        pas2 = (EditText)findViewById(R.id.editText0206);

        intent01 = new Intent(this, Activity_01_Main.class); // Registration form.

        ref = FirebaseDatabase.getInstance().getReference("users"); // Firebase Database connection (with the user field in this case).

        btn0201.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = fn.getText().toString();
                sName = sn.getText().toString();
                eMail = email.getText().toString();
                pass1 = pas1.getText().toString();
                pass2 = pas2.getText().toString();

                if (correct()) {    // write into database
                    String id = ref.push().getKey();
                    user = new User(fName, sName, eMail, pass1); // creating a user object
                    ref.child(id).setValue(user); // writing the user object in to new child in the database

                    Context context = getApplicationContext(); // if the field/s is/are empty
                    Toast.makeText(context, "Registration SUCCESSFULL", Toast.LENGTH_LONG).show();

                    startActivity(intent01); //back to the Login activity
                }
                else {
                    Context context = getApplicationContext(); // if the field/s is/are empty
                    Toast.makeText(context, "Enter valid User Information", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}