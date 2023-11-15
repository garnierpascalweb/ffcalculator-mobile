package com.gpwsofts.ffcalculator.mobile.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Base de données pour l'application
 * https://developer.android.com/training/data-storage/room?hl=fr
 * @since 1.0.0
 */
@Database(entities = {Result.class}, version = 1)
public abstract class FFCalculatorDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ffcalculator_database";
    private static  FFCalculatorDatabase instance;
    public abstract ResultDao resultDao();
    public static synchronized FFCalculatorDatabase getInstance(Context context){
        if (null == instance){
            instance = Room.databaseBuilder(context.getApplicationContext(), FFCalculatorDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
