package com.marcin_k.gui_development.databasePersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.marcin_k.gui_development.model.FitnessActivity;

import java.util.ArrayList;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class used to manipulate data stored in SQLite fitness activity database,
 *  allows to retrieve all records and add new record to the
 *  database, application loads all the activates during loading
 *  main activity and stores a copy in the array list in
 *  that main activity class, new records are added when users adds them
 *  to the list
 */

public class FitnessActivityDataSource {

    private Context mContext;
    private FitnessActivitySQLiteHelper mFitnessActivitySQLiteHelper;

    public FitnessActivityDataSource(Context context) {
        mContext = context;
        mFitnessActivitySQLiteHelper = new FitnessActivitySQLiteHelper(context);

        //pushes the FitnessActivitySQLiteHelper to initiate database
        //SQLiteDatabase database = mFitnessActivitySQLiteHelper.getReadableDatabase();
        //database.close();
    }

    //returns the arrayList build based on the values stored in db
    public ArrayList<FitnessActivity> read(){
        SQLiteDatabase database = open();
        Cursor cursor = database.query(
                FitnessActivitySQLiteHelper.FITNESS_TABLE,
                new String[]{BaseColumns._ID, FitnessActivitySQLiteHelper.COLUMN_FITNESS_ACTIVITY_TYPE, FitnessActivitySQLiteHelper.COLUMN_FITNESS_DURATION, FitnessActivitySQLiteHelper.COLUMN_FITNESS_TIME, FitnessActivitySQLiteHelper.COLUMN_FITNESS_CALORIES},
                null, //selection
                null, //selection args
                null, //group by
                null, //having
                null  //order
        );

        ArrayList<FitnessActivity> fitnessActivities = new ArrayList<FitnessActivity>();
        if(cursor.moveToFirst()){
            do{
                FitnessActivity fitnessActivity = new FitnessActivity(
                        getStringFromColumnName(cursor, FitnessActivitySQLiteHelper.COLUMN_FITNESS_ACTIVITY_TYPE),
                        getStringFromColumnName(cursor, FitnessActivitySQLiteHelper.COLUMN_FITNESS_DURATION),
                        getStringFromColumnName(cursor, FitnessActivitySQLiteHelper.COLUMN_FITNESS_TIME));
                fitnessActivities.add(fitnessActivity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB(database);
        return fitnessActivities;
    }
    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    //clears all the activities in the list
    public void clearDatabase(){
        mContext.deleteDatabase(FitnessActivitySQLiteHelper.DB_NAME);
    }

    public void create(FitnessActivity fitnessActivity){
        SQLiteDatabase database = open();
        //start of the transaction
        database.beginTransaction();

        //places the objects attributes in correct table columns
        ContentValues fitnessValues = new ContentValues();
        fitnessValues.put(FitnessActivitySQLiteHelper.COLUMN_FITNESS_ACTIVITY_TYPE, fitnessActivity.getActivityType());
        fitnessValues.put(FitnessActivitySQLiteHelper.COLUMN_FITNESS_DURATION, fitnessActivity.getDuration());
        fitnessValues.put(FitnessActivitySQLiteHelper.COLUMN_FITNESS_TIME, fitnessActivity.getTime());
        fitnessValues.put(FitnessActivitySQLiteHelper.COLUMN_FITNESS_CALORIES, fitnessActivity.getCalories());

        //inserts the values into db, (return the primary key - not currently used)
        database.insert(FitnessActivitySQLiteHelper.FITNESS_TABLE, null, fitnessValues);


        //finish transaction
        database.setTransactionSuccessful();
        database.endTransaction();
        closeDB(database);
    }

    //opens the db to write operations
    private SQLiteDatabase open(){
        return mFitnessActivitySQLiteHelper.getWritableDatabase();
    }

    //closes the db
    private void closeDB(SQLiteDatabase database){
        database.close();
    }
}
