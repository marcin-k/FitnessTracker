package com.marcin_k.gui_development.view;
/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class used for creating a new user account, class verifies that
 *  username is not already in use and checks if retype password
 *  matches the password
 *
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.marcin_k.gui_development.R;
import com.marcin_k.gui_development.controller.SettingsController;
import com.marcin_k.gui_development.databasePersistence.UsersDataSource;
import com.marcin_k.gui_development.model.User;

public class NewUser extends AppCompatActivity {

    private Button mCreateAccountButton;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mRetypePassword;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //binding ui elements
        mCreateAccountButton = (Button) findViewById(R.id.createAccountButton);
        mUsername = (EditText) findViewById(R.id.usernameNewUser);
        mPassword = (EditText) findViewById(R.id.passwordNewUser);
        mRetypePassword = (EditText) findViewById(R.id.retypePasswordNewUser);
        mError = (TextView) findViewById(R.id.errorNewUser);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SettingsController.getInstance().isUsernameFree(mUsername.getText().toString())){
                    mError.setText("This username is already in use");
                }
                else{
                    if(!mPassword.getText().toString().equals(mRetypePassword.getText().toString())){
                        mError.setText("Sorry, Your passwords do not match");
                    }
                    else{
                        //adds user to database and local array list of users
                        addUser(mUsername.getText().toString(), mPassword.getText().toString());
                        Toast.makeText(getApplicationContext() , "Account has been created", Toast.LENGTH_LONG).show();
                        startLoginActivity();
                    }
                }
            }
        });
    }
    private void addUser(String username, String password){
        User user = new User(username, password);
        //adds to db
        UsersDataSource usersDataSource = new UsersDataSource(this);
        usersDataSource.create(user);
        //adds to arrayList
        SettingsController.getInstance().addUser(user);
    }
    //used to go to create new account activity
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sets up a background color when navigating back
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_new_user);
        relativeLayout.setBackgroundResource(SettingsController.getInstance().getBackground());
    }
}
