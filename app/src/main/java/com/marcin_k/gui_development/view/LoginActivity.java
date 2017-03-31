package com.marcin_k.gui_development.view;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class is used to display the login screen, from where user can
 *  go to NewUser screen to create a new account or login using the
 *  existing account, class uses the database access through
 *  UserDataSource to validate the login using methods in
 *  setting controller (which holds the local copy of db)
 *
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mNewUserButton;
    private TextView mPasswordError;
    private EditText mUsername;
    private EditText mPassword;

    //Shared preferences are usd to store info about the theme, goal and users name
    public static final String PREFS_FILE = "com.marcin_k.gui_development.preferences";
    public static final String THEME = "THEME";
    public static final String GOAL = "GOAL";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SettingsController.getInstance().restoreUsers(this);

        //restores the shared preferences for settings
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        SettingsController.getInstance().setGoal(mSharedPreferences.getString(GOAL, "0.5 kg"));
        SettingsController.getInstance().changeBackground(mSharedPreferences.getString(THEME, "blue"));

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mNewUserButton = (Button) findViewById(R.id.createAccountButton);
        mPasswordError = (TextView) findViewById(R.id.errorNewUser);
        mUsername = (EditText) findViewById(R.id.usernameNewUser);
        mPassword = (EditText) findViewById(R.id.passwordNewUser);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgToDisplay = SettingsController.getInstance().isLoginValid(mUsername.getText().toString(), mPassword.getText().toString());
                if(msgToDisplay.equals("loginConfirm")){
                    SettingsController.getInstance().setUserName(mUsername.getText().toString());
                    Log.d("Username-login screen", SettingsController.getInstance().getUserName());
                    startMain();
                }
                else{
                    mPasswordError.setText(msgToDisplay);
                }
            }
        });

        //if new user button is click startNewUser method is executed
        //which creates intent to go to NewUser Activity
        mNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewUser();
            }
        });
    }

    //makes sures that background is changed if user navigates using system back button
    @Override
    protected void onResume() {
        super.onResume();
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_login);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }

    //used to start main activity
    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //used to go to create new account activity
    private void startNewUser() {
        Intent intent = new Intent(this, NewUser.class);
        startActivity(intent);
    }
}