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

public class food_update extends AppCompatActivity {

    EditText fuid, futype, fuamount, fudate;
    Button fupdate;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_update);

        fuid = findViewById(R.id.fuid);
        futype = findViewById(R.id.futype);
        fuamount = findViewById(R.id.fuamount);
        fudate = findViewById(R.id.fudate);
        fupdate = findViewById(R.id.fupdate);

        String itemId = getIntent().getStringExtra("itemId");
        String type = getIntent().getStringExtra("type");
        String date = getIntent().getStringExtra("date");
        String amount = getIntent().getStringExtra("amount");

        fuid.setText(itemId);
        futype.setText(type);
        fudate.setText(date);
        fuamount.setText(String.valueOf(amount));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("food").child(itemId);

        fupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecord();
            }
        });
    }
    private void updateRecord() {
        // Get the updated data from EditText fields
        String type = futype.getText().toString();
        String amountstr = fuamount.getText().toString();
        String date = fudate.getText().toString();

        int amount = Integer.parseInt(amountstr);

        // Update the data in the Firebase Realtime Database
        databaseReference.child("type").setValue(type);
        databaseReference.child("amount").setValue(amount);
        databaseReference.child("date").setValue(date);

//        Intent i = new Intent(food_update.this,food.class);
//        startActivity(i);
        // Notify the user that the record has been updated
        Toast.makeText(food_update.this, "Record Updated Successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }
}