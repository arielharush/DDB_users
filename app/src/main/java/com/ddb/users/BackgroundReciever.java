package com.ddb.users;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BackgroundReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, ServiceUpdate.class);
        context.startService(startServiceIntent);
    }
}


