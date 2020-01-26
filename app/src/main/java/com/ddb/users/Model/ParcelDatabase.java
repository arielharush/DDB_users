package com.ddb.users.Model;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ddb.users.Entities.Converters;
import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Enums.PackType;
import com.ddb.users.Entities.Enums.PackageWeight;
import com.ddb.users.Entities.Parcel;

import java.util.Date;


@Database(entities = {Parcel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
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

            Parcel parcel = new Parcel("1", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.SENT);
            Parcel parcel1 = new Parcel("2", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.OFFER_FOR_SHIPPING);
            Parcel parcel2 = new Parcel("3", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.IN_THE_WHY);
            Parcel parcel3 = new Parcel("4", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.RECEIVED);
            Parcel parcel4 = new Parcel("5", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.SENT);
            Parcel parcel5 = new Parcel("6", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.IN_THE_WHY);
//            parcelDao.insert(parcel);
//            parcelDao.insert(parcel1);
//            parcelDao.insert(parcel2);
//            parcelDao.insert(parcel3);
//            parcel4.setDeliveryman_phone("8768");
//            parcelDao.insert(parcel4);
//            parcelDao.insert(parcel5);
//
//
//
//            Parcel parcel6 = new Parcel("7", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "1234", new Date(), PackStatus.SENT);
//            Parcel parcel7 = new Parcel("8", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "+972559959829", new Date(), PackStatus.SENT);
//            Parcel parcel8 = new Parcel("9", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "+972559959829", new Date(), PackStatus.OFFER_FOR_SHIPPING);
//            Parcel parcel9 = new Parcel("10", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "+972559959829", new Date(), PackStatus.IN_THE_WHY);
//            Parcel parcel10 = new Parcel("11", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "+972559959829", new Date(), PackStatus.RECEIVED);
//            Parcel parcel11= new Parcel("12", PackType.BIG_PACK, true, PackageWeight.UP_TO_1KG, new Location("a"), "5354", new Date(), PackStatus.OFFER_FOR_SHIPPING);
//            parcelDao.insert(parcel6);
//            parcelDao.insert(parcel7);
//            parcelDao.insert(parcel8);
//            parcelDao.insert(parcel9);
//            parcelDao.insert(parcel10);
//            parcelDao.insert(parcel11);



            return null;
        }
    }


}
