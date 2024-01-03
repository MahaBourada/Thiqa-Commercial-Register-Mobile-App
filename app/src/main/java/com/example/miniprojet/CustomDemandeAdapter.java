package com.example.miniprojet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
        etat.setText(demande.getEtat());

        return convertView;
    }

    private void openSocialMediaLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

        // Check if there's an app to handle the intent
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            context.startActivity(intent);
        } else {
            // If no app can handle the intent, open the link in the browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            context.startActivity(browserIntent);
        }
    }
}
