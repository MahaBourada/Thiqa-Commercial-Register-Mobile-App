package com.example.miniprojet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miniprojet.customAdapters.CustomDemandeAdapter;
import com.example.miniprojet.R;
import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;
import com.example.miniprojet.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "Dashboard";
    private Button creer_demande;
    private ImageButton support, faq, logoutBtn;
    private TextView firstNameTextView, lastNameTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Log.d("Debug", "onCreate: Home activity created");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        logoutBtn = findViewById(R.id.logoutbtn);

        // Check if the user is authenticated
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid(); // No change here
            Log.d("MyApp1", "User ID: " + userId);

            // DatabaseReference pointing to the specific user's node in the Realtime Database
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            Log.d("MyApp2", "User ID: " + userId);
            // Use addValueEventListener to continuously listen for changes to user data
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Directly retrieve the User object using the User class
                        User user = snapshot.getValue(User.class);

                        if (user != null) {
                            String firstName = user.getFirstName();
                            String lastName = user.getLastName();

                            // Log the retrieved user information
                            Log.d("MyApp", userId + ", FirstName=" + user.getFirstName() + ", LastName=" + user.getLastName());
                            System.out.println("FETCH DATA FROM DB FIRE User info: " + ", FirstName=" + user.getFirstName() + ", LastName=" + user.getLastName());
                            Log.d("Debug", "FETCH DATA FROM DB FIRE User info: " + ", FirstName=" + user.getFirstName() + ", LastName=" + user.getLastName());

                            firstNameTextView.setText(firstName);
                            lastNameTextView.setText(lastName);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

            // Logout button click listener
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sign out the user
                    firebaseAuth.signOut();

                    // Navigate back to MainActivity
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                }
            });

            DemandesHelper db = new DemandesHelper(this);
            List<Demandes> demandesList = db.getAllDemandes();

            ListView listView = findViewById(R.id.demande);
            TextView emptyListTextView = findViewById(R.id.empty_list);

            if (demandesList.isEmpty()) {
                listView.setVisibility(View.GONE);
                emptyListTextView.setVisibility(View.VISIBLE);
            } else {
                CustomDemandeAdapter adapter = new CustomDemandeAdapter(this, demandesList);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                emptyListTextView.setVisibility(View.GONE);

                // Set OnItemClickListener for the ListView
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Retrieve the clicked Demandes object
                        Demandes selectedDemande = demandesList.get(position);

                        // Create an Intent to start the DetailsActivity
                        Intent intent = new Intent(DashboardActivity.this, DetailsActivity.class);

                        // Pass the demand ID or any necessary information to retrieve details in DetailsActivity
                        intent.putExtra("demandeId", selectedDemande.getId()); // Assuming getId() returns the ID

                        // Start the DetailsActivity
                        startActivity(intent);
                    }
                });
            }

            creer_demande = findViewById(R.id.creer);
            creer_demande.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent = new Intent(DashboardActivity.this, DemandeActivity.class);
                    startActivity(myintent);
                }
            });

            support = findViewById(R.id.clientSupportButton);
            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent = new Intent(DashboardActivity.this, SupportActivity.class);
                    startActivity(myintent);
                }
            });

            faq = findViewById(R.id.faqButton);
            faq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myintent = new Intent(DashboardActivity.this, FaqActivity.class);
                    startActivity(myintent);
                }
            });
        }
    }
}