package com.ddb.users;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ddb.users.Entities.Parcel;

import java.util.List;

public class ParcelRepository {
    private ParcelDao parcelDao;
    private LiveData<List<Parcel>> allParcels;


    public ParcelRepository(Application application) {
        ParcelDatabase database = ParcelDatabase.getInstance(application);
        parcelDao = database.parcelDao();
        allParcels = parcelDao.getAllParcels();
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


    private static class InsertParcelAsynvTask extends AsyncTask<Parcel, Void, Void> {


        private ParcelDao parcelDao;

        private InsertParcelAsynvTask(ParcelDao parcelDao) {
            this.parcelDao = parcelDao;
        }


        @Override
        protected Void doInBackground(Parcel... parcels) {
            parcelDao.insert(parcels[0]);
            return null;

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
