package com.example.montabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;

import java.util.ArrayList;
import java.util.List;

public class AddExerciceActivity extends AppCompatActivity {
    //DATA
    private DatabaseClient mDb;

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

        mDb = DatabaseClient.getInstance(getApplicationContext());
        inputName = findViewById(R.id.inputName);
        inputDesc = findViewById(R.id.inputDesc);
        inputWork = findViewById(R.id.editWorkTime);
        inputRest = findViewById(R.id.editRestTime);
        valider = findViewById(R.id.buttonValider);
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