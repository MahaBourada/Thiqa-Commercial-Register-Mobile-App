package com.example.miniprojet;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;
import com.example.miniprojet.model.PdfHelper;

import java.util.List;

public class DemandeActivity extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    private EditText typeIdentite, numIdentite, nomEntreprise, adresse, activity, numFiscale, rib;
    private CheckBox declaration;
    private Button btnChooseFile;
    private List<Uri> pdfList;
    private ArrayAdapter<Uri> pdfListAdapter;

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
        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            PdfHelper pdfHelper = new PdfHelper(this);
            SQLiteDatabase db = pdfHelper.getWritableDatabase();
            pdfHelper.insertFileUri(uri);
            db.close();
            Toast.makeText(this, "File URI saved to database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't work", Toast.LENGTH_SHORT).show();
        }
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
            DemandesHelper db = new DemandesHelper(this);

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
