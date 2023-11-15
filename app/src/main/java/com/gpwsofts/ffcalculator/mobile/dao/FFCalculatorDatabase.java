package com.gpwsofts.ffcalculator.mobile.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.os.AsyncTask;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

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

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
           new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Tache permettant l'alimentation de la base de données à la première initialisation
     * @since 1.0.0
     */
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private ResultDao resultDao;
        private PopulateDbAsyncTask(FFCalculatorDatabase db){
            resultDao = db.resultDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Result mockResult1 = new Result();
            mockResult1.setPlace("St Romain de Popey");
            mockResult1.setLogo("Open 2/3");
            mockResult1.setPts(10.29);
            mockResult1.setPrts(147);
            mockResult1.setPos(16);
            mockResult1.setLibelle("Open 2/3 Access");
            mockResult1.setIdClasse("1.25.0");
            resultDao.insert(mockResult1);
            return null;
        }
    };
}
