package com.marcin_k.gui_development.databasePersistence;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class hold the static information about FitnessActivities database,
 *  defines its structure, allows to access specific columns from that db,
 *  create a database
 *
 */

public class FitnessActivitySQLiteHelper extends SQLiteOpenHelper{

    //names of the database
    public static final String DB_NAME = "activities.db";
    //database version
    private static final int DB_VERSION = 1;

    //------------------ FitnessActivity table functionality ----------------
    public static final String FITNESS_TABLE = "FITNESS";
    public static final String COLUMN_FITNESS_ACTIVITY_TYPE = "ACTIVITY_TYPE";
    public static final String COLUMN_FITNESS_DURATION = "DURATION";
    public static final String COLUMN_FITNESS_TIME = "TIME";
    public static final String COLUMN_FITNESS_CALORIES = "CALORIES";
    //query to set up a table for fitness activities
    private static final String CREATE_FITNESS =
            "CREATE TABLE "+FITNESS_TABLE+ "("+ BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_FITNESS_ACTIVITY_TYPE+" TEXT,"+
            COLUMN_FITNESS_DURATION+" TEXT,"+
            COLUMN_FITNESS_TIME+" TEXT,"+
            COLUMN_FITNESS_CALORIES+" INTEGER)";



    //will create a database if doest exist and check the version if upgrade is required
    public FitnessActivitySQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //called to create database (only once)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FITNESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
