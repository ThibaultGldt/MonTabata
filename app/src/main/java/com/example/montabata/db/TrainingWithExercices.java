package com.example.montabata.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TrainingWithExercices{
    @Embedded public Training training;
    @Relation(
            parentColumn = "training_ID",
            entityColumn = "exercice_ID",
            associateBy = @Junction(TrainingExerciceCrossRef.class)
    )
    public List<Exercices> exercicesList;
}

@Entity(primaryKeys = {"training_ID", "exercice_ID"})
class TrainingExerciceCrossRef {
    public int training_ID;
    public int exercice_ID;
}