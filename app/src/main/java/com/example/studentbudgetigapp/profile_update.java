package com.example.studentbudgetigapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class profile_update extends AppCompatActivity {
    EditText sid, sname, samount, spassword,semail;
    Button pupdate;
    DatabaseReference profileReference,monthReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        sid = findViewById(R.id.puid);
        sname = findViewById(R.id.puname);
        semail = findViewById(R.id.puemail);
        spassword = findViewById(R.id.pupassword);
        samount = findViewById(R.id.puamount);

        String itemId = getIntent().getStringExtra("itemId");
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String amount = getIntent().getStringExtra("amount");

        sid.setText(itemId);
        sname.setText(name);
        semail.setText(email);
        spassword.setText(password);
        samount.setText(String.valueOf(amount));

        String key = getIntent().getStringExtra("key");

        profileReference = FirebaseDatabase.getInstance().getReference().child("student").child(itemId);
        monthReference = FirebaseDatabase.getInstance().getReference().child("month").child(itemId);

        pupdate = findViewById(R.id.pupdate);
        pupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateRecord();
            }
        });
    }

    private void updateRecord(){

        String name = sname.getText().toString();
        String email = semail.getText().toString();
        String password = spassword.getText().toString();
        String amount = samount.getText().toString();

        profileReference.child("name").setValue(name);
        profileReference.child("email").setValue(email);
        profileReference.child("password").setValue(password);
        profileReference.child("amount").setValue(Integer.parseInt(amount));

        monthReference.child("total").setValue(Integer.parseInt(amount));
        monthReference.child("save").setValue(Integer.parseInt(amount));


        Toast.makeText(profile_update.this, "Record Updated Successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }
}