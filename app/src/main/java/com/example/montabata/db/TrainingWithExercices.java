package com.example.montabata.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TrainingWithExercices{
    @Embedded public Training training;
    @Relation(
            parentColumn = "m_id",//id de l'entrainement
            entityColumn = "id",//id de l'exercice
            associateBy = @Junction(TrainingExercice.class)
    )
    public List<Exercices> exercicesList;
}

