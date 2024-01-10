package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private Button creer_demande;
    private ImageButton support, faq;
    private Handler handler = new Handler();
    private static final int UPDATE_DELAY = 5 * 60 * 1000; // 5 minutes in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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