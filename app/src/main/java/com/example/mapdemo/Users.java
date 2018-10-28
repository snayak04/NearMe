package com.example.mapdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.provider.Settings.Secure;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Users extends Activity {
    String fName;
    String lName;
    String ID;

    public Users(String fName, String lName){
        this.fName = fName;
        this.lName = lName;
        this.ID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
        createUser(this.fName, this.lName, this.ID);
    }

    private static void createUser(String fName, String lName, String ID){
// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
    public String Name() {
        return this.fName + " " + this.lName;
    }

    /**
     * Queries the location table, and fetches users that are under 100m.
     * @return List of users
     */
    public ArrayList<Users> showNearby(){
        ArrayList<Users> users = new ArrayList<>();
        //TODO:: Query using NearbyManager
        return users;
    }
}
