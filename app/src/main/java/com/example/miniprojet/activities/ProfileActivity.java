package com.example.miniprojet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.miniprojet.R;
import com.example.miniprojet.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends Activity {
    TextView email, firstName, lastName, phone;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(myintent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        email = findViewById(R.id.email);
        firstName = findViewById(R.id.prenom);
        lastName = findViewById(R.id.nomfamille);
        phone = findViewById(R.id.tel);

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid(); // No change here

            // DatabaseReference pointing to the specific user's node in the Realtime Database
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            // Use addValueEventListener to continuously listen for changes to user data
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Directly retrieve the User object using the User class
                        User user = snapshot.getValue(User.class);

                        if (user != null) {

                            email.setText(user.getEmail());
                            firstName.setText(user.getFirstName());
                            lastName.setText(user.getLastName());
                            phone.setText(user.getPhoneNumber());
                        } else {
                            Log.d("Debug", "No such document");
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("Debug", "Failed to read value.", error.toException());
                }
            });
        }
    }
}
