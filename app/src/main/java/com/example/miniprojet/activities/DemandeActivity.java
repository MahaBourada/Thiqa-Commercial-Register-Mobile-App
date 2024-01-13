package com.example.miniprojet.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.R;
import com.example.miniprojet.model.Demandes;
import com.example.miniprojet.model.DemandesHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DemandeActivity extends AppCompatActivity {
    private EditText numIdentite, nomEntreprise, adresse, numFiscale, rib;
    private CheckBox declaration;
    private Button idntFile, contratFile, fiscaleFile, ribFile, submit;
    private static final int PICK_PDF_REQUEST_IDNT = 1;
    private static final int PICK_PDF_REQUEST_CONTRAT = 2;
    private static final int PICK_PDF_REQUEST_FISCALE = 3;
    private static final int PICK_PDF_REQUEST_RIB = 4;
    private Uri idntFileUri;
    private Uri contratFileUri;
    private Uri fiscaleFileUri;
    private Uri ribFileUri;
    private ImageButton back;
    String selectedTypeIdentite;
    String selectedActivite;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        FirebaseDatabase.getInstance();

        numIdentite = findViewById(R.id.num_identite);
        nomEntreprise = findViewById(R.id.nom_entreprise);
        adresse = findViewById(R.id.adresse);
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

        //Firebase configuration
        submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userId = firebaseUser.getUid();


                // Example for saving demandes data
                DatabaseReference demandesRef = FirebaseDatabase.getInstance().getReference("demandes");
                String demandeId = demandesRef.push().getKey();


                if(selectedTypeIdentite == null) {
                    Toast.makeText(getBaseContext(), "Veuillez selectionner un type de pièce d'identité", Toast.LENGTH_SHORT).show();
                } else if(widgetToString(numIdentite).trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer un N° de pièce d'identité", Toast.LENGTH_SHORT).show();
                } else if(widgetToString(nomEntreprise).trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer un nom d'entreprise", Toast.LENGTH_SHORT).show();
                } else if(widgetToString(adresse).trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer une adresse commerciale", Toast.LENGTH_SHORT).show();
                } else if(selectedActivite == null) {
                    Toast.makeText(getBaseContext(), "Veuillez selectionner un type d'activité", Toast.LENGTH_SHORT).show();
                } else if (widgetToString(numFiscale).trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer un N° d'identification fiscale", Toast.LENGTH_SHORT).show();
                } else if (widgetToString(rib).trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer un RIB", Toast.LENGTH_SHORT).show();
                } else if (idntFileUri == null) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer le justificatif de pièce d'identité", Toast.LENGTH_SHORT).show();
                } else if (contratFileUri == null) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer le justificatif de contrat de bail/propriété", Toast.LENGTH_SHORT).show();
                } else if (fiscaleFileUri == null) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer le justificatif de carte fiscale", Toast.LENGTH_SHORT).show();
                } else if (ribFileUri == null) {
                    Toast.makeText(getBaseContext(), "Veuillez insérer le justificatif de RIB", Toast.LENGTH_SHORT).show();
                } else if (widgetToString(numIdentite).length() < 9) {
                    handleValidationError("Veuillez insérer les 9 chiffres de la pièce d'identité", numIdentite);
                } else if (widgetToString(numFiscale).length() < 14) {
                    handleValidationError("Veuillez insérer les 14 chiffres du N° d'identification fiscale", numFiscale);
                } else if (widgetToString(rib).length() < 24) {
                    handleValidationError("Veuillez insérer les 24 chiffres du RIB", rib);
                } else if (!declaration.isChecked()) {
                    Toast.makeText(getBaseContext(), "Veuillez accepter la déclaration pour soumettre la demande", Toast.LENGTH_SHORT).show();
                } else {
                    Demandes demandes = new Demandes(
                            userId,
                            selectedTypeIdentite,
                            widgetToString(numIdentite),
                            widgetToString(nomEntreprise),
                            widgetToString(adresse),
                            selectedActivite,
                            widgetToString(numFiscale),
                            widgetToString(rib),
                            idntFileUri.toString(),
                            contratFileUri.toString(),
                            fiscaleFileUri.toString(),
                            ribFileUri.toString(),
                            "En cours de traitement");  // Linking to user through userID
                    demandesRef.child(demandeId).setValue(demandes);

                    Toast.makeText(DemandeActivity.this, "Demande submitted successfully", Toast.LENGTH_SHORT).show();
                    Log.d("Debug", "Demande submitted successfully " + demandeId + " USER ID " + userId);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Update the etat randomly
                            String newEtat = getRandomEtat();

                            // Update the etat in the Firebase database
                            demandesRef.child(demandeId).child("etat").setValue(newEtat);

                            Log.d("Debug", "Etat updated to: " + newEtat);
                        }
                    }, 10000); // 10 seconds delay

                    Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
                    startActivity(myintent);
                }
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(DemandeActivity.this, DashboardActivity.class);
                startActivity(myintent);
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

    private String getRandomEtat() {
        // Randomly choose between "accepted" and "rejected"
        Random random = new Random();
        boolean isAccepted = random.nextBoolean();

        if (isAccepted) {
            return "Demande acceptée";
        } else {
            return "Demande refusée";
        }
    }

    private void handleValidationError(String errorMessage, View focusedView) {
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
        if (focusedView instanceof EditText) {
            ((EditText) focusedView).requestFocus();
        }
    }

    public String widgetToString (EditText field) {
        return field.getText().toString();
    }

}