package com.gpwsofts.ffcalculator.mobile.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile FFCalculatorDatabase instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //TODO a activer en cas de populate new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized FFCalculatorDatabase getInstance(final Context context) {
        // https://github.com/android/codelab-android-room-with-a-view/blob/master/app/src/main/java/com/example/android/roomwordssample/WordRoomDatabase.java
        if (null == instance) {
            synchronized (FFCalculatorDatabase.class) {
                if (null == instance) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), FFCalculatorDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

    public abstract ResultDao resultDao();
}
