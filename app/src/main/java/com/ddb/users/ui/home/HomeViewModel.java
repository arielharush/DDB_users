package com.ddb.users.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ddb.users.Entities.Parcel;
import com.ddb.users.Model.ParcelRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    private LiveData<List<Parcel>> parcels;
    private ParcelRepository repository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new ParcelRepository(application);
        parcels = repository.getAllParcelsForTake();
    }

    public LiveData<List<Parcel>> getParcels() {
        return parcels;
    }

}