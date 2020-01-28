package com.ddb.users;


import android.content.Intent;

import androidx.lifecycle.LifecycleService;

import com.ddb.users.Model.ParcelRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ServiceUpdate extends LifecycleService {


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase_DBManager.context = getApplicationContext();
        Firebase_DBManager.repository = new ParcelRepository(getApplication());
        Firebase_DBManager.notifyToParcelList();


    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
        rootRef = rootRef.child("Errors");
        rootRef.setValue("stopping");

    }
}


