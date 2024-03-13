package com.example.studentbudgetigapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class other_insert extends AppCompatActivity {

    EditText otype, odate, oamount;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_insert);

        otype =findViewById(R.id.otype);
        odate =findViewById(R.id.odate);
        oamount = findViewById(R.id.oamount);
        Button oinsert = (Button)findViewById(R.id.oinsert);

        oinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("other");

                String type = otype.getText().toString().trim();
                String date = odate.getText().toString().trim();
                String amountstr = oamount.getText().toString().trim();

                if (TextUtils.isEmpty(type) || TextUtils.isEmpty(date) || TextUtils.isEmpty(amountstr)) {
                    // Handle empty fields
                    Toast.makeText(other_insert.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                int amount = Integer.parseInt(amountstr);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long itemCount = snapshot.getChildrenCount();
                        // Use the count as the fid
                        String oid = String.valueOf(itemCount + 1);
                        Iother iother = new Iother(type, date, amount);
                        reference.child(oid).setValue(iother);
                        Toast.makeText(getApplicationContext(), "One item added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(other_insert.this, other.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                        Toast.makeText(other_insert.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}