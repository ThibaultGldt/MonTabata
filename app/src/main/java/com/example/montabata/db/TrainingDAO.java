package com.example.montabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TrainingDAO {
    @Query("SELECT * FROM training")
    List<Training> getAll();

    @Query("SELECT * FROM training WHERE m_id = :id")
    Training getById(int id);

    @Insert
    long insert(Training training);

    @Delete
    void delete(Training training);

    @Update
    void update(Training training);

}
