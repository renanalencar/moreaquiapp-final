package br.com.renanalencar.moreaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EstateData extends SQLiteOpenHelper {
    private static EstateData sInstance;

    // Database Info
    private static final String DATABASE_NAME = "estatesDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NAME = "estates";

    // Post Table Columns
    private static final String _ID = "id";
    private static final String TYPE = "type";
    private static final String SIZE = "size";
    private static final String STATUS = "status";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PHONE = "phone";

    public static synchronized EstateData getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new EstateData(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private EstateData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE + " TEXT NOT NULL, "
                + SIZE + " TEXT NOT NULL, "
                + STATUS + " TEXT, "
                + LATITUDE + " DOUBLE, "
                + LONGITUDE + " DOUBLE, "
                + PHONE + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Insert a post into the database
    public void addEstate(LocationEstate estate) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(TYPE, estate.TYPE);
            values.put(SIZE, estate.SIZE);
            values.put(STATUS, estate.STATUS);
            values.put(LATITUDE, estate.LATITUDE);
            values.put(LONGITUDE, estate.LONGITUDE);
            values.put(PHONE, estate.PHONE);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to add estate to database");
        } finally {
            db.endTransaction();
        }
    }

    // Get all posts in the database
    public List<LocationEstate> getAllEstates() {
        List<LocationEstate> estates = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_NAME);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    LocationEstate newEstate = new LocationEstate(cursor.getString(cursor.getColumnIndex(TYPE)),
                            cursor.getString(cursor.getColumnIndex(SIZE)),
//                            Integer.parseInt(cursor.getString(cursor.getColumnIndex(PHONE))),
                            cursor.getString(cursor.getColumnIndex(PHONE)),
                            cursor.getString(cursor.getColumnIndex(STATUS)),
                            Double.parseDouble(cursor.getString(cursor.getColumnIndex(LATITUDE))),
                            Double.parseDouble(cursor.getString(cursor.getColumnIndex(LONGITUDE))));
                    estates.add(newEstate);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get estates from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return estates;
    }


}
