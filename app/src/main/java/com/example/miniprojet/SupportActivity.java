package com.example.miniprojet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SupportActivity extends AppCompatActivity {
    EditText objet, contenu;
    Button send, telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        objet = findViewById(R.id.objet);
        contenu = findViewById(R.id.contenu);
        send = findViewById(R.id.send_email);
        telephone = findViewById(R.id.telephone);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                String emailDestinataire = "ThiqaCommerce@gmail.com";
                String emailObjet = objet.getText().toString();
                String emailContenu = contenu.getText().toString();

                if(emailObjet.trim().equals("") || emailContenu.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez compléter les champs manquants", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailDestinataire});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailObjet);
                intent.putExtra(Intent.EXTRA_TEXT, emailContenu);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Choose an Email Client : "));
            }
        });

        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero = telephone.getText().toString();

                Intent intent = new Intent(Intent.ACTION_DIAL);

                intent.setData(Uri.parse("tel:" + numero));

                startActivity(intent);
            }
        });
    }
}
