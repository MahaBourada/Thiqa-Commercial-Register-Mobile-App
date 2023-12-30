package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] entreprises;

    String[] etats = new String[] {
            "En cours de traitement", "Acceptée", "Refusée"
    };

    private Button creer_demande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DemandesHandler db = new DemandesHandler(this);
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
        }

        creer_demande = findViewById(R.id.creer);
        creer_demande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, DemandeActivity.class);
                startActivity(myintent);
            }
        });
    }

    public void clickMsg (View view) {
        Toast.makeText(getBaseContext(), "Clicked on client support", Toast.LENGTH_SHORT).show();
    }
}