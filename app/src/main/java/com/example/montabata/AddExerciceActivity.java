package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;

public class AddExerciceActivity extends AppCompatActivity {
    //DATA
    private DatabaseClient mDb;
    private Intent intent;
    //VIEW
    EditText inputName;
    EditText inputDesc;
    EditText inputWork;
    EditText inputRest;
    Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercice);
        //récupération des données
        mDb = DatabaseClient.getInstance(getApplicationContext());
        intent = getIntent();

        //récupération des éléments graphiques
        inputName = findViewById(R.id.inputName);
        inputDesc = findViewById(R.id.inputDesc);
        inputWork = findViewById(R.id.editWorkTime);
        inputRest = findViewById(R.id.editRestTime);
        valider = findViewById(R.id.buttonValider);

        if (intent.hasExtra("exoId")){//si il y a un extra dans l'intent alors le click sert a modifier un exercice
            //on récupère l'id passé dans l'intent
            Integer exo_id = intent.getIntExtra("exoId", 0);
            //on appelle une fonction qui prérempli les champs avec les données précédentes
            loadExercice(exo_id);
            valider.setOnClickListener(v -> editExercice(v, exo_id));
        }else{//sinon le click appel l'évènement ajouter un exercice
            valider.setOnClickListener(v -> addExercice(v));
        };
    }

    public void loadExercice(int exo_id){
        class loadExercice extends AsyncTask<Void, Void, Exercices> {

            @Override
            protected Exercices doInBackground(Void... voids) {
                //récupération de l'exercice en base de donnée
                Exercices exo = mDb.getAppDatabase().ExerciceDAO().getById(exo_id);
                return exo;
            }
            protected void onPostExecute(Exercices exercice){
                //chargement des données de l'exercice dans la vue
                inputName.setText(exercice.getNom());
                inputDesc.setText(exercice.getDescription());
                inputWork.setText(Integer.toString(exercice.getDuree()));
                inputRest.setText(""+exercice.getRepos());
            }
        }
        loadExercice lE = new loadExercice();
        lE.execute();
    }

    public void addExercice(View view) {

        String name = inputName.getText().toString().trim();
        String desc = inputDesc.getText().toString().trim();
        Integer work = Integer.valueOf(inputWork.getText().toString().trim());
        Integer rest = Integer.valueOf(inputRest.getText().toString().trim());

        class SaveExercice extends AsyncTask<Void, Void, Exercices> {
            protected Exercices doInBackground(Void... voids){
                Exercices exo = new Exercices();
                exo.setNom(name);
                exo.setDescription(desc);
                exo.setDuree(work);
                exo.setRepos(rest);

                long id = mDb.getAppDatabase().ExerciceDAO().insert(exo);
                exo.setId(id);
                return exo;
            }
            protected void onPostExecute(Exercices exercice){
                super.onPostExecute(exercice);

                setResult(RESULT_OK);
                finish();
            }
        }
        SaveExercice se = new SaveExercice();
        se.execute();
    }

    public void editExercice(View view, int exo_position){
        String name = inputName.getText().toString().trim();
        String desc = inputDesc.getText().toString().trim();
        Integer work = Integer.valueOf(inputWork.getText().toString().trim());
        Integer rest = Integer.valueOf(inputRest.getText().toString().trim());

        class SaveExercice extends AsyncTask<Void, Void, Exercices> {
            protected Exercices doInBackground(Void... voids){
                Exercices exo = mDb.getAppDatabase().ExerciceDAO().getById(exo_position);
                exo.setNom(name);
                exo.setDescription(desc);
                exo.setDuree(work);
                exo.setRepos(rest);

                mDb.getAppDatabase().ExerciceDAO().update(exo);

                return exo;
            }
            protected void onPostExecute(Exercices exercice){
                super.onPostExecute(exercice);

                setResult(RESULT_OK);
                finish();
            }
        }
        SaveExercice se = new SaveExercice();
        se.execute();
    }

    public void subWorkTime(View view) {
        Integer work = Integer.valueOf(inputWork.getText().toString().trim());
        work--;
        inputWork.setText(""+work);
    }

    public void addWorkTime(View view) {
        Integer work = Integer.valueOf(inputWork.getText().toString().trim());
        work++;
        inputWork.setText(""+work);
    }

    public void subRestTime(View view) {
        Integer rest = Integer.valueOf(inputRest.getText().toString().trim());
        rest--;
        inputRest.setText(""+rest);
    }

    public void addRestTime(View view) {
        Integer rest = Integer.valueOf(inputRest.getText().toString().trim());
        rest++;
        inputRest.setText(""+rest);
    }
}