package com.example.studentbudgetigapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registerpage extends AppCompatActivity {

    EditText semail, spassword, sname;

    FirebaseDatabase database;
    DatabaseReference reference, monthReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        semail = findViewById(R.id.semail);
        spassword = findViewById(R.id.spassword);
        sname = findViewById(R.id.sname);
        Button signup = findViewById(R.id.signup);
        TextView login = findViewById(R.id.slogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("student");
                monthReference = database.getReference("month");

                String email = semail.getText().toString().trim();
                String password = spassword.getText().toString().trim();
                String name = sname.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                    // Handle empty fields
                    Toast.makeText(Registerpage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            // User already exists
                            Toast.makeText(Registerpage.this, "User already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            long usercount = snapshot.getChildrenCount();
                            String sid = String.valueOf(usercount + 1);

                            // User does not exist, proceed with registration
                            HelperClass helperClass = new HelperClass(name, email, password, 0);
                            reference.child(sid).setValue(helperClass);

                            Mclass mclass = new Mclass(0,0,0,0,0);
                            monthReference.child(sid).setValue(mclass);

                            Toast.makeText(getApplicationContext(), "Registration complete", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Registerpage.this, MainActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registerpage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
