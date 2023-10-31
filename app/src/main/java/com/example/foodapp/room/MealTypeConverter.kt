package com.example.foodapp.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attributee: Any?): String {
        if (attributee == null)
            return ""
        return attributee as String
    }

    @TypeConverter
    fun fromStringToAny(attributee: String?): Any {
        if (attributee == null) {
            return ""
        }
        return attributee
    }


}