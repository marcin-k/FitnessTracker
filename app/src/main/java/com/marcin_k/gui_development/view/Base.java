package com.marcin_k.gui_development.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.marcin_k.gui_development.databasePersistence.FitnessActivityDataSource;
import com.marcin_k.gui_development.model.FitnessActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Footprint for MainActivity, contains set of methods
 * that do not directly impact UI elements
 */

public class Base extends AppCompatActivity {

    //used as a reference for adding new records
    protected static final int SECOND_ACTIVITY_RESULT_CODE = 0;

    //for debugging purposes
    protected static final String TAG = MainActivity.class.getSimpleName();

    //reference used for intends
    protected static final String ACTIVITY_PROGRESS = "ACTIVITY_PROGRESS";
    protected static final String ACTIVITY_PROGRESS_LABEL = "ACTIVITY_PROGRESS_LABEL";

    //ArrayList that contains list of all activities entered by user
    protected static ArrayList<FitnessActivity> mFitnessActivities = new ArrayList<FitnessActivity>();



    //----------Calculate calorie burned for Activity-------------------------------------
    public static int getCalorieCountForActivity(String activity){
        int count=0;
        for(FitnessActivity fitnessActivity:mFitnessActivities){
            if(fitnessActivity.getActivityType().equals(activity))
            count+=fitnessActivity.getCalories();
        }
        return count;
    }

    //----------Calculate total number of calories burned---------------------------------
    public static int getTotalCalorieCount(){
        int count=0;
        for(FitnessActivity fitnessActivity:mFitnessActivities){
            count+=fitnessActivity.getCalories();
        }
        return count;
    }

    //----------Method to jump to add new record activity---------------------------------
    public void startAddingNewRecords(){
        Intent intent = new Intent(this, AddNewRecord.class);
        startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
    }
    //---------------Method to jump to Settings activity-----------------------------------
    public void startSettingsActivity(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    //--------------Method to jump to ProgressView activity--------------------------------
    public void startProgressViewActivity(){
        Intent intent = new Intent(this, ProgressView.class);
        intent.putExtra(ACTIVITY_PROGRESS, mFitnessActivities);
        intent.putExtra(ACTIVITY_PROGRESS_LABEL, "All Activities");
        startActivity(intent);
    }
    //--------------Gets a current date----------------------------------------------------
    public String getTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(calendar.getTime());
    }
    //------------Gets ProgressView of a given type of activity----------------------------
    public void startProgressViewForSelectedActivity(String activity){
        Intent intent = new Intent(this, ProgressView.class);
        ArrayList<FitnessActivity> mActivities = new ArrayList<FitnessActivity>();

        for(FitnessActivity fitnessActivity:mFitnessActivities){
            if (fitnessActivity.getActivityType().equals(activity)){
                mActivities.add(fitnessActivity);
            }
        }
        intent.putExtra(ACTIVITY_PROGRESS_LABEL, activity+"");
        intent.putExtra(ACTIVITY_PROGRESS, mActivities);
        startActivity(intent);
    }
    //----------Adds new record to a list-------------------------------------------------
    public void addNewActivity(String activityType, String activityTDuration){
        FitnessActivity activity = new FitnessActivity(activityType, activityTDuration, getTodaysDate());
        //adds to db
        FitnessActivityDataSource fitnessActivityDataSource = new FitnessActivityDataSource(this);
        fitnessActivityDataSource.create(activity);
        //adds to arrayList
        mFitnessActivities.add(activity);
    }
}
