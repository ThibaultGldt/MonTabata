package com.example.montabata.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "training")
public class Training {
    @PrimaryKey(autoGenerate = true)
    private int m_id;
    private String m_nom;
    private int m_cycle;
    private int m_prepare;
    private int m_longRest;


    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_nom() {
        return m_nom;
    }

    public void setM_nom(String m_nom) {
        this.m_nom = m_nom;
    }

    public int getM_cycle() {
        return m_cycle;
    }

    public void setM_cycle(int m_cycle) {
        this.m_cycle = m_cycle;
    }

    public int getM_prepare() {
        return m_prepare;
    }

    public void setM_prepare(int m_prepare) {
        this.m_prepare = m_prepare;
    }

    public int getM_longRest() {
        return m_longRest;
    }

    public void setM_longRest(int m_longRest) {
        this.m_longRest = m_longRest;
    }
}
