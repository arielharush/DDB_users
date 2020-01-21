package com.ddb.users;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ddb.users.Entities.Parcel;

import java.util.List;

@Dao
public interface ParcelDao {


    @Insert
    void insert(Parcel parcel);


    @Delete
    void delete(Parcel parcel);


    @Update
    void update(Parcel parcel);


    @Query("DELETE FROM parcels_table")
    void deletAllParcels();


    @Query("SELECT * FROM parcels_table")
    LiveData<List<Parcel>> getAllParcels();


}
