package com.example.miniprojet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniprojet.R;
import com.example.miniprojet.customAdapters.ExpandableTextView;

public class FaqActivity extends AppCompatActivity {
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(FaqActivity.this, DashboardActivity.class);
                startActivity(myintent);
            }
        });

        ExpandableTextView expandableTextView1 = findViewById(R.id.expandableTextView1);
        expandableTextView1.setQuestion("• Quels documents fournir ?");
        expandableTextView1.setAnswer("  Une pièce d'identité valable, un contrat de propriété ou de location, une carte fiscale et un chèque bancaire barre.");

        ExpandableTextView expandableTextView2 = findViewById(R.id.expandableTextView2);
        expandableTextView2.setQuestion("• Est-ce le permis de conduire est accepté comme pièce d'identité ?");
        expandableTextView2.setAnswer("  Une carte biométrique ou passeport en cours de validité sont exigés.");

        ExpandableTextView expandableTextView3 = findViewById(R.id.expandableTextView3);
        expandableTextView3.setQuestion("• Quel numéro saisir sur la carte d’identité ?");
        expandableTextView3.setAnswer("  Il faut saisir le numéro en haut à gauche de la carte d’identité constitué de 9 chiffres.");

        ExpandableTextView expandableTextView4 = findViewById(R.id.expandableTextView4);
        expandableTextView4.setQuestion("• Quels sont les différents types d'activités ?");
        expandableTextView4.setAnswer("Commerce de détail, Commerce de gros, Restauration et Hôtellerie, Services, Industrie, BTP (Bâtiment et Travaux Publics), Transport et Logistique, TIC (Technologies de l'Information et de la Communication), Agroalimentaire, Import-Export.");

        ExpandableTextView expandableTextView6 = findViewById(R.id.expandableTextView5);
        expandableTextView6.setQuestion("• Est-ce qu'un compte CCP est accepté ?");
        expandableTextView6.setAnswer("  Seul un compte bancaire est accepté.");

        ExpandableTextView expandableTextView7 = findViewById(R.id.expandableTextView6);
        expandableTextView7.setQuestion("• Comment choisir le nom de son entreprise ?");
        expandableTextView7.setAnswer("  Le nom de l'entreprise doit être unique et clair. Il ne doit pas enfreindre les lois et régulations en vigueur en Algérie.");

    }
}
