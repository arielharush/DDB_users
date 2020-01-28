package com.ddb.users.Entities;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ddb.users.Entities.Enums.*;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Parcels_table")
public class Parcel {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    String key;
    PackType packType;
    boolean breakable;
    PackageWeight packageWeight;
    //Location location;
    double longitude;
    double latitude;
    String receiver_phone;
    //  Date dateSend;
    //@TypeConverters(PackStatus.class)
    PackStatus packStatus;
    String deliveryman_phone;
    double longitudeReceiver;
    double latitudeReceiver;
    // Date dateReceived;
    float distance;
    public double getLongitudeReceiver() {
        return longitudeReceiver;
    }

    public void setLongitudeReceiver(double longitudeReceiver) {
        this.longitudeReceiver = longitudeReceiver;
    }

    public double getLatitudeReceiver() {
        return latitudeReceiver;
    }

    public void setLatitudeReceiver(double latitudeReceiver) {
        this.latitudeReceiver = latitudeReceiver;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public static String packageWeightTosString(PackageWeight packageWeight) {
        String temp = "";


        switch (packageWeight) {
            case UP_TO_500_GR:
                temp = "Up to 500 gr";
                break;
            case UP_TO_1KG:
                temp = "Up to 1 kg";
                break;
            case UP_TO_5KG:
                temp = "Up to 5 kg";
                break;
            case UP_TO_20KG:
                temp = "Up to 20 kg";
            default:
                break;
        }


        return temp;

    }

    public static String PackTypeTosString(PackType packType) {
        String temp = "";


        switch (packType) {
            case ENVELOPE:
                temp = "Envelope";
                break;
            case SMALL_PACK:
                temp = "Small pack";
                break;
            case BIG_PACK:
                temp = "Big pack";
                break;
            default:
                break;
        }


        return temp;

    }

    public static String packStatusTosString(PackStatus packStatus) {
        String temp = "";


        switch (packStatus) {
            case SENT:
                temp = "Sent";
                break;
            case OFFER_FOR_SHIPPING:
                temp = "Offer for shipping";
                break;
            case IN_THE_WHY:
                temp = "In the why";
                break;
            case RECEIVED:
                temp = "Received";
            default:
                break;
        }


        return temp;

    }

    public Parcel(String key, PackType packType, boolean breakable, PackageWeight packageWeight,
                  Location location, String receiver_phone, Date dateSend, PackStatus packStatus) {
        this.key = key;
        this.packType = packType;
        this.breakable = breakable;
        this.packageWeight = packageWeight;
        // this.location = location;
        this.receiver_phone = receiver_phone;
        //this.dateSend = new Date(dateSend.getTime());
        this.packStatus = packStatus;
        this.deliveryman_phone = "";
        // this.dateReceived = null;
        this.distance = 0;
    }

    public Parcel(Parcel parcel) {
        this.packType = parcel.packType;
        this.breakable = parcel.breakable;
        this.packageWeight = parcel.packageWeight;
        this.longitude = parcel.longitude;
        this.latitude = parcel.latitude;
        this.receiver_phone = parcel.receiver_phone;
        this.packStatus = parcel.packStatus;
        this.deliveryman_phone = parcel.deliveryman_phone;
        this.distance = parcel.distance;
        this.longitudeReceiver = parcel.longitudeReceiver;
        this.latitudeReceiver = parcel.latitudeReceiver;
        this.key = parcel.key;
    }

    public Parcel() {
        this.key = null;
        this.packType = PackType.ENVELOPE;
        this.breakable = false;
        this.packageWeight = PackageWeight.UP_TO_500_GR;
        // this.location = null;
        this.receiver_phone = null;
        this.deliveryman_phone = null;
        //  this.dateSend = new Date(Calendar.getInstance().getTime().getTime());
        //this.dateReceived = null;
        this.packStatus = PackStatus.SENT;
        this.distance = 0;
    }

    public Parcel(Long lo) {
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PackType getPackType() {
        return packType;
    }

    public void setPackType(PackType packType) {
        this.packType = packType;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public PackageWeight getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(PackageWeight packageWeight) {
        this.packageWeight = packageWeight;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getDeliveryman_phone() {
        return deliveryman_phone;
    }

    public void setDeliveryman_phone(String deliveryman_phone) {
        this.deliveryman_phone = deliveryman_phone;
    }

//    public Date getDateSend() {
//        return dateSend;
//    }
//
//    public void setDateSend(Date dateSend) {
//        this.dateSend = dateSend;
//    }
//
//    public Date getDateReceived() {
//        return dateReceived;
//    }
//
//    public void setDateReceived(Date dateReceived) {
//        this.dateReceived = dateReceived;
//    }

    public PackStatus getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(PackStatus packStatus) {
        this.packStatus = packStatus;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "key=" + key +
                ", packType=" + packType +
                ", breakable=" + breakable +
                ", packageWeight=" + packageWeight +
                //  ", location=" + location +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", deliveryman_phone='" + deliveryman_phone + '\'' +
                //  ", dateSend=" + dateSend +
                // ", dateReceived=" + dateReceived +
                ", packStatus=" + packStatus +
                '}';
    }
}
