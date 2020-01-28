package com.ddb.users;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.ddb.users.Entities.Parcel;
import com.ddb.users.Firebase_DBManager;
import com.ddb.users.Model.ParcelRepository;
import com.ddb.users.R;
import com.ddb.users.UserData;

import java.util.List;


public class ServiceUpdate extends LifecycleService {


    @Override
    public void onCreate() {
        super.onCreate();
        //// Firebase_DBManager.context = getApplicationContext();
        //  Firebase_DBManager.repository = new ParcelRepository(getApplication());
        //  Firebase_DBManager.notifyToParcelList();

    }


}


