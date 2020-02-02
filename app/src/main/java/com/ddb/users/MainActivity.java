package com.ddb.users;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ddb.users.Model.ParcelRepository;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private EditText phoneNumberEditText;
    private Button sendSmsButton;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        //mAuth.signOut();

        if (mAuth.getCurrentUser() == null) {
            setContentView(R.layout.login);
            phoneNumberEditText = findViewById(R.id.phone_number);
            sendSmsButton = findViewById(R.id.send_sms);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {


                } else {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                }

            }
            sendSmsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phoneNumberEditText.length() != 10) {


                    } else {

                        Intent intent = new Intent(MainActivity.this, LoginTestCodeActivity.class);
                        intent.putExtra("number", "" + phoneNumberEditText.getText());
                        startActivity(intent);
                        MainActivity.this.finish();
                    }

                }
            });

            return;

        } else {

            if (!UserData.getUserEnteredData(getApplicationContext())) {
                Intent intent = new Intent(MainActivity.this, LoginTestCodeActivity.class);
                intent.putExtra("number", "");
                startActivity(intent);
                MainActivity.this.finish();

            } else {

            }
            setContentView(R.layout.activity_main);
            UserData.getuserDataFromFirebase(this, getApplicationContext());

        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void logOut(MenuItem item) {
        mAuth.signOut();
        MainActivity.this.finish();


//        ParcelRepository repository = new ParcelRepository(getApplication());
//        if (repository.getParcelById("etjrwke5876545676ytemu").getValue() == null) {
//            Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_SHORT).show();
//        } else {
//
//            Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Firebase_DBManager.stopNotifyToParcelList();
    }
}
