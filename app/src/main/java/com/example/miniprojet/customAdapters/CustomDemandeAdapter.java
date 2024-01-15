package com.example.miniprojet.customAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miniprojet.R;
import com.example.miniprojet.activities.DetailsActivity;
import com.example.miniprojet.model.Demandes;

import java.util.List;

public class CustomDemandeAdapter extends BaseAdapter {
    private static final String TAG = "Custom Adapater ACTIVITY";
    private Context context;
    private List<Demandes> demandesList;

    public CustomDemandeAdapter(Context context, List<Demandes> demandesList) {
        this.context = context;
        this.demandesList = demandesList;
    }

    @Override
    public int getCount() {
        return demandesList.size();
    }

    @Override
    public Object getItem(int position) {
        return demandesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_demande, parent, false);
        }

        TextView entrepriseName = convertView.findViewById(R.id.entreprise);
        TextView etat = convertView.findViewById(R.id.etat);

        Demandes demande = demandesList.get(position);

        entrepriseName.setText(demande.getNomEntreprise());

        if (demande.getEtat() != null) {
            if (demande.getEtat().equals("En cours de traitement")) {
                etat.setTextColor(Color.argb(255, 208, 109, 17));
            } else if (demande.getEtat().equals("Demande acceptée")) {
                etat.setTextColor(Color.argb(255, 14, 107, 12));
            } else if (demande.getEtat().equals("Demande refusée")) {
                etat.setTextColor(Color.argb(255, 178, 22, 22));
            }
            etat.setText(demande.getEtat());
        } else {
            etat.setText("En cours de traitement");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Demandes clickedDemande = (Demandes) getItem(position);

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("demandes", clickedDemande);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
