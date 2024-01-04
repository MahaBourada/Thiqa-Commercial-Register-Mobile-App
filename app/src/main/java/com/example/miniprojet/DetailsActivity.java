package com.example.miniprojet;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;

public class DetailsActivity extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 123;
    private static final int OPEN_DOCUMENT_REQUEST_CODE = 456;
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
            int numIdentiteValue = data.getNumIdentite();
            int numFiscaleValue = data.getNumFiscale();
            int ribBanqueValue = data.getRibBanque();

            String typeIdentite = data.getTypeIdentite();
            String numIdentite = String.valueOf(numIdentiteValue);
            String nomEntreprise = data.getNomEntreprise();
            String adresse = data.getAdresse();
            String activityValue = data.getActivity();
            String numFiscale = String.valueOf(numFiscaleValue);
            String ribBanque = String.valueOf(ribBanqueValue);
            String etatString = data.getEtat();  // Assuming etat is a String
            String demandeTitleString = data.getNomEntreprise();

            idntType = findViewById(R.id.type_identite);
            idntNum = findViewById(R.id.num_identite);
            company_name = findViewById(R.id.nom_entreprise);
            adress = findViewById(R.id.adresse);
            activity = findViewById(R.id.activite);
            fiscale_num = findViewById(R.id.num_fiscale);
            rib = findViewById(R.id.rib);
            etat = findViewById(R.id.etat);
            demandeTitle = findViewById(R.id.companyName);

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
    }
}
