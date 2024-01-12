package com.example.miniprojet.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.R;
import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class DemandeActivity extends AppCompatActivity {
    private EditText typeIdentite, numIdentite, nomEntreprise, adresse, activity, numFiscale, rib;
    private CheckBox declaration;
    private Button idntFile, contratFile, fiscaleFile, ribFile;
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
    private ListView dropdownListView1, dropdownListView2;
    private AutoCompleteTextView textField;
    String selectedTypeIdentite;
    String selectedActivite;

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

        //typeIdentite = findViewById(R.id.type_identite);
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

        TextInputLayout textInputType = findViewById(R.id.textInputType);
        AutoCompleteTextView type_identite = findViewById(R.id.type_identite);

        // Sample data for the dropdown
        List<String> itemsType = Arrays.asList("Passeport", "Carte d'identité");

        // Set up the adapter for AutoCompleteTextView
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemsType);
        type_identite.setAdapter(adapterType);

        type_identite.setOnItemClickListener((parent, view, position, id) -> {
            String selectedValue = (String) parent.getItemAtPosition(position);
            selectedTypeIdentite = selectedValue;
        });

        TextInputLayout textInputActivite = findViewById(R.id.textInputActivite);
        AutoCompleteTextView activite = findViewById(R.id.activite);

        // Sample data for the dropdown
        List<String> itemsActivite = Arrays.asList("Commerce de détail", "Commerce de gros", "Restauration et Hôtellerie", "Services", "Industrie",
                "BTP (Bâtiment et Travaux Publics)", "Transport et Logistique", "TIC (Technologies de l'Information et de la Communication)",
                "Agroalimentaire", "Import-Export");

        // Set up the adapter for AutoCompleteTextView
        ArrayAdapter<String> adapterActivite = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemsActivite);
        activite.setAdapter(adapterActivite);

        activite.setOnItemClickListener((parent, view, position, id) -> {
            String selectedValue = (String) parent.getItemAtPosition(position);
            selectedActivite = selectedValue;
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
        } else if (widgetToString(numIdentite).length() < 9) {
            handleValidationError("Veuillez insérer les 9 chiffres de la pièce d'identité", numIdentite);
        } else if (widgetToString(numFiscale).length() < 14) {
            handleValidationError("Veuillez insérer les 14 chiffres du N° d'identification fiscale", numFiscale);
        } else if (widgetToString(rib).length() < 24) {
            handleValidationError("Veuillez insérer les 24 chiffres du RIB", rib);
        } else if (emptyPdfError()) {
            Toast.makeText(getBaseContext(), "Veuillez insérer les fichiers manquants", Toast.LENGTH_SHORT).show();
        } else if (!declaration.isChecked()) {
            Toast.makeText(getBaseContext(), "Veuillez accepter la déclaration pour soumettre la demande", Toast.LENGTH_SHORT).show();
        } else {
            DemandesHelper db = new DemandesHelper(this);

            db.addDemande(new Demandes(
                    selectedTypeIdentite,
                    widgetToString(numIdentite),
                    widgetToString(nomEntreprise),
                    widgetToString(adresse),
                    selectedActivite,
                    widgetToString(numFiscale),
                    rib.getText().toString(),
                    idntFileUri.toString(),
                    contratFileUri.toString(),
                    fiscaleFileUri.toString(),
                    ribFileUri.toString()
            ));

            Toast.makeText(getBaseContext(), "Demande enregistrée", Toast.LENGTH_SHORT).show();

            Log.d("Debug", "TYPE IDENTITE: " + selectedTypeIdentite);

            Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
            startActivity(myintent);
        }
    }

    private void handleValidationError(String errorMessage, View focusedView) {
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
        if (focusedView instanceof EditText) {
            ((EditText) focusedView).requestFocus();
        }
    }

    public boolean necessaryFieldsError() {
        return selectedTypeIdentite.trim().equals("") || widgetToString(numIdentite).trim().equals("")
                || widgetToString(nomEntreprise).trim().equals("") || widgetToString(adresse).trim().equals("")
                || selectedActivite.trim().equals(("")) || widgetToString(numFiscale).trim().equals("")
                || widgetToString(rib).trim().equals("");
    }

    public boolean emptyPdfError() {
        return idntFileUri == null || contratFileUri == null
                || fiscaleFileUri == null || ribFileUri == null;
    }

    public String widgetToString (EditText field) {
        return field.getText().toString();
    }
}