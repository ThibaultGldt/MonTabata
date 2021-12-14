package com.example.montabata.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Exercices.class/*, Training.class*/}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExerciceDAO ExerciceDAO();
   // public abstract TrainingDAO TrainingDAO();

}
