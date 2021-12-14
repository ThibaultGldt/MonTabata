package com.example.montabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciceDAO {
    @Query("SELECT * FROM exercice")
    List<Exercices> getAll();

    @Insert
    long insert(Exercices exercices);

    @Delete
    void delete(Exercices exercices);

    @Update
    void update(Exercices exercices);
}
