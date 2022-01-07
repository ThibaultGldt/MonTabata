package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.TrainingExercice;
import com.example.montabata.db.TrainingWithExercices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExerciceActivity extends AppCompatActivity implements ExerciceAdapter.OnActionListener{
    //DATA
    private DatabaseClient mDb;
    private ExerciceAdapter adapter;
    private List<Exercices> listExercices = new ArrayList<Exercices>();
    private Intent intent;
    private int training_id;
    //VIEW
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        intent = getIntent();

        recyclerView = (RecyclerView) findViewById(R.id.listExercices);
        //On passe la liste d'exercice remplie au chargement de l'activité, la vue xml pour une ligne d'exercice et l'activité
        //pour ajouté un écouteur d'action sur le bouton modifier de chaque ligne d'exercice
        adapter = new ExerciceAdapter(listExercices, R.layout.exercices_view, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getExercices();

        if(intent.hasExtra("training_ID")){
            training_id = intent.getIntExtra("training_ID", -1);
        }
    }

    public void getExercices(){
        class GetExercices extends AsyncTask<Void, Void, List<Exercices>>{

            @Override
            protected List<Exercices> doInBackground(Void... voids) {
                List<Exercices> listExercices = mDb.getAppDatabase()
                        .ExerciceDAO().getAll();
                List<TrainingExercice> trainingExercices = mDb.getAppDatabase().TrainingWithExercicesDAO().getTrainingExercice(training_id);
                List<Exercices> listExoToRemove = new ArrayList<Exercices>();

                for (Iterator<Exercices> iterator = listExercices.iterator(); iterator.hasNext();){
                    Exercices exo = iterator.next();
                    for (TrainingExercice trainingExercice :
                            trainingExercices) {
                        if (exo.getId() == trainingExercice.id) {
                            //On stock les exercices présent dans l'entrainement dans une liste tampon
                            listExoToRemove.add(exo);
                        }
                    }
                }
                for (Exercices exo :
                        listExoToRemove) {
                    //on enlève les exercices stockés de la liste à renvoyé
                    //lorsque l'on veut ajouter un exercice à notre entrainement
                    //on ne voit que les exercices n'étant pas déjà dans l'entrainement
                    listExercices.remove(exo);
                }

                return listExercices;
            }

            @Override
            protected void onPostExecute(List<Exercices> exercices) {
                super.onPostExecute(exercices);
                adapter.clear();
                adapter.addAll(exercices);
                //adapter.notifyDataSetChanged();
            }
        }
        GetExercices gE = new GetExercices();
        gE.execute();
    }

    //@Override
    protected void onStart() {
        super.onStart();
        getExercices();
    }

    public void addExercice(View view) {
        Intent addExerciceIntent = new Intent(ExerciceActivity.this, AddExerciceActivity.class);
        startActivity(addExerciceIntent);
    }

    @Override
    public void startEditActivity(int position) {
        //J'en suis ici
        // TODO: 14/12/2021 tester cette fonction
        Intent editExerciceIntent = new Intent(ExerciceActivity.this, AddExerciceActivity.class);
        editExerciceIntent.putExtra("exoId", position);
        startActivity(editExerciceIntent);
    }

    @Override
    public void addExerciceToTraining(int position){
        class addExerciceToTraining extends AsyncTask<Void, Void, TrainingExercice>{

            @Override
            protected TrainingExercice doInBackground(Void... voids) {
                TrainingExercice trainingExercice = new TrainingExercice();
                trainingExercice.m_id = training_id;
                trainingExercice.id = position;
                mDb.getAppDatabase().TrainingWithExercicesDAO().insert(trainingExercice);
                return trainingExercice;
            }

            @Override
            protected void onPostExecute(TrainingExercice trainingExercice) {
                super.onPostExecute(trainingExercice);
                setResult(RESULT_OK);
                finish();
            }
        }
        addExerciceToTraining addExerciceToTraining = new addExerciceToTraining();
        addExerciceToTraining.execute();
    }
}