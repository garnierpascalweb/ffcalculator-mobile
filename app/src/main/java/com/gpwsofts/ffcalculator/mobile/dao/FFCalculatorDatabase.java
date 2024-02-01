package com.gpwsofts.ffcalculator.mobile.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.AsyncTask;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

/**
 * Base de données pour l'application
 * https://developer.android.com/training/data-storage/room?hl=fr
 *
 * @since 1.0.0
 */
@Database(entities = {Result.class}, version = 1)
public abstract class FFCalculatorDatabase extends RoomDatabase {
    private static final String TAG_NAME = "FFCalculatorDatabase";
    private static final String DATABASE_NAME = "ffcalculator_database";
    private static FFCalculatorDatabase instance;

    public abstract ResultDao resultDao();

    public static synchronized FFCalculatorDatabase getInstance(Context context) {
        if (null == instance) {
            Log.i(TAG_NAME, "primo construction de la base de donnees " + DATABASE_NAME);
            instance = Room.databaseBuilder(context.getApplicationContext(), FFCalculatorDatabase.class, DATABASE_NAME)
                    // pas compris (Part 4)
                    .fallbackToDestructiveMigration()
                    // add callback pour faire quelque chose a la creation de la base de donnees (Part 4)
                    // la creation de la base de donnees cest vraiemnt a l'install de l'application
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //TODO a activer en cas de populate new PopulateDbAsyncTask(instance).execute();
        }
    };
}
