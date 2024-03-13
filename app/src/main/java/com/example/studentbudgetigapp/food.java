package com.example.studentbudgetigapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
public class food extends Fragment {

    private Button insert;
    private LinearLayout foodDataLayout;
    private DatabaseReference foodReference;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        foodDataLayout = view.findViewById(R.id.food_data_layout);
        foodReference = FirebaseDatabase.getInstance().getReference("food");

        foodReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodDataLayout.removeAllViews();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Retrieve data
                    String key = dataSnapshot.getKey();
                    String type = dataSnapshot.child("type").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
//                    int amount = dataSnapshot.child("amount").getValue(Integer.class);

                    Long amountLong = dataSnapshot.child("amount").getValue(Long.class);
                    String amount = String.valueOf(amountLong);
                    // Inflate layout for each record
                    @SuppressLint("ResourceType")

                    View recordView = LayoutInflater.from(getContext()).inflate(R.layout.food_record, foodDataLayout, false);

                    // Initialize views in the record layout
                    TextView keyTextview = recordView.findViewById(R.id.id);
                    TextView typeTextView = recordView.findViewById(R.id.type);
                    TextView dateTextView = recordView.findViewById(R.id.date);
                    TextView amountTextView = recordView.findViewById(R.id.amount);

                    // Set data to views
                    keyTextview.setText(key);
                    typeTextView.setText(type);
                    dateTextView.setText(date);
                    amountTextView.setText(amount);

                    // Add record view to the layout2
                    foodDataLayout.addView(recordView);

                    TextView delete = recordView.findViewById(R.id.fdelete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Retrieve the key (id) of the clicked item
                            String itemId = keyTextview.getText().toString();
                            DatabaseReference itemRef = foodReference.child(itemId);

                            // Remove the item from the database
                            itemRef.removeValue().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to delete item", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    keyTextview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Retrieve the key (id) of the clicked item
                            String itemId = keyTextview.getText().toString();
                            DatabaseReference itemRef = foodReference.child(itemId);

                            itemRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        String type = snapshot.child("type").getValue(String.class);
                                        String date = snapshot.child("date").getValue(String.class);
//                                        int amount = snapshot.child("amount").getValue(Integer.class);
                                        Long amountLong = snapshot.child("amount").getValue(Long.class);
                                        String amount = String.valueOf(amountLong);

                                        // Start update_food activity and pass necessary data
                                        Intent intent = new Intent(getActivity(), food_update.class);
                                        intent.putExtra("itemId", itemId);
                                        intent.putExtra("type", type);
                                        intent.putExtra("date", date);
                                        intent.putExtra("amount", amount);
                                        startActivity(intent);
                                    } else {
                                        // Handle the case where the data for the clicked item doesn't exist
                                        Toast.makeText(getActivity(), "Data not found for item " + itemId, Toast.LENGTH_SHORT).show();
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        insert = view.findViewById(R.id.fadd);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), food_insert.class);
                startActivity(i);
            }
        });
        return view;
    }
}
