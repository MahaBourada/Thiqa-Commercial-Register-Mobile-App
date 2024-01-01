package com.example.miniprojet.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
public class DemandesHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RegistreCommerce";
    private static final String TABLE_DEMANDES = "Demandes";
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE_IDNT = "typeIdentite";
    private static final String KEY_NUM_IDNT = "numIdentite";
    private static final String KEY_NOM = "nomEntreprise";
    private static final String KEY_ADRESSE = "adresse";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_NUM_FISC = "numFiscale";
    private static final String KEY_RIB = "ribBanque";
    private static final String KEY_ETAT = "etat";

    public DemandesHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DEMANDES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE_IDNT + " TEXT,"
                + KEY_NUM_IDNT + " INTEGER," + KEY_NOM + " TEXT," + KEY_ADRESSE
                + " TEXT," + KEY_ACTIVITY + " TEXT," + KEY_NUM_FISC + " INTEGER,"
                + KEY_RIB + " INTEGER," + KEY_ETAT + " TEXT" + ")";
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
        values.put(KEY_TYPE_IDNT, demande.getTypeIdentite());
        values.put(KEY_NUM_IDNT, demande.getNumIdentite());
        values.put(KEY_NOM, demande.getNomEntreprise());
        values.put(KEY_ADRESSE, demande.getAdresse());
        values.put(KEY_ACTIVITY, demande.getActivity());
        values.put(KEY_NUM_FISC, demande.getNumFiscale());
        values.put(KEY_RIB, demande.getRibBanque());
        values.put(KEY_ETAT, demande.getEtat());
        // Inserting Row
        db.insert(TABLE_DEMANDES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // code to get the single contact
    Demandes getDemande(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEMANDES, new String[] { KEY_ID,
                        KEY_NOM, KEY_ETAT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Demandes user = new Demandes(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), cursor.getString(8));
        // return contact
        return user;
    }

    // Add a method to get a user by username
    public Demandes getDemandeByNom(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DEMANDES, new String[]{KEY_ID, KEY_NOM, KEY_ETAT},
                KEY_NOM + "=?", new String[]{username}, null, null, null, null);

        Demandes demande = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                demande = new Demandes(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)), cursor.getString(8));
            }
            cursor.close();
        }

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
                Demandes contact = new Demandes();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setNomEntreprise(cursor.getString(3));
                contact.setEtat(cursor.getString(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateDemande(Demandes demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_IDNT, demande.getTypeIdentite());
        values.put(KEY_NUM_IDNT, demande.getNumIdentite());
        values.put(KEY_NOM, demande.getNomEntreprise());
        values.put(KEY_ADRESSE, demande.getAdresse());
        values.put(KEY_ACTIVITY, demande.getActivity());
        values.put(KEY_NUM_FISC, demande.getNumFiscale());
        values.put(KEY_RIB, demande.getRibBanque());
        values.put(KEY_ETAT, demande.getEtat());
        // updating row
        return db.update(TABLE_DEMANDES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(demande.getId()) });
    }

    // Deleting single contact
    public void deleteUser(Demandes demande) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEMANDES, KEY_ID + " = ?",
                new String[] { String.valueOf(demande.getId()) });
        db.close();
    }
}
