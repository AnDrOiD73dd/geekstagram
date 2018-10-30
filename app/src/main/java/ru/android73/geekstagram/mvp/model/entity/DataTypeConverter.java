package ru.android73.geekstagram.mvp.model.entity;

import android.arch.persistence.room.TypeConverter;

import ru.android73.geekstagram.mvp.model.entity.DataType;

public class DataTypeConverter {

    @TypeConverter
    public static DataType toDataType(int dataType) {
        if (dataType == DataType.LOCAL.getCode()) {
            return DataType.LOCAL;
        } else if (dataType == DataType.REMOTE.getCode()) {
            return DataType.REMOTE;
        } else {
            throw new IllegalArgumentException("Could not recognize data type");
        }
    }

    @TypeConverter
    public static Integer toInteger(DataType dataType) {
        return dataType.getCode();
    }
}
