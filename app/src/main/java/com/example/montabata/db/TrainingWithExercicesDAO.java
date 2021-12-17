package com.example.montabata.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface TrainingWithExercicesDAO {
    @Transaction
    @Query("SELECT * FROM training")
    public List<TrainingWithExercices> getTrainingWithExercicesList();
}
