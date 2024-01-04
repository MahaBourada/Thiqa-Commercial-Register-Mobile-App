package com.example.miniprojet.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;
public class DemandesHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
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
    private static final String COLUMN_IDNT_URI = "idntUri";
    private static final String COLUMN_CONTRAT_URI = "cntrtUri";
    private static final String COLUMN_FISCALE_URI = "fiscaleUri";
    private static final String COLUMN_RIB_URI = "ribUri";

    public DemandesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DEMANDES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TYPE_IDNT + " TEXT,"
                + COLUMN_NUM_IDNT + " INTEGER," + COLUMN_NOM + " TEXT," + COLUMN_ADRESSE
                + " TEXT," + COLUMN_ACTIVITY + " TEXT," + COLUMN_NUM_FISC + " INTEGER,"
                + COLUMN_RIB + " INTEGER," + COLUMN_ETAT + " TEXT DEFAULT 'ahahahaha'," + COLUMN_IDNT_URI + " TEXT,"
                + COLUMN_CONTRAT_URI + " TEXT," + COLUMN_FISCALE_URI + " TEXT, " + COLUMN_RIB_URI + " TEXT" + ")";
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
    public void addDemande(Demandes demande) {
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
        values.put(COLUMN_IDNT_URI, demande.getIdentiteUri());
        values.put(COLUMN_CONTRAT_URI, demande.getContratEndroitUri());
        values.put(COLUMN_FISCALE_URI, demande.getFiscaleUri());
        values.put(COLUMN_RIB_URI, demande.getRibUri());
        // Inserting Row
        db.insert(TABLE_DEMANDES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single contact
    @SuppressLint("Range")
    public Demandes getDemande(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEMANDES, new String[] { COLUMN_ID,
                        COLUMN_TYPE_IDNT, COLUMN_NUM_IDNT, COLUMN_NOM, COLUMN_ADRESSE,
                        COLUMN_ACTIVITY, COLUMN_NUM_FISC, COLUMN_RIB, COLUMN_ETAT, COLUMN_IDNT_URI,
                        COLUMN_CONTRAT_URI, COLUMN_FISCALE_URI, COLUMN_RIB_URI}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        Demandes demande = new Demandes();

        if (cursor != null && cursor.moveToFirst()) {
            demande.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            demande.setTypeIdentite(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_IDNT)));
            demande.setNumIdentite(cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_IDNT)));
            demande.setNomEntreprise(cursor.getString(cursor.getColumnIndex(COLUMN_NOM)));
            demande.setAdresse(cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE)));
            demande.setActivity(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITY)));
            demande.setNumFiscale(cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_FISC)));
            demande.setRibBanque(cursor.getInt(cursor.getColumnIndex(COLUMN_RIB)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_ETAT)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_IDNT_URI)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_CONTRAT_URI)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_FISCALE_URI)));
            demande.setEtat(cursor.getString(cursor.getColumnIndex(COLUMN_RIB_URI)));

            cursor.close();
        }

        return demande;
    }

    // Add a method to get a user by username
    /* public Demandes getDemandeByNom(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEMANDES, new String[]{COLUMN_ID, COLUMN_NOM, COLUMN_ETAT},
                COLUMN_NOM + "=?", new String[]{username}, null, null, null, null);

        Demandes demande = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                demande = new Demandes(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
            }
            cursor.close();
        }

        return demande;
    } */

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
        // return contact list
        return contactList;
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
        values.put(COLUMN_IDNT_URI, demande.getIdentiteUri());
        values.put(COLUMN_CONTRAT_URI, demande.getContratEndroitUri());
        values.put(COLUMN_FISCALE_URI, demande.getFiscaleUri());
        values.put(COLUMN_RIB_URI, demande.getRibUri());
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
