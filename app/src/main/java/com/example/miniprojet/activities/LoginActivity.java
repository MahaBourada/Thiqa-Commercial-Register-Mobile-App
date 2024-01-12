package com.example.miniprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText editEmail, editPass;
    Button Connexion;
    TextView sinscrire, forgotPassword;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.mdp);
        Connexion = findViewById(R.id.btnconx);
        sinscrire = findViewById(R.id.sinscrirelink);
        forgotPassword = findViewById(R.id.forgotPassword);

        sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        Connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editEmail.getText());
                password = String.valueOf(editPass.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Entrer Un Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Entrer Un Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Save user's UID to SharedPreferences upon successful login
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        String uid = user.getUid();
//                                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = preferences.edit();
//                                        editor.putString("uid", uid);
//                                        editor.apply();
                                        Log.w("IdLogin", uid, task.getException());
                                        Toast.makeText(LoginActivity.this, "Id is saved on login preferences", Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(i);
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentification échouée: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    Exception exception = task.getException();
                                    if (exception != null) {
                                        // Log or display the exception message
                                        String errorMessage = exception.getMessage();
                                        // Handle the error message
                                    }
                                }
                            }
                        });
            }
        });
    }
}
