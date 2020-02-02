package com.ddb.users.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ddb.users.Entities.Parcel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ParcelRepository {
    private ParcelDao parcelDao;
    private LiveData<List<Parcel>> allParcels;
    private LiveData<List<Parcel>> allParcelsForTake;
    private LiveData<List<Parcel>> allParcelsForMe;
    private LiveData<List<Parcel>> allParcelsForMeHistory;
    private DatabaseReference parcelsRef;


    public ParcelRepository(Application application) {
        ParcelDatabase database = ParcelDatabase.getInstance(application);
        parcelDao = database.parcelDao();
        allParcels = parcelDao.getAllParcels();
        allParcelsForTake = parcelDao.getAllParcelsForTake();
        String phone = "";
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }
        allParcelsForMe = parcelDao.getAllParcelsForMe(phone);
        allParcelsForMeHistory = parcelDao.getAllParcelsForMeHistory(phone);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        parcelsRef = firebaseDatabase.getReference("parcels");


    }

    public void insert(Parcel parcel) {

        new InsertParcelAsynvTask(parcelDao).execute(parcel);
    }

    public void update(Parcel parcel) {

        new UpdateParcelAsynvTask(parcelDao).execute(parcel);
    }

    public void delete(Parcel parcel) {

        new DeleteParcelAsynvTask(parcelDao).execute(parcel);

    }

    public void deleteAllParcels() {
        new DeleteAllParcelsAsynvTask(parcelDao).execute();
    }

    public LiveData<List<Parcel>> getAllParcels() {
        return allParcels;
    }

    public LiveData<List<Parcel>> getAllParcelsForMe() {
        return allParcelsForMe;
    }

    public LiveData<Parcel> getParcelById(String ph) {
        return parcelDao.getParcelbyid(ph);
    }

    public LiveData<List<Parcel>> getAllParcelsForMeHistory() {
        return allParcelsForMeHistory;
    }

    public LiveData<List<Parcel>> getAllParcelsForTake() {
        return allParcelsForTake;
    }

    private static class InsertParcelAsynvTask extends AsyncTask<Parcel, Void, Parcel> {


        private ParcelDao parcelDao;

        private InsertParcelAsynvTask(ParcelDao parcelDao) {
            this.parcelDao = parcelDao;
        }


        @Override
        protected Parcel doInBackground(Parcel... parcels) {
            parcelDao.insert(parcels[0]);
            return null;

        }


        @Override
        protected void onPostExecute(Parcel parcel) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference parcelsRef = firebaseDatabase.getReference("parcels");
            ;
            parcelsRef.push().setValue(parcel);
        }

    }

    private static class UpdateParcelAsynvTask extends AsyncTask<Parcel, Void, Void> {


        private ParcelDao parcelDao;

        private UpdateParcelAsynvTask(ParcelDao parcelDao) {
            this.parcelDao = parcelDao;
        }


        @Override
        protected Void doInBackground(Parcel... parcels) {
            parcelDao.update(parcels[0]);
            return null;

        }
    }

    private static class DeleteParcelAsynvTask extends AsyncTask<Parcel, Void, Void> {


        private ParcelDao parcelDao;

        private DeleteParcelAsynvTask(ParcelDao parcelDao) {
            this.parcelDao = parcelDao;
        }


        @Override
        protected Void doInBackground(Parcel... parcels) {
            parcelDao.delete(parcels[0]);
            return null;

        }
    }

    private static class DeleteAllParcelsAsynvTask extends AsyncTask<Void, Void, Void> {

        private ParcelDao parcelDao;


        private DeleteAllParcelsAsynvTask(ParcelDao parcel) {
            this.parcelDao = parcelDao;
        }


        @Override
        protected Void doInBackground(Void... Voids) {
            parcelDao.deletAllParcels();
            return null;

        }
    }


}
