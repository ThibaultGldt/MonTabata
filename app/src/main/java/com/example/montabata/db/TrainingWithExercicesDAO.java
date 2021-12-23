package com.example.montabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TrainingWithExercicesDAO {
    @Transaction
    @Query("SELECT * FROM training")
    public List<TrainingWithExercices> getTrainingWithExercicesList();
    @Transaction
    @Query("SELECT * FROM training WHERE m_id = :training_ID ")
    public TrainingWithExercices getOneTrainingWithExercices(int training_ID);

    @Insert
    long insert(TrainingExercice trainingWithExercices);
    @Update
    void update(Training trainingWithExercices);

    @Delete
    void delete(TrainingExercice trainingWithExercices);
    
}
