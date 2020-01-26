package com.ddb.users.Entities;


import androidx.room.TypeConverter;

import com.ddb.users.Entities.Enums.PackStatus;
import com.ddb.users.Entities.Enums.PackType;
import com.ddb.users.Entities.Enums.PackageWeight;


public class Converters {
    @TypeConverter
    public static PackageWeight getPackageWeight(Integer numeral) {
        for (PackageWeight ds : PackageWeight.values()) {
            if (ds.ordinal() == numeral) {
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static int PackageWeightToInetger(PackageWeight packageWeight) {
        return packageWeight.ordinal();
    }


    @TypeConverter
    public static PackStatus getPackStatus(Integer numeral) {
        for (PackStatus ds : PackStatus.values()) {
            if (ds.ordinal() == numeral) {
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static int PackStatusToInetger(PackStatus packStatus) {
        return packStatus.ordinal();
    }


    @TypeConverter
    public static PackType getPackType(Integer numeral) {
        for (PackType ds : PackType.values()) {
            if (ds.ordinal() == numeral) {
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static int PackTypeToInetger(PackType packType) {
        return packType.ordinal();
    }


}
