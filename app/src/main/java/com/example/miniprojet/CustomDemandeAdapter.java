package com.example.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miniprojet.model.Demandes;

import java.util.List;

public class CustomDemandeAdapter extends BaseAdapter {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_demande, parent, false);
        }

        TextView entrepriseName = convertView.findViewById(R.id.entreprise);
        TextView etat = convertView.findViewById(R.id.etat);

        Demandes demande = demandesList.get(position);

        // Set entreprise name
        entrepriseName.setText(demande.getNomEntreprise());

        // Set etat
        etat.setText(demande.getEtat()); // Display the status directly

        return convertView;
    }
}
