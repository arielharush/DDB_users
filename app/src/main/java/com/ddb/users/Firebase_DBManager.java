package com.ddb.users;


import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Parcel;
import com.ddb.users.Entities.User;
import com.ddb.users.Model.ParcelRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.ddb.users.App.CHANNEL_1_ID;

public class Firebase_DBManager {


    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    static DatabaseReference ParcelsRef;

    public static Context context;
    public static ParcelRepository repository;

    private static NotificationManagerCompat notificationManager;

    public static void addParcel(final Parcel Parcel, final Action<Long> action) {
//
    }

    private static void addParcelToFirebase(final Parcel Parcel, final Action<Long> action) {
        String key = Parcel.getKey().toString();
        ParcelsRef.child(key).setValue(Parcel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // action.onSuccess((Long)Parcel.getKey());
                action.onProgress("upload Parcel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload Parcel data", 100);

            }
        });
    }


    public static void removeParcel(String id, final Action<Long> action) {

    }


    public static void updateParcel(final Parcel toUpdate, final Action<Long> action) {
        final String key = (toUpdate.getKey()).toString();

        removeParcel(toUpdate.getKey(), new Action<Long>() {
            @Override
            public void onSuccess(Long obj) {
                addParcel(toUpdate, action);
            }

            @Override
            public void onFailure(Exception exception) {
                action.onFailure(exception);
            }

            @Override
            public void onProgress(String status, double percent) {
                action.onProgress(status, percent);
            }
        });
    }

    private static ChildEventListener ParcelRefChildEventListener;

    public static void notifyToParcelList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://dblogisticare.firebaseio.com/");
        ParcelsRef = database.getReference("parcels");
        ///ParcelsRef.setValue("ii");
        notificationManager = NotificationManagerCompat.from(context);
        if (true) {


            ParcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = dataSnapshot.getKey();
                    Parcel.setKey((id));


                    if (repository.getParcelById(id).getValue() == null || (repository.getParcelById(id).getValue().getLastUpdateTime() <= Parcel.getLastUpdateTime())) {


                        if ((Parcel.getPackStatus() == PackStatus.SENT || Parcel.getPackStatus() == PackStatus.OFFER_FOR_SHIPPING) && Parcel.getDeliveryman_phone().equals("")) {
                            Location a = new Location("a");
                            a.setLongitude(Parcel.getLongitudeReceiver());
                            a.setLatitude(Parcel.getLatitudeReceiver());
                            Location b = new Location("b");
                            double long_ = UserData.getUserData(context).getAddress().getLongitude();
                            b.setLongitude(long_);
                            b.setLatitude(UserData.getUserData(context).getAddress().getLatitude());
                            float dis = a.distanceTo(b);
                            Parcel parcel1 = new Parcel(Parcel);
                            parcel1.setDistance(dis);
                            repository.insert(parcel1);


                        } else {
                            repository.insert(Parcel);
                        }

                    } else {


                    }




                    //TODO
                    /// write if for tet if the parcel in status of sent or offer for shiping

                    //  ParcelList.add(Parcel);
                    sendOnChannel1();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = (dataSnapshot.getKey());
                    Parcel.setKey(id);
                    Parcel parcelT = repository.getParcelById(id).getValue();
                    if (Parcel.getLastUpdateTime() > parcelT.getLastUpdateTime()) {
                        repository.insert(Parcel);
                    }

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Parcel Parcel = dataSnapshot.getValue(Parcel.class);
                    String id = (dataSnapshot.getKey());
                    Parcel.setKey(id);
                    repository.insert(Parcel);
                    repository.delete(Parcel);

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            ParcelsRef.addChildEventListener(ParcelRefChildEventListener);
        }
    }

    public static void stopNotifyToParcelList() {
        if (ParcelRefChildEventListener != null) {
            ParcelsRef.removeEventListener(ParcelRefChildEventListener);
            ParcelRefChildEventListener = null;
        }
    }


    public static void sendOnChannel1() {
        String title = "fg";
        String message = "Fg";

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_location_on_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }



}
