package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Training;

public class AddTrainingActivity extends AppCompatActivity {
    private DatabaseClient mDb;
    private Intent intent;

    EditText inputName;
    EditText inputCycle;
    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        intent = getIntent();

        inputName = findViewById(R.id.inputName);
        inputCycle = findViewById(R.id.inputCycle);
        valider = findViewById(R.id.buttonValider);

        if (intent.hasExtra("trainingId")){//si il y a un extra dans l'intent alors le click sert a modifier un exercice
            //on récupère l'id passé dans l'intent
            Integer training_id = intent.getIntExtra("trainingId", 0);
            //on appelle une fonction qui prérempli les champs avec les données précédentes
            loadTraining(training_id);
            valider.setOnClickListener(v -> editTraining(v, training_id));
        }else{//sinon le click appel l'évènement ajouter un exercice
            valider.setOnClickListener(v -> addTraining(v));
        };
    }

    public void loadTraining(int training_id){
        class loadTraining extends AsyncTask<Void, Void, Training> {

            @Override
            protected Training doInBackground(Void... voids) {
                //récupération de l'exercice en base de donnée
                Training training = mDb.getAppDatabase().TrainingDAO().getById(training_id);
                return training;
            }
            protected void onPostExecute(Training training){
                //chargement des données de l'exercice dans la vue
                inputName.setText(training.getM_nom());
                inputCycle.setText(""+training.getM_cycle());
            }
        }
        loadTraining lT = new loadTraining();
        lT.execute();
    }

    public void addTraining(View view) {

        String name = inputName.getText().toString().trim();
        Integer cycle = Integer.valueOf(inputCycle.getText().toString().trim());

        class SaveTraining extends AsyncTask<Void, Void, Training> {
            protected Training doInBackground(Void... voids){
                Training training = new Training();
                training.setM_nom(name);
                training.setM_cycle(cycle);
                long id = mDb.getAppDatabase().TrainingDAO().insert(training);
                training.setM_id((int) id);
                return training;
            }
            protected void onPostExecute(Training training){
                super.onPostExecute(training);

                setResult(RESULT_OK);
                finish();
            }
        }
        SaveTraining sT = new SaveTraining();
        sT.execute();
    }

    public void editTraining(View view, int training_position){
        String name = inputName.getText().toString().trim();
        Integer cycle = Integer.valueOf(inputCycle.getText().toString().trim());

        class SaveTraining extends AsyncTask<Void, Void, Training> {
            protected Training doInBackground(Void... voids){
                Training training = mDb.getAppDatabase().TrainingDAO().getById(training_position);
                training.setM_nom(name);
                training.setM_cycle(cycle);

                mDb.getAppDatabase().TrainingDAO().update(training);

                return training;
            }
            protected void onPostExecute(Training training){
                super.onPostExecute(training);

                setResult(RESULT_OK);
                finish();
            }
        }
        SaveTraining sT = new SaveTraining();
        sT.execute();
    }
}