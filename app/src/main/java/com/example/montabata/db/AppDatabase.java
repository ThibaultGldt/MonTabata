package com.example.montabata.db;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import kotlin.reflect.KFunction;

@Database(entities = {Exercices.class, Training.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ExerciceDAO ExerciceDAO();
    public abstract TrainingDAO TrainingDAO();
    public abstract TrainingWithExercicesDAO TrainingWithExercicesDAO();

}
