package com.example.montabata;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.montabata.db.Compteur;
import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.OnUpdateListener;
import com.example.montabata.db.Training;
import com.example.montabata.db.TrainingWithExercices;

import java.util.ArrayList;
import java.util.List;


public class TimerActivity extends AppCompatActivity implements OnUpdateListener {

    //VIEW
    private TextView showTimer;
    private TextView exerciceName;

    //DATA
    private DatabaseClient mDb;
    private List<Exercices> exercices;
    private TrainingWithExercices trainingWithExercices;
    private List<Compteur> timers;
    private int timersIterator;
    private Compteur currentTimer = null;
    private Intent intent;
    private ToneGenerator sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //initialisation
        mDb = DatabaseClient.getInstance(getApplicationContext());
        intent = getIntent();
        timersIterator = 0;
        exercices = new ArrayList<Exercices>();
        timers = new ArrayList<Compteur>();
        sound = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);


        showTimer = (TextView) findViewById(R.id.showTimer);
        showTimer.setText("Start");

        exerciceName = findViewById(R.id.exerciceName);

        showTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTimer == null){
                    onStart(v);
                }else if(!currentTimer.getPaused()){
                    onPause(v);
                }else{
                    onResume(v);
                }
            }
        });
        getExercices(intent.getIntExtra("trainingId", -1));
    }

    public void getExercices(int idTraining){
        class GetExercices extends AsyncTask<Void, Void, List<Exercices>>{

            @Override
            protected List<Exercices> doInBackground(Void... voids) {
                trainingWithExercices = mDb.getAppDatabase().TrainingWithExercicesDAO().getOneTrainingWithExercices(idTraining);
                exercices.addAll(trainingWithExercices.exercicesList);
                return exercices;
            }

            @Override
            protected void onPostExecute(List<Exercices> exercices) {
                super.onPostExecute(exercices);
                makeTimers();
            }
        }
        GetExercices gE = new GetExercices();
        gE.execute();
    }

    public void makeTimers(){
        Training training = trainingWithExercices.training;
        Compteur prepaTimer = new Compteur(training.getM_prepare());
        prepaTimer.setName("Préparation");
        timers.add(prepaTimer);

        for (int i = 0; i < exercices.size(); i++) {
            Exercices exo = exercices.get(i);
            //on crée un timer de travail et de repos pour chaque exercice
            for (int j = 0; j < training.getM_cycle(); j++) {
                //on ajoute ces timers à la liste un nombre de fois équivalent au nombre de cycle fournit dans l'entrainement
                Compteur workTimer = new Compteur(exo.getDuree());
                workTimer.setName(exo.getNom());
                timers.add(workTimer);

                if (j < training.getM_cycle() - 1) {
                    //si le cycle est le dernier on ne met pas de temps de repos car il y'aura un repos long
                    Compteur restTimer = new Compteur(exo.getRepos());
                    restTimer.setName("Repos");
                    timers.add(restTimer);
                }
            }

            if(i < exercices.size() - 1) {
                //si ce n'est pas le dernier exercice on ajoute un repos long
                Compteur longRestTimer = new Compteur(training.getM_longRest());
                longRestTimer.setName("Prochain exercice:\n"+exercices.get(i+1).getNom());
                timers.add(longRestTimer);
            }
        }
    }

    private void makeColors(){
        LinearLayout activityView = findViewById(R.id.timerView);

        if (currentTimer.getName() == "Repos" || currentTimer.getName().contains("Prochain")){
            activityView.setBackgroundColor(Color.GREEN);
        }else if(currentTimer.getName() == "Préparation"){
            activityView.setBackgroundColor(Color.CYAN);
        }else{
            activityView.setBackgroundColor(Color.RED);
        }
    }

    private void makeSounds(){
        //Impossible de comparer à la milliseconde exacte
        //20millisec minimum pour être presque sur de pas rater le bip et pas le jouer plusieurs fois
        if(currentTimer.getUpdatedTime() < 2960 && currentTimer.getUpdatedTime() > 2940){
            sound.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        }else if(currentTimer.getUpdatedTime() < 1960 && currentTimer.getUpdatedTime() > 1940){
            sound.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        }else if(currentTimer.getUpdatedTime() < 960 && currentTimer.getUpdatedTime() > 940){
            sound.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        }
    }

    public void onStart(View view){
        currentTimer = timers.get(timersIterator);
        currentTimer.addOnUpdateListener(this);
        currentTimer.start();
        makeColors();
    }

    public void onPause(View view){
        currentTimer.pause();
    }

    private void miseAJour() {

        // Affichage des informations du compteur
        showTimer.setText("" + currentTimer.getMinutes() + ":"
                + String.format("%02d", currentTimer.getSecondes())
                );
        exerciceName.setText(currentTimer.getName());

    }

    public void onResume(View view){
        currentTimer.start();
    }

    @Override
    public void onUpdate() {
        miseAJour();
        makeSounds();
    }

    @Override
    public void onFinish() {
        if (timersIterator + 1 < timers.size()){
            timersIterator++;
            onStart(showTimer);
        }else{
            endTraining();
        }
    }
    //problème de mise à jour avec finish

    public void endTraining(){
        showTimer.setText("Fin entrainement");
        showTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, TrainingsActivity.class);
                startActivity(intent);
            }
        });
        exerciceName.setText("");
    }
}