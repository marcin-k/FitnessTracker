package com.marcin_k.gui_development.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;
import com.marcin_k.gui_development.databasePersistence.FitnessActivityDataSource;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Setting class, contains the list of settings;
 * -change the user's name
 * -change the app theme (background colour)
 * -resetting the app
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  onPause method, used to saves information about the
 *  theme used and current goal (users calorie goal)
 *  stored in the shared preferences
 *
 *  onResume method implemented so whenever user changes the
 *  theme setting and use the build it back button the previous
 *  activity would use the updated version of the background colour
 *
 */
public class Settings extends AppCompatActivity {

    //references for ui elements
    private Button mApplyChanges;
    private Spinner mSpinnerTheme;
    private Spinner mSpinnerWeight;
    private Button mResetApp;
    private TextView mHomeButton;

    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        //Initialization of all variables (ui components)
        mResetApp = (Button) findViewById(R.id.resetAppButton);
        mSpinnerTheme = (Spinner) findViewById(R.id.spinnerTheme);
        mSpinnerWeight = (Spinner) findViewById(R.id.spinnerWeight);
        mHomeButton = (TextView) findViewById(R.id.home);
        mApplyChanges = (Button) findViewById(R.id.applyButton);

        //---- Sets a custom style for spinner(spinner.xml) and a dropdown menu(spinner_dropdown.xml)
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.theme_array, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinnerTheme.setAdapter(adapter);

        //---- Sets a custom style for spinner(spinner.xml) and a dropdown menu(spinner_dropdown.xml)
        ArrayAdapter adapterWeight = ArrayAdapter.createFromResource(this, R.array.weight_array, R.layout.spinner);
        adapterWeight.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinnerWeight.setAdapter(adapterWeight);

        /*
          below sets a selected items in the list as the currently used one
          whenever user visit the setting page any individual setting can be change
          without the need of touching the others
          */

        mSpinnerTheme.setSelection(SettingsController.getInstance().getThemePosition());
        mSpinnerWeight.setSelection(SettingsController.getInstance().getGoalPosition());

        //on click listener to reset app
        mResetApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FitnessActivityDataSource fitnessActivityDataSource = new FitnessActivityDataSource(getApplicationContext());
                fitnessActivityDataSource.clearDatabase();
                MainActivity.resetTheApp();
                goBackToMain();
            }
        });

        //on click listener to apply changes
        mApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsController.getInstance().changeBackground(mSpinnerTheme.getSelectedItem().toString().toLowerCase());
                SettingsController.getInstance().setGoal(mSpinnerWeight.getSelectedItem().toString());
                goBackToMain();
            }
        });

        //on click listener for home button
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });
    }
    //used to go back to main activity
    private void goBackToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    //updates the shared preferences
    @Override
    protected void onPause() {
        super.onPause();
        mEditor.putString(MainActivity.THEME, SettingsController.getInstance().getColourName());
        mEditor.putString(MainActivity.GOAL, SettingsController.getInstance().getGoal());
        mEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_settings);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }
}
