package com.vinako.phonegallery.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Khue on 15/5/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "phones";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "phones";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_CREATED_AT = "createdAt";

    private SQLiteDatabase database;
    private final Context context;

    //database path
    private static String DATABASE_PATH;

    public static final String TAG = "DatabaseOpenHelper";
    //constructor
    public DatabaseOpenHelper(Context context){
        //create new database
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath()+"/databases/";
        Log.d("Database", "Create new instance of DatabaseOpenHelper");
        try{
            // Open your local db as the input stream
            InputStream myInput = context.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = DATABASE_PATH + DATABASE_NAME;

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
                Log.d(TAG, "Buffer" +buffer);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        boolean check = checkDataBase(db);
//        Log.d(TAG, "boolean " + check);
//       // SQLiteDatabase db_Read = null;
//
//        // Creates empty database default system path
////        db = this.getWritableDatabase();
////        db.close();
//        try {
//            if (!check) {
//                copyDataBase();
//                Log.d(TAG, "Copy OK");
//            }
//        } catch (IOException e) {
//            throw new Error("Error copying database");
//        }
        try {
            copyDataBase();
            Log.d(TAG, "Copy OK");
        }catch (Exception  e){
            throw new Error("Error copying database");
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {
            // database doesn't exist yet.
            Log.d(TAG, "can not open new database");
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private boolean checkDataBase(SQLiteDatabase db) {

        return db != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     *
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
            Log.d(TAG, "Buffer" +buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private void copyDataBase(SQLiteDatabase db) throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
            Log.d(TAG, "Buffer" +buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /** open the database */
    public void open() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        Log.d(TAG, "Open Data base, assign db  to database");
    }

    /** close the database */
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    //update createdAt
    public boolean updatePhone (long rowId, int createdAt){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CREATED_AT, createdAt);
        return database.update(TABLE_NAME, values, COLUMN_ID + "=" +rowId, null) >0;
    }

    //getPhone
    public Cursor getPhone( long rowId){
        Cursor mCursor = database.query(true,
                TABLE_NAME,
                new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_LINK,COLUMN_CREATED_AT}
        , COLUMN_ID +"=" + rowId, null, null, null, null, null);
        if(mCursor != null)
            mCursor.moveToFirst();

        return mCursor;
    }

    //get All Phone
    public  Cursor getAllPhone(){
        return database.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_CATEGORY, COLUMN_LINK,COLUMN_CREATED_AT}
                , null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
