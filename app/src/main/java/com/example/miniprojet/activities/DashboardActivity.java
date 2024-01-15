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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.miniprojet.customAdapters.CustomDemandeAdapter;
import com.example.miniprojet.R;
import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "Dashboard";
    private Button creer_demande;
    private ImageButton support, faq, profile, logoutBtn;
    private TextView firstNameTextView, lastNameTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef, demandesRef;
    List<Demandes> demandesList;
    CustomDemandeAdapter adapter;
    RelativeLayout loading_screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loading_screen = findViewById(R.id.loading_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        logoutBtn = findViewById(R.id.logoutbtn);


        // Check if the user is authenticated
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid(); // No change here

            loading_screen.setVisibility(View.VISIBLE);

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
                            String firstName = user.getFirstName();
                            String lastName = user.getLastName();

                            firstNameTextView.setText(firstName);
                            lastNameTextView.setText(lastName);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    }

                    loading_screen.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    loading_screen.setVisibility(View.GONE);
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

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

        TextView emptyListTextView = findViewById(R.id.empty_list);
        ListView demandesListView = findViewById(R.id.demande);

        demandesList = new ArrayList<>();
        adapter = new CustomDemandeAdapter(this, demandesList);
        demandesRef = FirebaseDatabase.getInstance().getReference().child("demandes");

        demandesListView.setAdapter(adapter);
        if (firebaseUser != null) {
            String currentUserId = firebaseUser.getUid();


            demandesRef.orderByChild("userID").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    demandesList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Demandes demande = snapshot.getValue(Demandes.class);
                        demandesList.add(demande); // Add 'demande' to the list
                    }
                    adapter.notifyDataSetChanged();
                    if (demandesList.isEmpty()) {
                        demandesListView.setVisibility(View.GONE);
                        emptyListTextView.setVisibility(View.VISIBLE);
                    } else {
                        demandesListView.setVisibility(View.VISIBLE);
                        emptyListTextView.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        String demandeId = demandesRef.push().getKey();
        demandesRef.child(demandeId).setValue(new Demandes());
        demandesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected Demandes object
                Demandes selectedDemande = demandesList.get(position);

                // Create an Intent to start the DetailsActivity
                Intent intent = new Intent(DashboardActivity.this, DetailsActivity.class);

                // Pass the Firebase-generated demande ID to retrieve details in DetailsActivity
                intent.putExtra("demandeId", selectedDemande.getId()); // Assuming getId() returns the Firebase-generated ID
                Log.d("Debug", "DEMANDE ID: " + selectedDemande.getId());

                // Start the DetailsActivity
                startActivity(intent);
            }
        });


        if (demandesList.isEmpty()) {
                demandesListView.setVisibility(View.GONE);
                emptyListTextView.setVisibility(View.VISIBLE);
            } else {
                //CustomDemandeAdapter adapter = new CustomDemandeAdapter(this, demandesList);
                //listView.setAdapter(adapter);
                demandesListView.setVisibility(View.VISIBLE);
                emptyListTextView.setVisibility(View.GONE);

                // Set OnItemClickListener for the ListView
                demandesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(myintent);
            }
        });
    }
}