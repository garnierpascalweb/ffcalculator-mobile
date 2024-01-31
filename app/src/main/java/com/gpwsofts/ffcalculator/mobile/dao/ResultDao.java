package com.gpwsofts.ffcalculator.mobile.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.List;

/**
 * Interface de la couche DAO
 * @since 1.0.0
 */
@Dao
public interface ResultDao {
    @Query("SELECT * FROM result")
    LiveData<List<Result>> getAllResults();
    @Query("SELECT * FROM result WHERE id IN (:resultIds)")
    List<Result> getAllByIds(int[]resultIds);

    @Insert
    void insert(Result result);

    @Update
    void update(Result result);
    @Delete
    void delete(Result result);

    @Query("DELETE FROM result")
    void deleteAllResults();
}
