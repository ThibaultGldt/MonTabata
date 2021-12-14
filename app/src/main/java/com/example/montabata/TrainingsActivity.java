package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TrainingsActivity extends AppCompatActivity {
    //DATA
    private TrainingAdapter adapter;
    List<String> entrainements = new ArrayList<String>();

    //VIEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listeEntrainements);

        for(int i = 0; i < 20; i++){
            entrainements.add("entrainement " + i);
        }

        adapter = new TrainingAdapter(entrainements, R.layout.entrainement_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addTraining(View view){
        entrainements.add(1, "training");
        adapter.notifyItemInserted(1);
    }
}