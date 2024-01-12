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
import com.example.miniprojet.model.DemandesHelper;

public class DetailsActivity extends AppCompatActivity {
    TextView idntType, idntNum, company_name, adress, activity, fiscale_num, rib, etat, demandeTitle;
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

        DemandesHelper db = new DemandesHelper(this);
        int demandeId = getIntent().getIntExtra("demandeId", -1);
        Demandes data = db.getDemande(demandeId);

        try {
            String typeIdentite = data.getTypeIdentite();
            String numIdentite = data.getNumIdentite();
            String nomEntreprise = data.getNomEntreprise();
            String adresse = data.getAdresse();
            String activityValue = data.getActivity();
            String numFiscale = data.getNumFiscale();
            String ribBanque = data.getRibBanque();
            String etatString = data.getEtat();
            String demandeTitleString = data.getNomEntreprise();

            System.out.println(ribBanque);

            idntType = findViewById(R.id.type_identite);
            idntNum = findViewById(R.id.num_identite);
            company_name = findViewById(R.id.nom_entreprise);
            adress = findViewById(R.id.adresse);
            activity = findViewById(R.id.activite);
            fiscale_num = findViewById(R.id.num_fiscale);
            rib = findViewById(R.id.rib);
            etat = findViewById(R.id.etat);
            demandeTitle = findViewById(R.id.companyName);

            if(data.getEtat().equals("En cours de traitement")) {
                etat.setTextColor(Color.argb(255, 208,109,17));
            } else if(data.getEtat().equals("Demande acceptée")) {
                etat.setTextColor(Color.argb(255, 14, 107, 12));
            } else if(data.getEtat().equals("Demande refusée")) {
                etat.setTextColor(Color.argb(255, 178, 22, 22));
            }

            idntType.setText(typeIdentite);
            idntNum.setText(numIdentite);
            company_name.setText(nomEntreprise);
            adress.setText(adresse);
            activity.setText(activityValue);
            fiscale_num.setText(numFiscale);
            rib.setText(ribBanque);
            etat.setText(etatString);
            demandeTitle.setText(demandeTitleString);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        idntFile = findViewById(R.id.idntFile);
        contratFile = findViewById(R.id.contratFile);
        fiscaleFile = findViewById(R.id.fiscaleFile);
        ribFile = findViewById(R.id.ribFile);

        idntFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(data.getIdentitePath());
            }
        });

        contratFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(data.getContratEndroitPath());
            }
        });

        fiscaleFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(data.getFiscalePath());
            }
        });

        ribFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile(data.getRibPath());
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
