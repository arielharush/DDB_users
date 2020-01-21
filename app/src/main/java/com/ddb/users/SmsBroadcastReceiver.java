package com.ddb.users;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();


                smsMessageStr = smsBody;
            }
            loginTestCode inst = loginTestCode.instance();
            String[] arrOfStr = smsMessageStr.split(" ", 10);
            if (arrOfStr.length != 7) {
                return;
            }
            if (!arrOfStr[6].equals("dblogisticare.")) {
                return;
            }

            if (inst != null) {

                inst.setEditTextCode(arrOfStr[0]);
            }

        }
    }
}