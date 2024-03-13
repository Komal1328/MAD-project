package com.example.studentbudgetigapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class profile extends Fragment {
    private Button update;
    private TextView refresh;
    private LinearLayout profileDataLayout;
    private DatabaseReference profileReference;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileDataLayout = view.findViewById(R.id.profile_data_layout);
        profileReference = FirebaseDatabase.getInstance().getReference("student");

        update = view.findViewById(R.id.sadd);

        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profileDataLayout.removeAllViews();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);

                    Long amountLong = dataSnapshot.child("amount").getValue(Long.class);
                    String amount = String.valueOf(amountLong);

                    // Inflate layout for each record
                    @SuppressLint("InflateParams")
                    View recordView = LayoutInflater.from(getContext()).inflate(R.layout.profile_record, profileDataLayout, false);

                    // Initialize views in the record layout
                    TextView keyTextview = recordView.findViewById(R.id.pid);
                    TextView nameTextView = recordView.findViewById(R.id.pname);
                    TextView emailTextView = recordView.findViewById(R.id.pemail);
                    TextView passwordTextView = recordView.findViewById(R.id.ppassword);
                    TextView amountTextView = recordView.findViewById(R.id.pamount);

                    // Set data to views
                    keyTextview.setText(key);
                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    passwordTextView.setText(password);
                    amountTextView.setText(amount);

                    profileDataLayout.addView(recordView);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Retrieve the key (id) of the clicked item
                            String itemId = keyTextview.getText().toString();
                            DatabaseReference itemRef = profileReference.child(itemId);

                            itemRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String name = snapshot.child("name").getValue(String.class);
                                        String email = snapshot.child("email").getValue(String.class);
                                        String password = snapshot.child("password").getValue(String.class);


                                        Long amountLong = snapshot.child("amount").getValue(Long.class);
                                        String amount = String.valueOf(amountLong);


                                        Intent intent = new Intent(getActivity(), profile_update.class);
                                        intent.putExtra("itemId", itemId);
                                        intent.putExtra("name", name);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", password);
                                        intent.putExtra("amount", amount);
                                        startActivity(intent);

                                    } else {
                                        // Handle the case where the data for the clicked item doesn't exist
                                        Toast.makeText(getActivity(), "Data not found for item " + itemId, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("ProfileFragment", "Database error: " + error.getMessage());
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "Database error: " + error.getMessage());
            }
        });

//        refresh = view.findViewById(R.id.refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                profileReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        profileDataLayout.removeAllViews();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            addProfileRecord(dataSnapshot);
//                        }
//                        // Show a toast or perform any other action as needed
//                        Toast.makeText(getActivity(), "Details fetched successfully", Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });

        return view;
    }

//    public void onProfileIdClicked(View view) {
//        TextView pidTextView = (TextView) view;
//        String key = pidTextView.getText().toString();
//        Intent intent = new Intent(getActivity(), profile_update.class);
//        intent.putExtra("key", key);
//        startActivity(intent);
//    }

    @SuppressLint("MissingInflatedId")
    private void addProfileRecord(DataSnapshot dataSnapshot) {
        // Retrieve data
        String key = dataSnapshot.getKey();
        String name = dataSnapshot.child("name").getValue(String.class);
        String email = dataSnapshot.child("email").getValue(String.class);
        String password = dataSnapshot.child("password").getValue(String.class);
        String amount = String.valueOf(dataSnapshot.child("amount").getValue(Long.class));

        // Inflate layout for each record
        @SuppressLint("InflateParams")
        View recordView = LayoutInflater.from(getContext()).inflate(R.layout.profile_record, null);

        // Initialize views in the record layout
        TextView keyTextview = recordView.findViewById(R.id.pid);
        TextView nameTextView = recordView.findViewById(R.id.pname);
        TextView emailTextView = recordView.findViewById(R.id.pemail);
        TextView passwordTextView = recordView.findViewById(R.id.ppassword);
        TextView amountTextView = recordView.findViewById(R.id.pamount);

        // Set data to views
        keyTextview.setText(key);
        nameTextView.setText(name);
        emailTextView.setText(email);
        passwordTextView.setText(password);
        amountTextView.setText(amount);

        // Add record view to the layout
        profileDataLayout.addView(recordView);
    }
}