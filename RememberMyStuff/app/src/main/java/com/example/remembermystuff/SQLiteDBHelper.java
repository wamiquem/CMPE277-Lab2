package com.example.remembermystuff;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StuffsDB";
    private static final String TABLE_NAME = "Stuffs";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_LOCATION};

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Stuffs ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "location TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Stuff stuff) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(stuff.getId()) });
        db.close();
    }

    public Stuff getStuff(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Stuff stuff = new Stuff();
        stuff.setId(Integer.parseInt(cursor.getString(0)));
        stuff.setName(cursor.getString(1));
        stuff.setLocation(cursor.getString(2));

        return stuff;
    }

    public List<Stuff> allStuffs() {

        List<Stuff> stuffs = new LinkedList<Stuff>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Stuff stuff;

        if (cursor.moveToFirst()) {
            do {
                stuff = new Stuff();
                stuff.setId(Integer.parseInt(cursor.getString(0)));
                stuff.setName(cursor.getString(1));
                stuff.setLocation(cursor.getString(2));
                stuffs.add(stuff);
            } while (cursor.moveToNext());
        }

        return stuffs;
    }

    public void addStuff(Stuff stuff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stuff.getName());
        values.put(KEY_LOCATION, stuff.getLocation());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateStuff(Stuff stuff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, stuff.getName());
        values.put(KEY_LOCATION, stuff.getLocation());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(stuff.getId()) });

        db.close();

        return i;
    }
}
