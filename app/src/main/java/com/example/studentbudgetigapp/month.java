package com.example.studentbudgetigapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class month extends Fragment {

    private LinearLayout monthDataLayout;
    private DatabaseReference monthReference,foodReference,otherReference  ;
    TextView refresh;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_month, container, false);

        monthDataLayout = view.findViewById(R.id.month_data_layout);
        monthReference = FirebaseDatabase.getInstance().getReference("month");
        foodReference = FirebaseDatabase.getInstance().getReference("food");
        otherReference = FirebaseDatabase.getInstance().getReference("other");

        monthReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                monthDataLayout.removeAllViews();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    addProfileRecord(dataSnapshot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famount();
                oamount();
                updateExpenses();
                Toast.makeText(getActivity(),"Data refreshed",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void famount(){
        monthReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String monthId = monthSnapshot.getKey();
                    DatabaseReference monthNodeRef = monthReference.child(monthId);

                    foodReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot foodSnapshot) {
                            int foodtotal = 0;
                            for (DataSnapshot foodDataSnapshot : foodSnapshot.getChildren()) {
                                int amount = foodDataSnapshot.child("amount").getValue(Integer.class);
                                foodtotal += amount;
                            }
                            // Update the "ftot" value in the current "month" node with the calculated total amount
                            monthNodeRef.child("ftot").setValue(foodtotal);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void oamount(){
        monthReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String monthId = monthSnapshot.getKey();
                    DatabaseReference monthNodeRef = monthReference.child(monthId);

                    otherReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot otherSnapshot) {
                            int othertotal = 0;
                            for (DataSnapshot otherDataSnapshot : otherSnapshot.getChildren()) {
                                int amount = otherDataSnapshot.child("amount").getValue(Integer.class);
                                othertotal += amount;
                            }
                            // Update the "ftot" value in the current "month" node with the calculated total amount
                            monthNodeRef.child("otot").setValue(othertotal);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle onCancelled
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void updateExpenses(){
        monthReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot monthSnapshot : snapshot.getChildren()) {
                    String monthId = monthSnapshot.getKey();
                    DatabaseReference monthNodeRef = monthReference.child(monthId);

                    // Retrieve ftot and otot values from the current month
                    int ftot = monthSnapshot.child("ftot").getValue(Integer.class);
                    int otot = monthSnapshot.child("otot").getValue(Integer.class);

                    // Calculate total expenses
                    int totalExpenses = ftot + otot;

                    // Update the "expenses" child in the current "month" node with the calculated total expenses
                    monthNodeRef.child("etot").setValue(totalExpenses);

                    // Retrieve the income value from the current month
                    int income = monthSnapshot.child("total").getValue(Integer.class);

                    // Calculate savings
                    int savings = income - totalExpenses;

                    // Update the "save" child in the current "month" node with the calculated savings
                    monthNodeRef.child("save").setValue(savings);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addProfileRecord(DataSnapshot dataSnapshot){

        // Retrieve data
        String key = dataSnapshot.getKey();
        Long totalLong = dataSnapshot.child("total").getValue(Long.class);
        String total = String.valueOf(totalLong);

        Long ftotLong = dataSnapshot.child("ftot").getValue(Long.class);
        String ftot = String.valueOf(ftotLong);

        Long etotLong = dataSnapshot.child("etot").getValue(Long.class);
        String etot = String.valueOf(etotLong);

        Long ototLong = dataSnapshot.child("otot").getValue(Long.class);
        String otot = String.valueOf(ototLong);

        Long saveLong = dataSnapshot.child("save").getValue(Long.class);
        String save = String.valueOf(saveLong);

        // Inflate layout for each record
        @SuppressLint("ResourceType")

        View recordView = LayoutInflater.from(getContext()).inflate(R.layout.month_record, monthDataLayout, false);

        // Initialize views in the record layout
        TextView totalTextview = recordView.findViewById(R.id.total);
        TextView etotTextView = recordView.findViewById(R.id.etot);
        TextView ftotTextView = recordView.findViewById(R.id.ftot);
        TextView ototTextView = recordView.findViewById(R.id.otot);
        TextView saveTextView = recordView.findViewById(R.id.save);

        // Set data to views
        // keyTextview.setText(key);
        totalTextview.setText(total);
        etotTextView.setText(etot);
        ftotTextView.setText(ftot);
        ototTextView.setText(otot);
        saveTextView.setText(save);

        // Add record view to the layout
        monthDataLayout.addView(recordView);

    }
}