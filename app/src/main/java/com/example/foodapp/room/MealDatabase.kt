package com.example.foodapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.data.entity.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun getMealsDao(): MealDaoFav

    companion object {
        @Volatile
        var INSTANGE: MealDatabase? =
            null// volatile yanı hep aynı degerı alcagını gaantı ederek derleyici yormaz

        //.fallbackToDestructiveMigration() :vt degiştiğide verileri koru

        fun getInstange(contexx: Context): MealDatabase {
            if (INSTANGE == null) {
                synchronized(MealDatabase::class) {
                    INSTANGE = Room.databaseBuilder(contexx,MealDatabase::class.java, "meals.sqlite")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANGE as MealDatabase
        }
    }


}