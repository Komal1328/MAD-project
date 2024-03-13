package com.example.studentbudgetigapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText lemail, lpassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lemail = findViewById(R.id.lemail);
        lpassword = findViewById(R.id.lpassword);
        Button loginbtn = (Button) findViewById(R.id.llogin);
        TextView signin = (TextView) findViewById(R.id.lsignup);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateemail() | !validatePassword()) {
                } else {
                    checkUser();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registerpage.class);
                startActivity(i);
            }
        });
    }
    public Boolean validateemail() {
        String val = lemail.getText().toString();
        if (val.isEmpty()) {
            lemail.setError("Username cannot be empty");
            return false;
        } else {
            lemail.setError(null);
            return true;
        }
    }
    public Boolean validatePassword() {
        String val = lpassword.getText().toString();
        if (val.isEmpty()) {
            lpassword.setError("Password cannot be empty");
            return false;
        } else {
            lpassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String email = lemail.getText().toString().trim();
        String password = lpassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("student");
//        Query checkUserDatabase = reference.orderByChild("email").equalTo(email);

//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userEmail = dataSnapshot.child("email").getValue(String.class);
                    String userPassword = dataSnapshot.child("password").getValue(String.class);
                    if (userEmail != null && userEmail.equals(email) && userPassword != null && userPassword.equals(password)) {
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                        String passwordFromDB = dataSnapshot.child("password").getValue(String.class);

                        Intent intent = new Intent(MainActivity.this, Homepage.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);

                        userFound = true;
                        break;
                    }
                }
                if (!userFound) {
                    lemail.setError("Invalid email or password");
                    lemail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}


// if (snapshot.exists()) {
//         lemail.setError(null);
//         String passwordFromDB = snapshot.child(email).child("password").getValue(String.class);
//        if (passwordFromDB.equals(password)) {
//        lemail.setError(null);
//        String nameFromDB = snapshot.child(email).child("name").getValue(String.class);
//        String emailFromDB = snapshot.child(email).child("email").getValue(String.class);
//
//        Intent intent = new Intent(MainActivity.this, Homepage.class);
//        intent.putExtra("name", nameFromDB);
//        intent.putExtra("email", emailFromDB);
//        intent.putExtra("password", passwordFromDB);
//        startActivity(intent);
//        } else {
//        lpassword.setError("Invalid Credentials");
//        lpassword.requestFocus();
//        }
//        } else {
//        lemail.setError("User does not exist");
//        lemail.requestFocus();
//        }