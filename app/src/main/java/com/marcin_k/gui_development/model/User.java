package com.marcin_k.gui_development.model;

/**
 * Created by Marcin Krzeminski 20077294
 *
 * +++++++++++++ Added for 2nd part of the project +++++++++++++++++++++
 *
 *  Class was created to satisfy the requirements of the login screen
 *  used to store user object with username and password, application
 *  maintains the array list of users in settings controller and
 *  a SQLite database
 *
 */

public class User {
    //used to store users login
    private String username;
    //used to store users password
    private String password;

    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
