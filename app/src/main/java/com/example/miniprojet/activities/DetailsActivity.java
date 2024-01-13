package com.example.miniprojet.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.R;
import com.example.miniprojet.model.Demandes;

public class DetailsActivity extends AppCompatActivity {
    TextView idntType, idntNum, company_name, adress, activity, fiscale_num, rib, etat, demande_title;
    Button idntFile, contratFile, fiscaleFile, ribFile;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_details);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(DetailsActivity.this, DashboardActivity.class);
                startActivity(myintent);
            }
        });

        Intent intent = getIntent();
        Demandes demande = (Demandes) intent.getSerializableExtra("demandes");
        if (intent != null && intent.hasExtra("demandes")) {

            // Access demand properties and set them in your UI elements
            demande_title = findViewById(R.id.companyName);
            company_name = findViewById(R.id.nom_entreprise);
            idntType = findViewById(R.id.type_identite);
            idntNum = findViewById(R.id.num_identite);
            adress = findViewById(R.id.adresse);
            activity = findViewById(R.id.activite);
            fiscale_num = findViewById(R.id.num_fiscale);
            rib = findViewById(R.id.rib);
            etat = findViewById(R.id.etat);

            company_name.setText(demande.getNomEntreprise());
            demande_title.setText(demande.getNomEntreprise());
            idntType.setText(demande.getTypeIdentite());
            idntNum.setText(demande.getNumIdentite());
            adress.setText(demande.getAdresse());
            activity.setText(demande.getActivity());
            fiscale_num.setText(demande.getNumFiscale());
            rib.setText(demande.getRibBanque());

            if (demande.getEtat().equals("En cours de traitement")) {
                etat.setTextColor(Color.argb(255, 208, 109, 17));
            } else if (demande.getEtat().equals("Demande acceptée")) {
                etat.setTextColor(Color.argb(255, 14, 107, 12));
            } else if (demande.getEtat().equals("Demande refusée")) {
                etat.setTextColor(Color.argb(255, 178, 22, 22));
            }

            etat.setText(demande.getEtat());
        }

        idntFile = findViewById(R.id.idntFile);
        contratFile = findViewById(R.id.contratFile);
        fiscaleFile = findViewById(R.id.fiscaleFile);
        ribFile = findViewById(R.id.ribFile);

        idntFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(demande.getIdentitePath());
            }
        });

        contratFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(demande.getContratEndroitPath());
            }
        });

        fiscaleFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(demande.getFiscalePath());
            }
        });

        ribFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(demande.getRibPath());
            }
        });
    }

    private void downloadFile(String uriString) {
        Uri uri = Uri.parse(uriString);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf"); // You can set the MIME type based on the file type

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            System.out.println(e);
        }
    }
}
