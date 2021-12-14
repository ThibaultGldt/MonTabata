package com.example.montabata;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button goToTimer;
    private Button goToTrainings;
    private Button goToExercice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToTimer = (Button) findViewById(R.id.Timer);
        goToTrainings = (Button) findViewById(R.id.Trainings);
        goToExercice = (Button) findViewById(R.id.Exercice);
    }

    public void GoToTimer(View v){
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }

    public void GoToTrainings(View v){
        Intent intent = new Intent(MainActivity.this, TrainingsActivity.class);
        startActivity(intent);
    }

    public void GoToExercice(View v){
        Intent intent = new Intent(MainActivity.this,ExerciceActivity.class);
        startActivity(intent);
    }
}