package com.ddb.users;


import android.content.Context;
import android.content.SharedPreferences;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ddb.users.Entities.Address;
import com.ddb.users.Entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class UserData {


    static public Context contextT;
    static public User thisUser;

    static public boolean getUserEnteredData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean userFirstLogin = pref.getBoolean("userdata", false);
        return userFirstLogin;
    }

    static public void setUserEnteredData(Context context, boolean b) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("userdata", b).commit();
    }

    private static void setUserData(Context context, User tempUser) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("first_name", tempUser.getFirst_name());
        editor.putString("last_name", tempUser.getLast_name());
        editor.putString("mail_address", tempUser.getMail_address());
        editor.putString("country", tempUser.getAddress().getCountry());
        editor.putString("city", tempUser.getAddress().getCity());
        editor.putString("street", tempUser.getAddress().getStreet());
        editor.putString("number", tempUser.getAddress().getNumber());
        editor.putString("zipCode", tempUser.getAddress().getZipCode());
        editor.putString("latitude", tempUser.getAddress().getLatitude() + "");
        editor.putString("longitude", tempUser.getAddress().getLongitude() + "");
        editor.commit();
    }

    static public User getUserData(Context context) {


        User tempUser = new User();
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        tempUser.setFirst_name(pref.getString("first_name", "uu"));
        tempUser.setLast_name(pref.getString("last_name", ""));
        tempUser.setMail_address(pref.getString("mail_address", ""));
        Address address = new Address();
        address.setCountry(pref.getString("country", ""));
        address.setCity(pref.getString("city", ""));
        address.setLatitude(Double.parseDouble(pref.getString("latitude", "")));
        address.setLongitude(Double.parseDouble(pref.getString("longitude", "")));
        address.setStreet(pref.getString("street", ""));
        address.setNumber(pref.getString("number", ""));
        address.setZipCode(pref.getString("zipCode", ""));
        tempUser.setPhone_number(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        tempUser.setAddress(address);
        return tempUser;
    }

    static public void getuserDataFromFirebase(final MainActivity activity, final Context context) {
        contextT = context;
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/").getReference("users");
        //rootRef.child("users");
        rootRef = rootRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                TextView name = (TextView) activity.findViewById(R.id.name_bar);
                TextView phone = (TextView) activity.findViewById(R.id.phone_bar);
                name.setText(user.getFirst_name() + "  " + user.getLast_name());
                phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                setUserData(context, user);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
