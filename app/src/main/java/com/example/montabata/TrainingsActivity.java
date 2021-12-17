package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingsActivity extends AppCompatActivity {
    //DATA
    private TrainingAdapter adapter;
    private DatabaseClient mDb;
    List<Training> entrainements = new ArrayList<Training>();

    //VIEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        mDb = DatabaseClient.getInstance(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listeEntrainements);

        adapter = new TrainingAdapter(entrainements, R.layout.entrainement_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getTrainings();
    }

    public void getTrainings(){
        class GetTrainings extends AsyncTask<Void, Void, List<Training>> {

            @Override
            protected List<Training> doInBackground(Void... voids) {
                List<Training> trainingList = mDb.getAppDatabase()
                        .TrainingDAO().getAll();
                return trainingList;
            }

            @Override
            protected void onPostExecute(List<Training> trainingList) {
                super.onPostExecute(trainingList);
                adapter.clear();
                adapter.addAll(trainingList);
                //adapter.notifyDataSetChanged();
            }
        }
        GetTrainings gT = new GetTrainings();
        gT.execute();
    }

    protected void onStart() {
        super.onStart();
        getTrainings();
    }
}