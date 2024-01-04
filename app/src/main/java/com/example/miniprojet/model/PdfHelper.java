package com.example.miniprojet.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class PdfHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RegistreCommerce";
    private static final int DATABASE_VERSION = 3;
    protected static final String TABLE_NAME = "PdfFiles";
    private static final String COLUMN_ID = "id";
    protected static final String COLUMN_FILE_URI = "file_uri";

    public PdfHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILE_URI + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void insertFileUri(Uri fileUri) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FILE_URI, fileUri.toString());

        db.insert(TABLE_NAME, null, values);
    }

}
