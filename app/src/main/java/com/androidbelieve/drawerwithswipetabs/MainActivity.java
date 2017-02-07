package com.androidbelieve.drawerwithswipetabs;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    MenuItem lolgin;
    Toolbar toolbar;
     static final String LOG_TAG = "MainActivity";
     static final int GOOGLE_API_CLIENT_ID = 0;
     static GoogleApiClient mGoogleApiClient;
     static PlaceArrayAdapter mPlaceArrayAdapter;
    static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences prefs = getSharedPreferences("carpool", MODE_PRIVATE);
         final int status = prefs.getInt("status",0);


        /////////////runtime permission////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
        }

        /////////////------runtime permission//////

        /**
         *Setup the DrawerLayout and NavigationView
         */

             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the HomeFragment as the first Fragment
         */

             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(status==0){
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new LoginFragment()).commit();
            toolbar.setTitle("CarPool");
        }
        lolgin = mNavigationView.getMenu().findItem(R.id.nav_item_logout);
        if(mNavigationView.getMenu() != null){

            if(status==0){
                lolgin.setTitle("LogIn");

            }
            else{
                lolgin.setTitle("LogOut");
            }


        }
             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                 int status = prefs.getInt("status",0);

                 if (menuItem.getItemId() == R.id.nav_item_home&&status==1) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
                     toolbar.setTitle("CarPool");
                 }


                 if (menuItem.getItemId() == R.id.nav_item_logout &&status ==0) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,new LoginFragment()).commit();
                        toolbar.setTitle("CarPool");
                 }
                 if (menuItem.getItemId() == R.id.nav_item_logout &&status ==1) {
                     SharedPreferences.Editor editor = getSharedPreferences("carpool", MODE_PRIVATE).edit();
                     editor.putInt("status",0);
                     editor.commit();
                     Intent intent = getIntent();
                     finish();
                     startActivity(intent);

                 }

                if (menuItem.getItemId() == R.id.nav_item_profile&&status==1) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
                    toolbar.setTitle("Profile");
                }
                 if (menuItem.getItemId() == R.id.nav_item_noti&&status==1) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new NotificationsFragment()).commit();
                     toolbar.setTitle("Notifications");
                 }
                 if (menuItem.getItemId() == R.id.nav_item_yori&&status==1) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new YourRidesFragment()).commit();
                     toolbar.setTitle("Your Rides");
                 }
                 if (menuItem.getItemId() == R.id.nav_item_cogr&&status==1) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new CompanyGrpFragment()).commit();
                     toolbar.setTitle("Company Group");
                 }
                 if (menuItem.getItemId() == R.id.nav_item_selo&&status==1) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new SendLocationFragment()).commit();
                     toolbar.setTitle("Send Location");
                 }
                 if (menuItem.getItemId() == R.id.nav_item_raca&&status==1 ){
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     xfragmentTransaction.replace(R.id.containerView,new RateCardFragment()).commit();
                     toolbar.setTitle("Rate Card");
                 }

                 if(status==0 && menuItem.getItemId() != R.id.nav_item_logout){
                     Toast.makeText(getApplicationContext(),"Please Login first!",Toast.LENGTH_SHORT).show();
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,new LoginFragment()).commit();
                     toolbar.setTitle("CarPool");
                 }

                 return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */


        if(status==1){
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);


                toolbar.setTitle("CarPool");
                mDrawerLayout.setDrawerListener(mDrawerToggle);

                mDrawerToggle.syncState();}

    }
    public void setlolbar(String title){
        toolbar.setTitle(title);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {

        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {


                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case 2: {


                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to Access your Location", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }
}