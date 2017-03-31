package com.marcin_k.gui_development.databasePersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.marcin_k.gui_development.model.User;

import java.util.ArrayList;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class used to manipulate data stored in SQLite users database,
 *  allows to retrieve all records and add new record to the
 *  database, application loads all the users during loading
 *  login screen and stores a copy in the array list in
 *  settings activity class, once new user account is created
 *  new entry is added to the database
 */

public class UsersDataSource {
    private Context mContext;
    private UsersSQLiteHelper mUsersSQLiteHelper;

    public UsersDataSource(Context context) {
        mContext = context;
        mUsersSQLiteHelper = new UsersSQLiteHelper(context);
    }

    //returns the arrayList build based on the values stored in db
    public ArrayList<User> read(){
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                UsersSQLiteHelper.USER_TABLE,
                new String[]{BaseColumns._ID, UsersSQLiteHelper.COLUMN_USERS_USERNAME, UsersSQLiteHelper.COLUMN_USERS_PASSWORD},
                null, //selection
                null, //selection args
                null, //group by
                null, //having
                null  //order
        );

        ArrayList<User> users = new ArrayList<User>();
        if(cursor.moveToFirst()){
            do{
                User user = new User(
                        getStringFromColumnName(cursor, UsersSQLiteHelper.COLUMN_USERS_USERNAME),
                        getStringFromColumnName(cursor, UsersSQLiteHelper.COLUMN_USERS_PASSWORD));
                users.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB(database);
        return users;
    }
    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void create(User user){
        SQLiteDatabase database = open();
        //start of the transaction
        database.beginTransaction();

        //places the objects attributes in correct table columns
        ContentValues userValues = new ContentValues();
        userValues.put(UsersSQLiteHelper.COLUMN_USERS_USERNAME, user.getUsername());
        userValues.put(UsersSQLiteHelper.COLUMN_USERS_PASSWORD, user.getPassword());

        //inserts the values into db, (return the primary key - not currently used)
        database.insert(UsersSQLiteHelper.USER_TABLE, null, userValues);

        //finish transaction
        database.setTransactionSuccessful();
        database.endTransaction();
        closeDB(database);
    }

    //opens the db to write operations
    private SQLiteDatabase open(){
        return mUsersSQLiteHelper.getWritableDatabase();
    }

    //closes the db
    private void closeDB(SQLiteDatabase database){
        database.close();
    }
}

