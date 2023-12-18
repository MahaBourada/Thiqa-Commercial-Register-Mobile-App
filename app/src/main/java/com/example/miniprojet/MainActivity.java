package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] entreprises = new String[] {
            "Hamouda", "Batata", "Samta"
    };

    String[] etats = new String[] {
            "En cours de traitement", "Acceptée", "Refusée"
    };

    private Button creer_demande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.demande);
        CustomDemandeAdapter adapter = new CustomDemandeAdapter(this, entreprises, etats);
        listView.setAdapter(adapter);

        creer_demande = findViewById(R.id.creer);
        creer_demande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, DemandeActivity.class);
                startActivity(myintent);
            }
        });
    }

    public void clickMsg (View view) {
        Toast.makeText(getBaseContext(), "Clicked on client support", Toast.LENGTH_SHORT).show();
    }
}