package com.example.miniprojet.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DemandesHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "RegistreCommerce";
    private static final String TABLE_DEMANDES = "Demandes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TYPE_IDNT = "typeIdentite";
    private static final String COLUMN_NUM_IDNT = "numIdentite";
    private static final String COLUMN_NOM = "nomEntreprise";
    private static final String COLUMN_ADRESSE = "adresse";
    private static final String COLUMN_ACTIVITY = "activity";
    private static final String COLUMN_NUM_FISC = "numFiscale";
    private static final String COLUMN_RIB = "ribBanque";
    private static final String COLUMN_ETAT = "etat";
    private static final String COLUMN_IDNT_PATH = "idntPath";
    private static final String COLUMN_CONTRAT_PATH = "contratPath";
    private static final String COLUMN_FISCALE_PATH = "fiscalePath";
    private static final String COLUMN_RIB_PATH = "ribPath";

    public DemandesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DEMANDES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE_IDNT + " TEXT,"
                + COLUMN_NUM_IDNT + " TEXT,"
                + COLUMN_NOM + " TEXT,"
                + COLUMN_ADRESSE + " TEXT,"
                + COLUMN_ACTIVITY + " TEXT,"
                + COLUMN_NUM_FISC + " TEXT,"
                + COLUMN_RIB + " TEXT,"
                + COLUMN_ETAT + " TEXT,"
                + COLUMN_IDNT_PATH + " TEXT,"
                + COLUMN_CONTRAT_PATH + " TEXT,"
                + COLUMN_FISCALE_PATH + " TEXT,"
                + COLUMN_RIB_PATH + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEMANDES);
        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
    public void addDemande(final Demandes demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_IDNT, demande.getTypeIdentite());
        values.put(COLUMN_NUM_IDNT, demande.getNumIdentite());
        values.put(COLUMN_NOM, demande.getNomEntreprise());
        values.put(COLUMN_ADRESSE, demande.getAdresse());
        values.put(COLUMN_ACTIVITY, demande.getActivity());
        values.put(COLUMN_NUM_FISC, demande.getNumFiscale());
        values.put(COLUMN_RIB, demande.getRibBanque());
        values.put(COLUMN_ETAT, "En cours de traitement");
        values.put(COLUMN_IDNT_PATH, demande.getIdentitePath());
        values.put(COLUMN_CONTRAT_PATH, demande.getContratEndroitPath());
        values.put(COLUMN_FISCALE_PATH, demande.getFiscalePath());
        values.put(COLUMN_RIB_PATH, demande.getRibPath());

        // Inserting Row
        long rowId = db.insert(TABLE_DEMANDES, null, values);

        // Close database connection
        db.close();

        // Schedule the etat update after 30 seconds using a handler
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateEtat(rowId);
            }
        }, 10000); // 30 seconds delay
    }
    // code to get the single contact
    @SuppressLint("Range")
    public Demandes getDemande(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEMANDES, new String[] { COLUMN_ID,
                        COLUMN_TYPE_IDNT, COLUMN_NUM_IDNT, COLUMN_NOM, COLUMN_ADRESSE,
                        COLUMN_ACTIVITY, COLUMN_NUM_FISC, COLUMN_RIB, COLUMN_ETAT, COLUMN_IDNT_PATH,
                        COLUMN_CONTRAT_PATH, COLUMN_FISCALE_PATH, COLUMN_RIB_PATH}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        Demandes demande = new Demandes();

        if (cursor != null && cursor.moveToFirst()) {
            demande.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            demande.setTypeIdentite(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_IDNT)));
            demande.setNumIdentite(cursor.getString(cursor.getColumnIndex(COLUMN_NUM_IDNT)));
            demande.setNomEntreprise(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
            demande.setAdresse(cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE)));
            demande.setActivity(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITY)));
            demande.setNumFiscale(cursor.getString(cursor.getColumnIndex(COLUMN_NUM_FISC)));
            demande.setRibBanque(cursor.getString(cursor.getColumnIndex(COLUMN_RIB)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_ETAT)));
            demande.setIdentitePath(cursor.getString(cursor.getColumnIndex(COLUMN_IDNT_PATH)));
            demande.setContratEndroitPath(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRAT_PATH)));
            demande.setFiscalePath(cursor.getString(cursor.getColumnIndex(COLUMN_FISCALE_PATH)));
            demande.setRibPath(cursor.getString(cursor.getColumnIndex(COLUMN_RIB_PATH)));

            cursor.close();
        }

        // Close database connection
        db.close();

        return demande;
    }

    // code to get all contacts in a list
    public List<Demandes> getAllDemandes() {
        List<Demandes> contactList = new ArrayList<Demandes>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DEMANDES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Demandes demande = new Demandes();
                demande.setId(Integer.parseInt(cursor.getString(0)));
                demande.setNomEntreprise(cursor.getString(3));
                demande.setEtat(cursor.getString(8));
                // Adding contact to list
                contactList.add(demande);
            } while (cursor.moveToNext());
        }
        // Close database connection
        db.close();
        // return contact list
        return contactList;
    }

    private void updateEtat(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();

        // Use nextInt with bound 2 to get a random value of 0 or 1
        Random random = new Random();
        int randomValue = random.nextInt(2);

        // Map 0 to "Acceptée" and 1 to "Refusée"
        String newEtat = (randomValue == 0) ? "Demande acceptée" : "Demande refusée";

        updateValues.put(COLUMN_ETAT, newEtat);

        // Update the row with the new etat
        db.update(TABLE_DEMANDES, updateValues, COLUMN_ID + " = ?", new String[]{String.valueOf(rowId)});

        // Close database connection
        db.close();
    }

    // code to update the single contact
    public int updateDemande(Demandes demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_IDNT, demande.getTypeIdentite());
        values.put(COLUMN_NUM_IDNT, demande.getNumIdentite());
        values.put(COLUMN_NOM, demande.getNomEntreprise());
        values.put(COLUMN_ADRESSE, demande.getAdresse());
        values.put(COLUMN_ACTIVITY, demande.getActivity());
        values.put(COLUMN_NUM_FISC, demande.getNumFiscale());
        values.put(COLUMN_RIB, demande.getRibBanque());
        values.put(COLUMN_ETAT, demande.getEtat());
        values.put(COLUMN_IDNT_PATH, demande.getIdentitePath());
        values.put(COLUMN_CONTRAT_PATH, demande.getContratEndroitPath());
        values.put(COLUMN_FISCALE_PATH, demande.getFiscalePath());
        values.put(COLUMN_RIB_PATH, demande.getRibPath());
        // updating row
        return db.update(TABLE_DEMANDES, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(demande.getId()) });
    }

    // Deleting single contact
    public void deleteUser(Demandes demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEMANDES, COLUMN_ID + " = ?",
                new String[] { String.valueOf(demande.getId()) });
        db.close();
    }
}
