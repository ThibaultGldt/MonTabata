package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.Training;
import com.example.montabata.db.TrainingExercice;
import com.example.montabata.db.TrainingWithExercices;

public class AddTrainingActivity extends AppCompatActivity {
    private DatabaseClient mDb;
    private Intent intent;

    EditText inputName;
    EditText inputCycle;
    EditText inputPrepare;
    EditText inputLongRest;
    Button addExerciceToTraining;
    Button valider;
    private Integer training_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        intent = getIntent();

        inputName = findViewById(R.id.inputName);
        inputCycle = findViewById(R.id.inputCycle);
        inputPrepare = findViewById(R.id.inputPrepare);
        inputLongRest = findViewById(R.id.inputLongRest);
        addExerciceToTraining = findViewById(R.id.addExerciceToTraining);
        valider = findViewById(R.id.buttonValider);

        if (intent.hasExtra("trainingId")){//si il y a un extra dans l'intent alors le click sert a modifier un exercice
            //on récupère l'id passé dans l'intent
            training_id = intent.getIntExtra("trainingId", 0);
            //on appelle une fonction qui prérempli les champs avec les données précédentes
            loadTraining(training_id);
            valider.setOnClickListener(v -> editTraining(v, training_id));
        }else{//sinon le click appel l'évènement ajouter un exercice
            valider.setOnClickListener(v -> addTraining(v));
        };

        addExerciceToTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToSelectExercice();
            }
        });
    }

    public void loadTraining(int training_id){
        class loadTraining extends AsyncTask<Void, Void, TrainingWithExercices> {

            @Override
            protected TrainingWithExercices doInBackground(Void... voids) {
                //récupération de l'exercice en base de donnée
                TrainingWithExercices training = mDb.getAppDatabase().TrainingWithExercicesDAO().getOneTrainingWithExercices(training_id);
                return training;
            }
            protected void onPostExecute(TrainingWithExercices trainingWithExercices){
                //chargement des données de l'exercice dans la vue
                inputName.setText(trainingWithExercices.training.getM_nom());
                inputCycle.setText(""+trainingWithExercices.training.getM_cycle());
                inputPrepare.setText(""+ trainingWithExercices.training.getM_prepare());
                inputLongRest.setText(""+trainingWithExercices.training.getM_longRest());
                showTrainingExercices(trainingWithExercices);
            }
        }
        loadTraining lT = new loadTraining();
        lT.execute();
    }

    private void showTrainingExercices(TrainingWithExercices trainingWithExercices){
        LinearLayout mainLinearLayout = findViewById(R.id.exerciceList);

        for (Exercices exercice : trainingWithExercices.exercicesList){
            LinearLayout exerciceLine = (LinearLayout) getLayoutInflater().inflate(R.layout.exercice_line, null);

            TextView exerciceName = exerciceLine.findViewById(R.id.exerciceName);
            TextView exerciceDuree = exerciceLine.findViewById(R.id.exerciceDuree);
            TextView exerciceRepos = exerciceLine.findViewById(R.id.exerciceRepos);
            //Button deleteExercice = exerciceLine.findViewById(R.id.deleteButton);
            /*deleteExercice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeExerciceFromTraining(exercice.getId());
                }
            });*/

            exerciceName.setText(exercice.getNom());
            exerciceDuree.setText("" + exercice.getDuree());
            exerciceRepos.setText("" + exercice.getRepos());
            mainLinearLayout.addView(exerciceLine);
        }
    }

    public void addTraining(View view) {

        String name = inputName.getText().toString().trim();
        Integer cycle = Integer.valueOf(inputCycle.getText().toString().trim());
        Integer prepare = Integer.valueOf((inputPrepare.getText().toString().trim()));
        Integer longRest = Integer.valueOf((inputLongRest.getText().toString().trim()));

        class SaveTraining extends AsyncTask<Void, Void, Training> {
            protected Training doInBackground(Void... voids){
                Training training = new Training();
                training.setM_nom(name);
                training.setM_cycle(cycle);
                training.setM_prepare(prepare);
                training.setM_longRest(longRest);
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
        Integer prepare = Integer.valueOf(inputPrepare.getText().toString().trim());
        Integer longRest = Integer.valueOf(inputLongRest.getText().toString().trim());

        class SaveTraining extends AsyncTask<Void, Void, TrainingWithExercices> {
            protected TrainingWithExercices doInBackground(Void... voids){
                TrainingWithExercices trainingWithExercices = mDb.getAppDatabase().TrainingWithExercicesDAO().getOneTrainingWithExercices(training_position);
                trainingWithExercices.training.setM_nom(name);
                trainingWithExercices.training.setM_cycle(cycle);
                trainingWithExercices.training.setM_prepare(prepare);
                trainingWithExercices.training.setM_longRest(longRest);

                mDb.getAppDatabase().TrainingWithExercicesDAO().update(trainingWithExercices.training);

                return trainingWithExercices;
            }
            protected void onPostExecute(TrainingWithExercices training){
                super.onPostExecute(training);

                setResult(RESULT_OK);
                finish();
            }
        }
        SaveTraining sT = new SaveTraining();
        sT.execute();
    }

    public void GoToSelectExercice(){
        Intent selectExerciceIntent = new Intent(AddTrainingActivity.this, ExerciceActivity.class);
        selectExerciceIntent.putExtra("training_ID", training_id);
        startActivity(selectExerciceIntent);
    }

    /*public void removeExerciceFromTraining(long exerciceID){
        class removeExerciceFromTraining extends AsyncTask<Void, Void, TrainingExercice>{

            @Override
            protected TrainingExercice doInBackground(Void... voids) {
                mDb.getAppDatabase().TrainingWithExercicesDAO().delete();
            }
        }
    }*/
}