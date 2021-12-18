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
    @Transaction
    @Query("SELECT * FROM training WHERE m_id = :training_ID ")
    public TrainingWithExercices getOneTrainingWithExercices(int training_ID);
}
