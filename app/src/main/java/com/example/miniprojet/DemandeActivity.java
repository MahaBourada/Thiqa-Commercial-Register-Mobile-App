package com.example.miniprojet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHandler;

public class DemandeActivity extends AppCompatActivity {
    private EditText typeIdentite, numIdentite, nomEntreprise, adresse, activity, numFiscale, rib;
    private CheckBox declaration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        typeIdentite = findViewById(R.id.type_identite);
        numIdentite = findViewById(R.id.num_identite);
        nomEntreprise = findViewById(R.id.nom_entreprise);
        adresse = findViewById(R.id.adresse);
        activity = findViewById(R.id.activite);
        numFiscale = findViewById(R.id.num_fiscale);
        rib = findViewById(R.id.rib);

        declaration = findViewById(R.id.declaration);
    }

    public void soumettreDemande(View view) {
        if (widgetToString(typeIdentite).trim().equals("") || widgetToString(numIdentite).trim().equals("")
                || widgetToString(nomEntreprise).trim().equals("") || widgetToString(adresse).trim().equals("")
                || widgetToString(activity).trim().equals(("")) || widgetToString(numFiscale).trim().equals("")
                || widgetToString(rib).trim().equals("")) {
            Toast.makeText(getBaseContext(), "Veuillez compléter les champs manquants", Toast.LENGTH_SHORT).show();
        } else if (!declaration.isChecked()) {
            Toast.makeText(getBaseContext(), "Veuillez accepter la déclaration pour soumettre la demande", Toast.LENGTH_SHORT).show();
        } else {
            DemandesHandler db = new DemandesHandler(this);

            db.addDemande(new Demandes(widgetToString(typeIdentite), widgetToInt(numIdentite), widgetToString(nomEntreprise),
                    widgetToString(adresse), widgetToString(activity), widgetToInt(numFiscale), widgetToInt(rib)
                    , "En cours de traitement"));

            Toast.makeText(getBaseContext(), "Demande enregistrée", Toast.LENGTH_SHORT).show();

            Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
            startActivity(myintent);
        }
    }

    public String widgetToString (EditText field) {
        return field.getText().toString();
    }

    public int widgetToInt (EditText field) {
        String stringValue = field.getText().toString();
        return Integer.parseInt(stringValue);
    }
}
