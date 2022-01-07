package com.example.montabata.db;

import android.os.CountDownTimer;

public class Compteur extends UpdateSource {

    // CONSTANTE

    // DATA
    private long updatedTime;
    private CountDownTimer timer;   // https://developer.android.com/reference/android/os/CountDownTimer.html
    private Boolean isPaused;
    private String name;


    public Compteur(long time) {
        setUpdatedTime(time * 1000);
        setPaused(false);
    }

    // Lancer le compteur
    public void start() {

        if (timer == null) {

            // Créer le CountDownTimer
            timer = new CountDownTimer(getUpdatedTime(), 10) {

                // Callback fired on regular interval
                public void onTick(long millisUntilFinished) {
                    setUpdatedTime(millisUntilFinished);

                    // Mise à jour
                    update();
                }

                // Callback fired when the time is up
                public void onFinish() {
                    setUpdatedTime(0);
                    // Mise à jour
                    update();
                    finish();
                }

            }.start();   // Start the countdown
        }
        setPaused(false);
    }

    // Mettre en pause le compteur
    public void pause() {

        if (timer != null) {

            // Arreter le timer
            stop();
            this.setPaused(true);
            // Mise à jour
            update();
        }
    }

    // Arrete l'objet CountDownTimer et l'efface
    private void stop() {
        timer.cancel();
        timer = null;

    }

    public int getMinutes() {
        return (int) (getUpdatedTime() / 1000)/60;
    }

    public int getSecondes() {
        int secs = (int) (getUpdatedTime() / 1000);
        return secs % 60;
    }

    public int getMillisecondes() {
        return (int) (getUpdatedTime() % 1000);
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public void setPaused(Boolean paused) {
        isPaused = paused;
    }
}

