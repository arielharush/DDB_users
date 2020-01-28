package com.ddb.users.Model;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.ddb.users.Entities.Parcel;
import java.util.List;

@Dao
public interface ParcelDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Parcel parcel);

    @Delete
    void delete(Parcel parcel);

    @Update
    void update(Parcel parcel);

    @Query("DELETE FROM parcels_table")
    void deletAllParcels();

    @Query("SELECT * FROM parcels_table")
    LiveData<List<Parcel>> getAllParcels();

    @Query("SELECT * FROM parcels_table WHERE (deliveryman_phone == '' AND  (packStatus == 0 OR packStatus == 1))")
    LiveData<List<Parcel>> getAllParcelsForTake();


    @Query("SELECT * FROM parcels_table WHERE (receiver_phone ==:ph AND packStatus <> 3) ")
    LiveData<List<Parcel>> getAllParcelsForMe(String ph);


    @Query("SELECT * FROM parcels_table WHERE (receiver_phone == :ph AND packStatus == 3)")
    LiveData<List<Parcel>> getAllParcelsForMeHistory(String ph);

    @Query("SELECT * FROM Parcels_table WHERE `key` == :ph")
    LiveData<Parcel> getParcelbyid(String ph);

}
