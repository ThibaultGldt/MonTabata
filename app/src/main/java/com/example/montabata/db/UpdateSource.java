package com.example.montabata.db;

import java.util.ArrayList;

public class UpdateSource {
    private ArrayList<OnUpdateListener> listeners = new ArrayList<OnUpdateListener>();

    // Méthode d'abonnement
    public void addOnUpdateListener(OnUpdateListener listener) {
        listeners.add(listener);
    }

    // Méthode activée par la source pour prévenir les auditeurs de l'événement update
    public void update() {

        // Notify everybody that may be interested.
        for (OnUpdateListener listener : listeners)
            listener.onUpdate();
    }

    public void finish(){
        for (OnUpdateListener listener : listeners){
            listener.onFinish();
        }
    }
}
