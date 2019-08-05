package com.example.fortouristsbytourists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_01_Main extends AppCompatActivity {

    Button btn0101, btn0102; // btn0101 - Login; btn0102 - Registration
    EditText etEmail, etPass;
    String email, pass;
    boolean success;
    DatabaseReference ref;
    ArrayList<User> list = new ArrayList<>();
    int index = 0;

    Intent intent02, intent03; // intent02 - registration; intent03 - best_places_to_visit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01_main);

        intent03 = new Intent(this, Activity_03_Best_Places_To_Visit.class); // Login to Best Places to visit.
        intent02 = new Intent(this, Activity_02_Registration.class); // Registration form.

        //Select * From_users; < --- THE ONE BELOW IS SAME AS IN SQL
        ref = FirebaseDatabase.getInstance().getReference("users");

        btn0101 = (Button) findViewById(R.id.button0101);
        btn0102 = (Button) findViewById(R.id.button0102);
        etEmail = (EditText) findViewById(R.id.editText0102);
        etPass = (EditText) findViewById(R.id.editText0103);

        ValueEventListener listener = new ValueEventListener() { // taking the user field/list database in an ArrayList
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dss: dataSnapshot.getChildren())
                {
                    User user = dss.getValue(User.class);
                    list.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        btn0101.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if ((email.length() > 0) && (pass.length() > 0)) { // checking if the email and password fields are empty
                    intent03.putExtra("email", email);

                    //success = true; // if the user exists in the database
                    success = existUser(email, pass);
                    if (success) startActivity(intent03); // to Activity_03_Best_Places_To_Visit

                    else {

                        Context context = getApplicationContext();
                        Toast.makeText(context, "Enter valid Email/Password", Toast.LENGTH_LONG).show();  // incorrect email/pass

                    }

                }

                else {

                    Context context = getApplicationContext(); // if the field/s is/are empty
                    Toast.makeText(context, "Enter valid Email/Password", Toast.LENGTH_LONG).show();

                }
            }
        });

        btn0102.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent02); // to Activity_02_Registration
            }
        });

        ref.addListenerForSingleValueEvent(listener);

    }

    boolean existUser(String email, String pass){ // checking the list if there is a user with such a username and a password
        boolean exist = false;

        int n = list.size();
        for (int i = 0; i < n; i++){
            User user = list.get(i);
            if ((user.getEmail().equals(email)) && (user.getPass().equals(pass))) {
                exist = true;
                break;
            }
        }

        return exist;
    }

}