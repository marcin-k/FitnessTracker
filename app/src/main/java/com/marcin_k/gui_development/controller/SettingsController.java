package com.marcin_k.gui_development.controller;

import android.content.Context;
import android.util.Log;

import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.databasePersistence.UsersDataSource;
import com.marcin_k.gui_development.model.User;

import java.util.ArrayList;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * Singleton class, maintains info about:
 * - theme used
 * - user's name
 * - calorie goal
 * - copy of users
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Array List of users has been added to the class, its initialized in
 *  line 39 of login activity, restoreUsers method loads users from
 *  Users database into array list to validate a users password,
 *  check if the database is empty and validate that correct username
 *  (one that exist) is entered
 *  isUsernameFree method allows to find if the username that user is
 *  looking to create is not already used by someone else
 *
 */
//----------------Singleton part of the class--------------------------------
public class SettingsController {

    private static SettingsController ourInstance = new SettingsController();

    public static SettingsController getInstance() {
        return ourInstance;
    }

    private SettingsController() {
    }

//----------------Non singleton part of the class----------------------------

//+++++++++++ Added for the second part of the project +++++++++++++++++++++++++++++++++++++++++++++

    //restores users from db
    public void restoreUsers(Context context){
        //restores the arrayList from db
        UsersDataSource usersDataSource = new UsersDataSource(context);
        users  =  usersDataSource.read();
    }

    //adds new user to array list
    public void addUser(User user){
        users.add(user);
    }
    //returns array list of users from db
    public ArrayList<User> getUsers(){
        return users;
    }

    //checks if username is already in use
    public boolean isUsernameFree(String userName){
        boolean toReturn = true;
        for(User user:getUsers()){
            Log.d("username", user.getUsername());
            if(user.getUsername().equals(userName)){
                toReturn=false;
            }
        }
        return toReturn;
    }

    //checks if username and password are correct
    public String isLoginValid(String username, String password){
        if(users.isEmpty()){
            return "Users DB is empty, please create a new account";
        }
        else{
            boolean passwordCorrect = false;
            boolean loginExist = false;
            for(User user:getUsers()){
                if(user.getUsername().equals(username)){
                    loginExist = true;
                    if (user.getPassword().equals(password)){
                        passwordCorrect=true;
                    }
                }
            }
            if(passwordCorrect){
                return "loginConfirm";
            }
            else {
                if (loginExist){
                    return "Incorrect Password";
                }
                else{
                    return "Incorrect Username";
                }
            }
        }
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //array list on users from db
    private ArrayList<User> users;

    //colour description
    private String colourName = "blue";

    //background colour
    private int background = R.drawable.bg_blue;

    //user's name, by default two spaces to allow change to upper case
    //only the first letter when entered
    private String userName = "  ";

    //Hold the currently set goal
    //It takes on average 3888 calories to burn 0.5kg of weight
    private int caloriesGoal = 3888;

    //currently set goal as String (kilograms)
    private String goal = "0.5 kg";

    //tracks the position in goal_selection array to update the selected goal when user visits
    //the setting activity
    private int goalPosition =0;

    //return colour information as String
    public String getColourName(){
        return colourName;
    }

    //return the currently used (set) background colour
    public int getBackground(){
        return background;
    }

    //changes the background colour based on value provided
    public void changeBackground(String colour){
        colourName = colour;
        if(colour.equals("orange")){
            background = R.drawable.bg_orange;
        }
        else if(colour.equals("blue")){
            background = R.drawable.bg_blue;
        }
        else if(colour.equals("violet")){
            background = R.drawable.bg_violet;
        }
    }

    //returns users name
    public String getUserName() {
        return userName;
    }

    //sets user's name
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Allows to get a selected setting for theme in Setting activity
    public int getThemePosition(){
        if(background ==R.drawable.bg_blue){
            return 0;
        }
        else if(background ==R.drawable.bg_violet){
            return 1;
        }
        else{
            return 2;
        }
    }

    //return the currently set goal (calories)
    public int getCaloriesGoal() {
        return caloriesGoal;
    }

    //sets a appropriate calories goal for a goal provided in kg
    public void setCaloriesGoalAndCaloriesPosition(String goal) {
        if(goal.equals("0.5 kg")){
            caloriesGoal=3888;
            goalPosition =0;
        }
        if(goal.equals("1 kg")){
            caloriesGoal=7777;
            goalPosition =1;
        }
        if(goal.equals("2 kg")){
            caloriesGoal=15554;
            goalPosition =2;
        }
        if(goal.equals("3 kg")){
            caloriesGoal=23331;
            goalPosition =3;
        }
        if(goal.equals("4 kg")){
            caloriesGoal=31108;
            goalPosition =4;
        }
        if(goal.equals("5 kg")){
            caloriesGoal=38885;
            goalPosition =5;
        }
        if(goal.equals("6 kg")){
            caloriesGoal=46662;
            goalPosition =6;
        }
        if(goal.equals("7 kg")){
            caloriesGoal=54439;
            goalPosition =7;
        }
        if(goal.equals("8 kg")){
            caloriesGoal=62216;
            goalPosition =8;
        }

    }
    //return the current goal position in goal_selection array in string.xml
    public int getGoalPosition(){
        return goalPosition;
    }

    //allows to change the goal (String) method changes the calories goal as
    // well using the setCaloriesGoalAndCaloriesPosition method
    public void setGoal(String goal) {
        this.goal=goal;
        setCaloriesGoalAndCaloriesPosition(goal);
    }

    //returns goal in String form e.g. "5 kg"
    public String getGoal(){
        return goal;
    }

}
