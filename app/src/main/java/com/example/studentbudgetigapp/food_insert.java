package com.example.studentbudgetigapp;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public  class food_insert extends AppCompatActivity {
    EditText ftype, fdate, famount;
    FirebaseDatabase database;
    DatabaseReference reference,monthReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_insert);

        ftype =findViewById(R.id.ftype);
        fdate =findViewById(R.id.fdate);
        famount = findViewById(R.id.famount);
        Button finsert = (Button)findViewById(R.id.finsert);

        finsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("food");

                String type = ftype.getText().toString().trim();
                String date = fdate.getText().toString().trim();
                String amountstr = famount.getText().toString().trim();

                if (TextUtils.isEmpty(type) || TextUtils.isEmpty(date) || TextUtils.isEmpty(amountstr)) {
                    // Handle empty fields
                    Toast.makeText(food_insert.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = Integer.parseInt(amountstr);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long itemCount = snapshot.getChildrenCount();
                        // Use the count as the fid
                        String fid = String.valueOf(itemCount + 1);
                        Ifood ifood = new Ifood(type, date, amount);
                        reference.child(fid).setValue(ifood);
                        Toast.makeText(getApplicationContext(), "One item added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(food_insert.this, food.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });
            }

        });

    }
}

