package com.ddb.users.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ddb.users.Entities.Parcel;
import com.ddb.users.Model.ParcelRepository;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private LiveData<List<Parcel>> parcels;
    private ParcelRepository repository;


    public GalleryViewModel(@NonNull Application application) {
        super(application);
        repository = new ParcelRepository(application);
        parcels = repository.getAllParcelsForMe();
    }


    public LiveData<List<Parcel>> getParcels() {
        return parcels;
    }


}