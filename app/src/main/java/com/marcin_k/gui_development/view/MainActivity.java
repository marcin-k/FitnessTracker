package com.marcin_k.gui_development.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;

//CircularProgressBar library : https://github.com/lopspower/CircularProgressBar
import com.marcin_k.gui_development.databasePersistence.FitnessActivityDataSource;
import com.marcin_k.gui_development.model.FitnessActivity;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Entry point of the application, main screen
 * used to present progress for all activities
 * and links to see individual listView, add new
 * records and change settings
 */

public class MainActivity extends Base {

    //variable references for ui elements:
    private ImageView mCaloriesIcon;
    private Button mButton;
    private ImageView mGolf;
    private ImageView mTennis;
    private ImageView mSwim;
    private ImageView mBike;
    private ImageView mHike;
    private ImageView mRun;
    private ImageView mSettings;
    private CircularProgressBar mCaloriesProgress;
    private static ProgressBar mTennisProgress;
    private static ProgressBar mSwimmingProgress;
    private static ProgressBar mHikingProgress;
    private static ProgressBar mRunningProgress;
    private static ProgressBar mGolfProgress;
    private static ProgressBar mCyclingProgress;
    private TextView mWelcomeLogo;
    private TextView mCaloriesBurned;

    //Shared preferences are usd to store info about the theme, goal and users name
    public static final String PREFS_FILE = "com.marcin_k.gui_development.preferences";
    public static final String THEME = "THEME";
    public static final String USERNAME = "USERNAME";
    public static final String GOAL = "GOAL";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //restores the arrayList from db
        FitnessActivityDataSource fitnessActivityDataSource = new FitnessActivityDataSource(this);
        Base.mFitnessActivities  =  fitnessActivityDataSource.read();

        //restores the shared preferences for settings
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        SettingsController.getInstance().setGoal(mSharedPreferences.getString(GOAL, "0.5 kg"));
        SettingsController.getInstance().changeBackground(mSharedPreferences.getString(THEME, "blue"));

        //Initialization of all variables (ui components)
        mCaloriesIcon = (ImageView) findViewById(R.id.imageViewCalories);
        mBike = (ImageView) findViewById(R.id.imageViewBike);
        mGolf = (ImageView) findViewById(R.id.imageViewGolf);
        mHike = (ImageView) findViewById(R.id.imageViewHike);
        mRun = (ImageView) findViewById(R.id.imageViewRun);
        mSwim = (ImageView) findViewById(R.id.imageViewSwim);
        mTennis = (ImageView) findViewById(R.id.imageViewTennis);
        mSettings = (ImageView) findViewById(R.id.imageViewSettings);
        mButton = (Button) findViewById(R.id.buttonAddNewRecord);
        mCaloriesProgress = (CircularProgressBar) findViewById(R.id.progressBarCalories);
        mWelcomeLogo = (TextView) findViewById(R.id.textViewWelcome);
        mTennisProgress = (ProgressBar) findViewById(R.id.progressBarTennis);
        mSwimmingProgress = (ProgressBar) findViewById(R.id.progressBarSwim);
        mHikingProgress = (ProgressBar) findViewById(R.id.progressBarHike);
        mRunningProgress = (ProgressBar) findViewById(R.id.progressBarRun);
        mGolfProgress = (ProgressBar) findViewById(R.id.progressBarGolf);
        mCyclingProgress = (ProgressBar) findViewById(R.id.progressBarBike);
        mCaloriesBurned = (TextView) findViewById(R.id.caloriesBurned);

        //Updates the welcome logo on main activity to display user's name (capitalizing first letter)
        mWelcomeLogo.setText("Welcome "+SettingsController.getInstance().getUserName().substring(0, 1).toUpperCase() +
                SettingsController.getInstance().getUserName().substring(1));

        //Updates the info about calories burned
        mCaloriesBurned.setText(getTotalCalorieCount()+" / "+SettingsController.getInstance().getCaloriesGoal());

        //-----------OnClick Listeners--------------------------------------
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //
            public void onClick(View v) {
                startAddingNewRecords();
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });
        mCaloriesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewActivity();
            }
        });

        mTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Tennis");
            }
        });
        mRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Running");
            }
        });
        mBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Cycling");
            }
        });
        mGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Golf");
            }
        });
        mHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Hiking");
            }
        });
        mSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressViewForSelectedActivity("Swimming");
            }
        });

        //Updates individual progress bars
        updateProgressBars();

        //Updates total calories burned progress bar (CircularProgressBar)
        updateCaloriesProgressBar();

        Log.d("Test Username",SettingsController.getInstance().getUserName());
    }
    //----------Resets the app (removes all entries from the list)------------------------
    protected static void resetTheApp(){
        //empty arrayList
        mFitnessActivities.clear();
        //reset individual progress bars
        mTennisProgress.setProgress(0);
        mSwimmingProgress.setProgress(0);
        mHikingProgress.setProgress(0);
        mRunningProgress.setProgress(0);
        mGolfProgress.setProgress(0);
        mCyclingProgress.setProgress(0);
    }

    //----------Updates the progress bar for individual activities------------------------
    private void updateProgressBars() {
        mTennisProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mTennisProgress.setProgress(getCalorieCountForActivity("Tennis"));

        mSwimmingProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mSwimmingProgress.setProgress(getCalorieCountForActivity("Swimming"));

        mHikingProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mHikingProgress.setProgress(getCalorieCountForActivity("Hiking"));

        mRunningProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mRunningProgress.setProgress(getCalorieCountForActivity("Running"));

        mGolfProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mGolfProgress.setProgress(getCalorieCountForActivity("Golf"));

        mCyclingProgress.setMax(SettingsController.getInstance().getCaloriesGoal()/6);
        mCyclingProgress.setProgress(getCalorieCountForActivity("Cycling"));

    }
    //----------Updates the progress bar for Circular Progress Bar------------------------
    //used during the initial app load
    private void updateCaloriesProgressBar() {
        long test = getTotalCalorieCount()*100/ SettingsController.getInstance().getCaloriesGoal();
        if(test>=100){
            Toast.makeText(this, "You reached your goal, Congratulation! Navigate to setting to reset the app and set new goal", Toast.LENGTH_SHORT).show();
        }
        mCaloriesProgress.setProgressWithAnimation(Math.round(test));
    }
    //used when new activity entered
    private void updateCaloriesProgressBar(String activityType) {
        long test = getTotalCalorieCount()*100/ SettingsController.getInstance().getCaloriesGoal();
        if(test>=100){
            Toast.makeText(this, "You reached your goal, Congratulation! Navigate to setting to reset the app and set new goal", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "New record of: "+activityType+
                    " has been added to the list", Toast.LENGTH_SHORT).show();
        }
        mCaloriesProgress.setProgressWithAnimation(Math.round(test));
    }

    //--------This method is called when the AddNewRecord activity finishes-----------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                // get String data from Intent
                String activityType = data.getStringExtra("ActivityType");
                String activityTDuration = data.getStringExtra("ActivityTDuration");
                addNewActivity(activityType, activityTDuration);
                updateCaloriesProgressBar(activityType);
                mCaloriesBurned.setText(getTotalCalorieCount()+" / "+SettingsController.getInstance().getCaloriesGoal());
                updateProgressBars();

            }
        }
    }

    //-------------------------------Persistence----------------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
        FitnessActivityDataSource dataSource = new FitnessActivityDataSource(this.getApplicationContext());
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }
}
