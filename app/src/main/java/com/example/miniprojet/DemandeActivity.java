package com.example.miniprojet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;

import java.util.List;

public class DemandeActivity extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    private EditText typeIdentite, numIdentite, nomEntreprise, adresse, activity, numFiscale, rib;
    private CheckBox declaration;
    private Button idntFile, contratFile, fiscaleFile, ribFile;
    private List<Uri> pdfList;
    private ArrayAdapter<Uri> pdfListAdapter;
    private static final int PICK_PDF_REQUEST_IDNT = 1; // Use a unique request code
    private static final int PICK_PDF_REQUEST_CONTRAT = 2; // Use a unique request code
    private static final int PICK_PDF_REQUEST_FISCALE = 3; // Use a unique request code
    private static final int PICK_PDF_REQUEST_RIB = 4; // Use a unique request code
    private Uri idntFileUri;
    private Uri contratFileUri;
    private Uri fiscaleFileUri;
    private Uri ribFileUri;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
                startActivity(myintent);
            }
        });

        typeIdentite = findViewById(R.id.type_identite);
        numIdentite = findViewById(R.id.num_identite);
        nomEntreprise = findViewById(R.id.nom_entreprise);
        adresse = findViewById(R.id.adresse);
        activity = findViewById(R.id.activite);
        numFiscale = findViewById(R.id.num_fiscale);
        rib = findViewById(R.id.rib);

        declaration = findViewById(R.id.declaration);

        idntFile = findViewById(R.id.idntFile);
        idntFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_PDF_REQUEST_IDNT);
            }
        });

        contratFile = findViewById(R.id.contratFile);
        contratFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_PDF_REQUEST_CONTRAT);
            }
        });

        fiscaleFile = findViewById(R.id.fiscaleFile);
        fiscaleFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_PDF_REQUEST_FISCALE);
            }
        });

        ribFile = findViewById(R.id.ribFile);
        ribFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_PDF_REQUEST_RIB);
            }
        });
    }
    private int currentFileRequestCode; // Add a field to track the current file request code

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        currentFileRequestCode = requestCode;
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            switch (requestCode) {
                case PICK_PDF_REQUEST_IDNT:
                    idntFileUri = uri;
                    break;
                case PICK_PDF_REQUEST_CONTRAT:
                    contratFileUri = uri;
                    break;
                case PICK_PDF_REQUEST_FISCALE:
                    fiscaleFileUri = uri;
                    break;
                case PICK_PDF_REQUEST_RIB:
                    ribFileUri = uri;
                    break;
            }
            Toast.makeText(this, "Fichier enregistré!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erreur: Veuillez réessayez", Toast.LENGTH_SHORT).show();
        }
    }

    public void soumettreDemande(View view) {
        if (necessaryFieldsError()) {
            Toast.makeText(getBaseContext(), "Veuillez compléter les champs manquants", Toast.LENGTH_SHORT).show();
        } else  if (emptyPdfError()) {
            Toast.makeText(getBaseContext(), "Veuillez insérer les fichiers manquants", Toast.LENGTH_SHORT).show();
        } else if (!declaration.isChecked()) {
            Toast.makeText(getBaseContext(), "Veuillez accepter la déclaration pour soumettre la demande", Toast.LENGTH_SHORT).show();
        } else {
            DemandesHelper db = new DemandesHelper(this);

            String etat = "En cours de traitement";

            db.addDemande(new Demandes(
                    widgetToString(typeIdentite),
                    widgetToInt(numIdentite),
                    widgetToString(nomEntreprise),
                    widgetToString(adresse),
                    widgetToString(activity),
                    widgetToInt(numFiscale),
                    widgetToInt(rib),
                    etat,
                    idntFileUri.toString(),
                    contratFileUri.toString(),
                    fiscaleFileUri.toString(),
                    ribFileUri.toString()
            ));

            Toast.makeText(getBaseContext(), "Demande enregistrée", Toast.LENGTH_SHORT).show();

            Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
            startActivity(myintent);
        }
    }

    public boolean necessaryFieldsError() {
        return widgetToString(typeIdentite).trim().equals("") || widgetToString(numIdentite).trim().equals("")
                || widgetToString(nomEntreprise).trim().equals("") || widgetToString(adresse).trim().equals("")
                || widgetToString(activity).trim().equals(("")) || widgetToString(numFiscale).trim().equals("")
                || widgetToString(rib).trim().equals("");
    }

    public boolean emptyPdfError() {
        return idntFileUri == null || contratFileUri == null
                || fiscaleFileUri == null || ribFileUri == null;
    }

    public String widgetToString (EditText field) {
        return field.getText().toString();
    }

    public int widgetToInt (EditText field) {
        String stringValue = field.getText().toString();
        return Integer.parseInt(stringValue);
    }
}