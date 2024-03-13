package com.example.studentbudgetigapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class other_update extends AppCompatActivity {
    EditText ouid, outype, ouamount, oudate;
    Button oupdate;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_update);

        ouid = findViewById(R.id.ouid);
        outype = findViewById(R.id.outype);
        ouamount = findViewById(R.id.ouamount);
        oudate = findViewById(R.id.oudate);
        oupdate = findViewById(R.id.oupdate);

        String itemId = getIntent().getStringExtra("itemId");
        String type = getIntent().getStringExtra("type");
        String date = getIntent().getStringExtra("date");
        String amount = getIntent().getStringExtra("amount");

        ouid.setText(itemId);
        outype.setText(type);
        oudate.setText(date);
        ouamount.setText(amount);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("other").child(itemId);

        oupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });
    }
    private void updateRecord() {
        // Get the updated data from EditText fields
        String type = outype.getText().toString();
        String amountstr = ouamount.getText().toString();
        String date = oudate.getText().toString();

        int amount = Integer.parseInt(amountstr);

        // Update the data in the Firebase Realtime Database
        databaseReference.child("type").setValue(type);
        databaseReference.child("amount").setValue(amount);
        databaseReference.child("date").setValue(date);

//        Intent i = new Intent(other_update.this,other.class);
//        startActivity(i);
        // Notify the user that the record has been updated
        Toast.makeText(other_update.this, "Record Updated Successfully and Press again to go back", Toast.LENGTH_SHORT).show();
        // Finish the activity
        finish();
    }
}