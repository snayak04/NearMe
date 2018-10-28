package com.example.mapdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private BottomNavigationView mBottomNavigationView;
    private int mCurrentItemID;
    private GoogleApiClient mGoogleApiClient;


    private static final String EXTRA_GOOGLE_ACCOUNT = "com.agora.android.agora.google_account";
    private static final String EXTRA_SIGN_OUT = "com.agora.android.agora.sign_out";

    public static boolean isSignOutRequested(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra(EXTRA_SIGN_OUT, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location_demo);

        final GoogleSignInAccount googleSignInAccount = getIntent()
                .getParcelableExtra(EXTRA_GOOGLE_ACCOUNT);


//
//
//        mSignOutButton = (Button) findViewById(R.id.button_sign_out);
//        mSignOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setSignOutRequested(true);
//            }
//        });
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public static Intent newIntent(Context context, GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra(EXTRA_GOOGLE_ACCOUNT, googleSignInAccount);
        return intent;
    }

    private void setSignOutRequested(boolean isSignOutRequested) {
        Intent data = new Intent();
        data.putExtra(EXTRA_SIGN_OUT, isSignOutRequested);
        setResult(RESULT_OK, data);
    }

    private Location getLastLocation() {
        boolean permissionOn=false;
        Location returnLocation = null;
        //if at least Marshmallow, need to ask user's permission to get GPS data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //if permission is not yet granted, ask for it
            while (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
            permissionOn=true;
        } else {
            permissionOn=true;
        }

        if(permissionOn) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);

            /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
            for (int i = providers.size() - 1; i >= 0; i--) {
                returnLocation = lm.getLastKnownLocation(providers.get(i));
                if (returnLocation != null) break;
            }
        }
        return returnLocation;

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}