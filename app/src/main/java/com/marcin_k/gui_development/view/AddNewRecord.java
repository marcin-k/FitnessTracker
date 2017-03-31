package com.marcin_k.gui_development.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Class used to add new record of FitnessActivity
 * user can select a activity and duration to be added
 * to the list
 */

public class AddNewRecord extends AppCompatActivity {

    //ui elements
    private ImageView mGolf;
    private ImageView mTennis;
    private ImageView mSwim;
    private ImageView mBike;
    private ImageView mHike;
    private ImageView mRun;
    private TextView mActivitySelected;
    private ImageView mSettings;
    private Spinner mSpinner;
    private Button mAddRecordToList;
    private TextView mHomeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        //Initialization of all variables (ui components)
        mBike = (ImageView) findViewById(R.id.imageViewBike);
        mGolf = (ImageView) findViewById(R.id.imageViewGolf);
        mHike = (ImageView) findViewById(R.id.imageViewHike);
        mRun = (ImageView) findViewById(R.id.imageViewRun);
        mSwim = (ImageView) findViewById(R.id.imageViewSwim);
        mTennis = (ImageView) findViewById(R.id.imageViewTennis);
        mActivitySelected = (TextView) findViewById(R.id.activitySelected);
        mSettings = (ImageView) findViewById(R.id.imageViewSettings);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mAddRecordToList = (Button) findViewById(R.id.addRecordToList);
        mHomeButton = (TextView) findViewById(R.id.home);

        //---- Sets a custom style for spinner(spinner.xml) and a dropdown menu(spinner_dropdown.xml)
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.duration_array, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinner.setAdapter(adapter);

        //-----------OnClick Listeners----------------------------------------------

        mTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Tennis");
            }
        });
        mRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Running");
            }
        });
        mBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Cycling");
            }
        });
        mGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Golf");
            }
        });
        mHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Hiking");
            }
        });
        mSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelected.setText("Swimming");
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });
        mAddRecordToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if user selected activity
                if(mActivitySelected.getText().toString().equals("---")){
                    Toast.makeText(getApplicationContext(), "Please click on one of the activities" +
                            " icon to select it",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("ActivityType", mActivitySelected.getText().toString());
                    intent.putExtra("ActivityTDuration", mSpinner.getSelectedItem().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    //method to go to home screen
    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //method to move to Setting Activity
    private void startSettingsActivity(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_add_new_record);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }

}
