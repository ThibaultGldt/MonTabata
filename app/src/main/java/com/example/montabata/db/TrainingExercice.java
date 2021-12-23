package com.example.montabata.db;

import androidx.room.Entity;

@Entity(primaryKeys = {"m_id", "id"})
public class TrainingExercice {
    public int m_id;
    public int id;
}
