package com.ddb.users;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Enums.PackType;
import com.ddb.users.Entities.Enums.PackageWeight;
import com.ddb.users.Entities.Parcel;

import java.util.Date;


@Database(entities = {Parcel.class}, version = 1, exportSchema = false)
public abstract class ParcelDatabase extends RoomDatabase {

    private static ParcelDatabase instance;

    public abstract ParcelDao parcelDao();


    public static synchronized ParcelDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ParcelDatabase.class, "Parcels_table").fallbackToDestructiveMigration().addCallback(roomCalback).build();
        }
        return instance;

    }


    private static RoomDatabase.Callback roomCalback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();


        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ParcelDao parcelDao;


        private PopulateDbAsyncTask(ParcelDatabase db) {
            parcelDao = db.parcelDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            parcelDao.insert(new Parcel("56hfgh", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.IN_THE_WHY));
            return null;
        }
    }


}
