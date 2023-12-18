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

import java.util.List;

public class CustomDemandeAdapter extends BaseAdapter {
    private Context context;
    private String[] entreprises;
    private String[] etats;

    public CustomDemandeAdapter(Context context, String[] entreprises, String[] etats) {
        this.context = context;
        this.entreprises = entreprises;
        this.etats = etats;
    }

    @Override
    public int getCount() {
        return entreprises.length;
    }

    @Override
    public Object getItem(int position) {
        return entreprises[position];
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

        // Set social media name
        entrepriseName.setText(entreprises[position]);

        // Set social media link
        etat.setText(etats[position]);

        // Set onClickListener to open social media link
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMediaLink(etats[position]);
            }
        });

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
