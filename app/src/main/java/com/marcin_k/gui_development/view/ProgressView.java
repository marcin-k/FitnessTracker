package com.marcin_k.gui_development.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;
import com.marcin_k.gui_development.adapter.ActivityAdapter;
import com.marcin_k.gui_development.model.FitnessActivity;

import java.util.ArrayList;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Class contains the ListView to display all fitnessActivites
 * or all records for individual fitnessActivites
 *
 */

public class ProgressView extends ListActivity {

    private ArrayList<FitnessActivity> mActivities;
    private TextView mListLabel;
    private String mActivityType;
    private TextView mCalorieGoalLabel;
    private TextView mCalorieGoalValue;
    private TextView mHomeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);

        //Initialization of all variables (ui components)
        mListLabel = (TextView) findViewById(R.id.ListViewLabel);
        mCalorieGoalValue = (TextView) findViewById(R.id.calorieGoalValue);
        mCalorieGoalLabel = (TextView) findViewById(R.id.calorieGoalLabel);
        mHomeButton = (TextView) findViewById(R.id.home);

        //gets the passed parcelable ArrayList
        Intent intent = getIntent();
        ArrayList<Parcelable> parcelable = intent.getParcelableArrayListExtra(MainActivity.ACTIVITY_PROGRESS);
        mActivityType = intent.getStringExtra(MainActivity.ACTIVITY_PROGRESS_LABEL);

        //sets the label above the listView
        mListLabel.setText(mActivityType +" history:");

        //sets the label above the listView
        mCalorieGoalLabel.setText("Calorie Goal for "+ mActivityType);

        //Display the progress below the title
        if(mActivityType.equals("All Activities")){
            mCalorieGoalValue.setText(MainActivity.getTotalCalorieCount()+" / "+
                    SettingsController.getInstance().getCaloriesGoal());
        }
        else{
            mCalorieGoalValue.setText(MainActivity.getCalorieCountForActivity(mActivityType)+" / "+
                    SettingsController.getInstance().getCaloriesGoal()/6);
        }

        //recreates array from parcelable array
            mActivities = (ArrayList<FitnessActivity>)parcelable.clone();
            ActivityAdapter adapter = new ActivityAdapter(this, mActivities);
            setListAdapter(adapter);

        //on click listener for list view item
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProgressView.this, "That brought you "+mActivities.get(position).getCalories()+
                        " calories closer to your target", Toast.LENGTH_SHORT).show();
            }
        });

        //home button on click listener
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
    @Override
    protected void onResume() {
        super.onResume();
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_progress_view);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }

}
