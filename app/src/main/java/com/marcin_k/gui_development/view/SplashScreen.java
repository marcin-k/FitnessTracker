package com.marcin_k.gui_development.view;
/*****************************************************
 *
 *  Created by Marcin Krzeminski 20077294
 *
 *  SplashScreen Class displays the application's
 *  splash screen for duration of 2 seconds and
 *  uses the intent to move to LoginActivity
 *  screen after that
 *
 *****************************************************/

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.marcin_k.gui_development.R;

public class SplashScreen extends AppCompatActivity {

    //duration of displaying the splash screen 2000 milliseconds (2 seconds)
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        // New Handler to start the LoginActivity
        // and close this Splash-Screen after 2 seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}