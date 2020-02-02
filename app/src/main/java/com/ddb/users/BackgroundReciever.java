package com.ddb.users;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class BackgroundReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(new Intent(context, ServiceUpdate.class));
//            } else {
//                context.startService(new Intent(context, ServiceUpdate.class));
//            }


            // String input = editTextInput.getText().toString();

            Intent serviceIntent = new Intent(context, ExampleService.class);
            serviceIntent.putExtra("inputExtra", "Parcels");

            ContextCompat.startForegroundService(context, serviceIntent);



        } catch (Exception e) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
            rootRef = rootRef.child("Errors");
            rootRef.setValue(e.toString());

        }
    }
}
