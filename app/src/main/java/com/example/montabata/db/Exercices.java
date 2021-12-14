package com.example.montabata.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercice")
public class Exercices {
//DATA
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String nom;
    private String description;
    private int duree;
    private int repos;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuree() { return duree; }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getRepos() {
        return repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }
}
