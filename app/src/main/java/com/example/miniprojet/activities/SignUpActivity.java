package com.example.miniprojet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.miniprojet.R;
import com.example.miniprojet.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends Activity {
    EditText editEmail, editPass, editFirstName, editLastName, phoneNumber, repeatPassword;
    Button btnsinsc;
    TextView conxlink;
    CheckBox termsCheckbox;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference userRef;
    ImageButton conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        conditions = findViewById(R.id.info);
        conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(SignUpActivity.this, ConditionsActivity.class);
                startActivity(myintent);
            }
        });

        FirebaseApp.initializeApp(this);

        termsCheckbox = findViewById(R.id.terms_checkbox);

        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.mdp);
        editFirstName = findViewById(R.id.prenom);
        editLastName = findViewById(R.id.ndf);
        btnsinsc = findViewById(R.id.btnsinsc);
        conxlink = findViewById(R.id.conxlink);
        phoneNumber = findViewById(R.id.num_tel);
        repeatPassword = findViewById(R.id.rptmdp);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");

        conxlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnsinsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editEmail.getText());
                String password = String.valueOf(editPass.getText());
                String repeatPsd = String.valueOf(repeatPassword.getText());
                String firstName = String.valueOf(editFirstName.getText());
                String phoneNumberStr = String.valueOf(phoneNumber.getText());
                String lastName = String.valueOf(editLastName.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer une adresse e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer une adresse e-mail valide", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer un nom de famille", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer un prénom", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumberStr)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer un numéro de téléphone ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer un mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password, repeatPsd)) {
                    Toast.makeText(SignUpActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumberStr )|| TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPsd)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidAlgerianPhoneNumber(phoneNumberStr)) {
                    Toast.makeText(SignUpActivity.this, "Veuillez entrer un numéro de téléphone algérien valide", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!termsCheckbox.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Veuillez accepter les conditions d'utilisation", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a User object with the retrieved information
                User newUser = new User(firstName, lastName, email, password, phoneNumberStr);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

                            String userId = user.getUid();
                            // Save user information to the Realtime Database
                            userRef.child(userId).setValue(newUser);
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Vous êtes déja enregistré avec cet e-mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Use a regex pattern for basic email validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidAlgerianPhoneNumber(String phoneNumber) {
        // Use a regex pattern for Algerian phone number validation
        String phoneRegex = "^(00213|\\+213|0)(5|6|7)[0-9]{8}$";
        return phoneNumber.matches(phoneRegex);
    }
}
