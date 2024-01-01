package com.example.miniprojet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FaqActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ExpandableTextView expandableTextView1 = findViewById(R.id.expandableTextView1);
        expandableTextView1.setQuestion("Quels documents fournir ?");
        expandableTextView1.setAnswer("Reponse 1");

        ExpandableTextView expandableTextView2 = findViewById(R.id.expandableTextView2);
        expandableTextView2.setQuestion("Est-ce le permis est accepté comme pièce d'identité ?");
        expandableTextView2.setAnswer("Reponse 2");

        ExpandableTextView expandableTextView3 = findViewById(R.id.expandableTextView3);
        expandableTextView3.setQuestion("Quel numéro saisir sur la carte d’identité ?");
        expandableTextView3.setAnswer("Reponse 3");

        ExpandableTextView expandableTextView4 = findViewById(R.id.expandableTextView4);
        expandableTextView4.setQuestion("Quels sont les différents types d'activités ?");
        expandableTextView4.setAnswer("Reponse 4");

        ExpandableTextView expandableTextView5 = findViewById(R.id.expandableTextView5);
        expandableTextView5.setQuestion("Dans quel cas fournit-on le contrat de bail et le contrat de propriété ?");
        expandableTextView5.setAnswer("Reponse 5");

        ExpandableTextView expandableTextView6 = findViewById(R.id.expandableTextView6);
        expandableTextView6.setQuestion("Est-ce qu'un compte CCP est accepté ?");
        expandableTextView6.setAnswer("Reponse 6");

        ExpandableTextView expandableTextView7 = findViewById(R.id.expandableTextView7);
        expandableTextView7.setQuestion("Quel justificatif à fournir pour le compte banquaire ?");
        expandableTextView7.setAnswer("Reponse 7");

    }
}
