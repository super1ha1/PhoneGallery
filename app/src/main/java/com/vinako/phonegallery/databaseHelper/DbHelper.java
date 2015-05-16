package com.vinako.phonegallery.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Khue on 15/5/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    public DbHelper(Context context) {
        super(context, PhoneContract.DB_NAME, null, PhoneContract.DB_VERSION);
//
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE phones (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                " category TEXT, " +
                " link TEXT," +
                " createdAt INTEGER " +
                ");";

        db.execSQL(sql);
        Log.d(TAG, "onCreate with SQL: " + sql);

        sql = " INSERT INTO phones VALUES (2,'android1','android','http://i.imgur.com/qhGQWxl.jpg',NULL)";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = "  INSERT INTO `phones` VALUES (3,'windowphone1','windowphone','http://i.imgur.com/PrjPgNb.jpg','') ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = " INSERT INTO `phones` VALUES (4,'windowphone2','windowphone','http://i.imgur.com/DNCsrt0.jpg',NULL)  ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = "  INSERT INTO `phones` VALUES (5,'j2me1','j2me','http://i.imgur.com/2I2M7en.jpg',NULL) ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = "  INSERT INTO `phones` VALUES (6,'j2me2','j2me','http://i.imgur.com/v5DqJLm.jpg','') ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = " INSERT INTO `phones` VALUES (7,'ios1','ios','http://i.imgur.com/YkR4jZc.jpg','')  ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = " INSERT INTO `phones` VALUES (8,'ios2','ios','http://i.imgur.com/Yhf5ibV.jpg',NULL)  ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);

        sql = "  INSERT INTO `phones` VALUES (9,'android2','android','http://i.imgur.com/RHcfcJC.jpg',NULL) ";
        db.execSQL(sql);
        Log.d(TAG, "Insert with SQL: " + sql);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
