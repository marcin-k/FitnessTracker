package com.marcin_k.gui_development.databasePersistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class hold the static information about Users database,
 *  defines its structure, allows to access specific columns from that db,
 *  create a database
 *
 */

public class UsersSQLiteHelper extends SQLiteOpenHelper {

    //names of the database
    public static final String USER_DB_NAME = "users.db";
    //database version
    private static final int USER_DB_VERSION = 1;

    //------------------ FitnessActivity table functionality ----------------
    public static final String USER_TABLE = "USERS";
    public static final String COLUMN_USERS_USERNAME = "USERNAME";
    public static final String COLUMN_USERS_PASSWORD = "PASSWORD";

    //query to set up a table for fitness activities
    private static final String CREATE_USERS =
            "CREATE TABLE "+USER_TABLE+ "("+ BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COLUMN_USERS_USERNAME+" TEXT,"+
                    COLUMN_USERS_PASSWORD+" TEXT)";

    public UsersSQLiteHelper(Context context) {
        super(context, USER_DB_NAME, null, USER_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
