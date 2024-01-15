package com.example.miniprojet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.miniprojet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends Activity {
    private EditText forgotEmail;
    private Button btnResetPassword;
    private TextView linkLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        forgotEmail = findViewById(R.id.forgot_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        linkLogin = findViewById(R.id.link_login);
        firebaseAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Use a regex pattern for basic email validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return email.matches(emailRegex);
    }


    private void resetPassword() {
        String email = forgotEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Veuillez entrer votre adresse e-mail", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!isValidEmail(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Veuillez entrer une adresse e-mail valide", Toast.LENGTH_SHORT).show();
                } else if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Nous vous avons envoyé des instructions pour réinitialiser votre mot de passe!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Échec de l'envoi des instructions. Veuillez vérifier votre adresse e-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
