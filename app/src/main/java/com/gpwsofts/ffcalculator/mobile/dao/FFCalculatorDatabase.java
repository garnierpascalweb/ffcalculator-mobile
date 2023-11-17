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

    /**
     * Tache permettant l'alimentation de la base de données à la première initialisation
     * Mock
     * @since 1.0.0
     */
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ResultDao resultDao;

        private PopulateDbAsyncTask(FFCalculatorDatabase db) {
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
            Result mockResult2 = new Result();
            mockResult2.setPlace("Mizirieux");
            mockResult2.setLogo("Open 2/3");
            mockResult2.setPts(23.01);
            mockResult2.setPrts(177);
            mockResult2.setPos(11);
            mockResult2.setLibelle("Open 2/3 Access");
            mockResult2.setIdClasse("1.25.0");
            resultDao.insert(mockResult2);
            Result mockResult3 = new Result();
            mockResult3.setPlace("Cusset");
            mockResult3.setLogo("Open 2/3");
            mockResult3.setPts(70);
            mockResult3.setPrts(56);
            mockResult3.setPos(2);
            mockResult3.setLibelle("Open 2/3 Access");
            mockResult3.setIdClasse("1.25.0");
            resultDao.insert(mockResult3);
            Result mockResult4 = new Result();
            mockResult4.setPlace("St Rambert d'Albon");
            mockResult4.setLogo("Open 2/3");
            mockResult4.setPts(25.16);
            mockResult4.setPrts(148);
            mockResult4.setPos(8);
            mockResult4.setLibelle("Open 2/3 Access");
            mockResult4.setIdClasse("1.25.0");
            resultDao.insert(mockResult4);
            Result mockResult5 = new Result();
            mockResult5.setPlace("Roanne");
            mockResult5.setLogo("Open 1/2/3");
            mockResult5.setPts(5.32);
            mockResult5.setPrts(38);
            mockResult5.setPos(15);
            mockResult5.setLibelle("Open 1/2/3 Access");
            mockResult5.setIdClasse("1.24.2");
            resultDao.insert(mockResult5);
            Result mockResult6 = new Result();
            mockResult6.setPlace("Arles");
            mockResult6.setLogo("Open 3");
            mockResult6.setPts(8);
            mockResult6.setPrts(80);
            mockResult6.setPos(14);
            mockResult6.setLibelle("Open 3 Access");
            mockResult6.setIdClasse("1.25.1");
            resultDao.insert(mockResult6);
            // Cadet
            Result mockResult7 = new Result();
            mockResult7.setPlace("St Amand Montrond");
            mockResult7.setLogo("U17");
            mockResult7.setPts(39.15);
            mockResult7.setPrts(87);
            mockResult7.setPos(2);
            mockResult7.setLibelle("U17 - Cadet");
            mockResult7.setIdClasse("1.31");
            resultDao.insert(mockResult7);
            // elite
            Result mockResult8 = new Result();
            mockResult8.setPlace("Hyeres");
            mockResult8.setLogo("Elite");
            mockResult8.setPts(5.76);
            mockResult8.setPrts(48);
            mockResult8.setPos(19);
            mockResult8.setLibelle("Elite Open 1/2/3");
            mockResult8.setIdClasse("1.12.6");
            resultDao.insert(mockResult8);
            // Federale juniors
            Result mockResult9 = new Result();
            mockResult9.setPlace("St Jean de Védas");
            mockResult9.setLogo("U19");
            mockResult9.setPts(140.3);
            mockResult9.setPrts(122);
            mockResult9.setPos(3);
            mockResult9.setLibelle("Federale U19");
            mockResult9.setIdClasse("1.14");
            resultDao.insert(mockResult9);
            return null;
        }
    };
}
