package com.gpwsofts.ffcalculator.mobile.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Interface de la couche DAO
 *
 * @since 1.0.0
 */
@Dao
public interface ResultDao {
    @Query("SELECT * FROM result ORDER BY id DESC")
    LiveData<List<Result>> getAllResults();

    @Query("SELECT * FROM result WHERE id IN (:resultIds)")
    List<Result> getAllByIds(int[] resultIds);

    //TODOVU Vu : ca reste à 15, cest fixe - 1.0.0 les points, c'est les 15 meilleurs résultats. 15 devrait etre en parametre
    @Query("SELECT ROUND(SUM(pts),2) FROM result WHERE id in (SELECT id FROM result ORDER BY pts DESC LIMIT 15)")
    LiveData<Double> getPts();

    @Insert
    void insert(Result result);

    @Update
    void update(Result result);

    @Delete
    void delete(Result result);

    @Query("DELETE FROM result")
    void deleteAllResults();
}
