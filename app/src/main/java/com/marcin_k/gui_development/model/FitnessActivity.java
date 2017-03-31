package com.marcin_k.gui_development.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.marcin_k.gui_development.R;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Class used for footprint for every (physical)activity
 * contains base attributes and set of getter methods
 *
 */
public class FitnessActivity implements Parcelable {
    //stores activity type e.g. Tennis
    private String mActivityType="";
    //duration of activity as String
    private String mDuration="";
    //date when activity was performed (added)
    private String mTime="";
    //calories burned during that activity
    private int mCalories=0;

    //Constructor
    public FitnessActivity(String activityType, String activityTDuration, String todaysDate) {
        mActivityType = activityType;
        mDuration = activityTDuration;
        mTime = todaysDate;
        mCalories = setCalories();
    }

    /**
     *  App assumes that on average (per hour) user would burn:
     *  - 420 calories while playing tennis
     *  - 696 calories while swimming
     *  - 540 calories while hiking
     *  - 576 calories while running
     *  - 168 calories while playing golf
     *  - 648 calories while cycling
     */

    //sets calories based on activity duration and type
    private int setCalories() {
        int calories = 0;

        if(mActivityType.equals("Tennis")){
            calories=35;
        }
        if(mActivityType.equals("Swimming")){
            calories=58;
        }
        if(mActivityType.equals("Hiking")){
            calories=45;
        }
        if(mActivityType.equals("Running")){
            calories=48;
        }
        if(mActivityType.equals("Golf")){
            calories=14;
        }
        if(mActivityType.equals("Cycling")){
            calories=54;
        }

        if(mDuration.equals("10 min")){
            calories*=2;
        }
        if(mDuration.equals("15 min")){
            calories*=3;
        }
        if(mDuration.equals("20 min")){
            calories*=4;
        }
        if(mDuration.equals("30 min")){
            calories*=6;
        }
        if(mDuration.equals("45 min")){
            calories*=9;
        }
        if(mDuration.equals("60 min")){
            calories*=12;
        }
        if(mDuration.equals("75 min")){
            calories*=15;
        }
        if(mDuration.equals("90 min")){
            calories*=18;
        }
        return calories;
    }

    //return a calories burned during activity
    public int getCalories(){
        return mCalories;
    }

    //return duration of activity
    public String getDuration() {
        return mDuration;
    }

    //return a date when activity was performed (entered)
    public String getTime() {
        return mTime;
    }

    //return activity type
    public String getActivityType() {
        return mActivityType;
    }

    //return a reference to icon for the progress_list
    public int getIconId(){

        //sets the icon to running icon by default
        int iconId = R.drawable.runicon;
        if(mActivityType.equals("Tennis")){
            iconId=R.drawable.tennisicon;
        }
        else if(mActivityType.equals("Cycling")){
            iconId=R.drawable.bikeicon;
        }
        else if(mActivityType.equals("Golf")){
            iconId=R.drawable.golficon;
        }
        else if(mActivityType.equals("Hiking")){
            iconId=R.drawable.hikeicon;
        }
        else if(mActivityType.equals("Swimming")){
            iconId=R.drawable.swimicon;
        }
        return iconId;
    }
//********** Parcelable interface used to access the array from ProgressView class ******************

    @Override
    public int describeContents() {
        return 0;
    }

    //allows to export (parcel) a class
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mActivityType);
        dest.writeString(mDuration);
        dest.writeString(mTime);
        dest.writeInt(mCalories);

    }

    //allows to import the class (read it)
    private FitnessActivity(Parcel in){
        mActivityType = in.readString();
        mDuration = in.readString();
        mTime= in.readString();
        mCalories = in.readInt();

    }

    public static final Creator<FitnessActivity>CREATOR = new Creator<FitnessActivity>() {
        @Override
        public FitnessActivity createFromParcel(Parcel source) {
            return new FitnessActivity(source);
        }

        @Override
        public FitnessActivity[] newArray(int size) {
            return new FitnessActivity[size];
        }
    };
}
