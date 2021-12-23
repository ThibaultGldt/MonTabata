package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.Training;
import com.example.montabata.db.TrainingWithExercices;

import java.util.ArrayList;
import java.util.List;

public class TrainingsActivity extends AppCompatActivity implements TrainingAdapter.OnActionListener{
    //DATA
    private TrainingAdapter adapter;
    private DatabaseClient mDb;
    List<TrainingWithExercices> entrainements = new ArrayList<TrainingWithExercices>();

    //VIEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listeEntrainements);

        adapter = new TrainingAdapter(entrainements, R.layout.entrainement_view, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTrainings();
    }

    public void getTrainings(){
        class GetTrainings extends AsyncTask<Void, Void, List<TrainingWithExercices>> {

            @Override
            protected List<TrainingWithExercices> doInBackground(Void... voids) {
                //List<Training> trainingList = mDb.getAppDatabase().TrainingDAO().getAll();
                //return trainingList;

                List<TrainingWithExercices> trainingWithExercicesList = mDb.getAppDatabase().TrainingWithExercicesDAO().getTrainingWithExercicesList();
                return trainingWithExercicesList;
            }

            @Override
            protected void onPostExecute(List<TrainingWithExercices> trainingList) {
                super.onPostExecute(trainingList);
                adapter.clear();
                adapter.addAll(trainingList);
                adapter.notifyDataSetChanged();
            }
        }
        GetTrainings gT = new GetTrainings();
        gT.execute();
    }

    public void GoToAddTraining(View view) {
        Intent addTrainingIntent = new Intent(TrainingsActivity.this, AddTrainingActivity.class);
        startActivity(addTrainingIntent);
    }

    @Override
    public void startEditActivity(int position) {
        //J'en suis ici
        // TODO: 14/12/2021 tester cette fonction
        Intent editTrainingIntent = new Intent(TrainingsActivity.this, AddTrainingActivity.class);
        editTrainingIntent.putExtra("trainingId", position);
        startActivity(editTrainingIntent);
    }

    protected void onStart() {
        super.onStart();
        getTrainings();
    }
}