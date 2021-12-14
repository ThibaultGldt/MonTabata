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

import java.util.ArrayList;
import java.util.List;

public class ExerciceActivity extends AppCompatActivity implements ExerciceAdapter.OnActionListener{
    //DATA
    private DatabaseClient mDb;
    private ExerciceAdapter adapter;
    private List<Exercices> listExercices = new ArrayList<Exercices>();
    //VIEW
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercice);

        mDb = DatabaseClient.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.listExercices);

        adapter = new ExerciceAdapter(listExercices, R.layout.exercices_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getExercices();
    }

    public void getExercices(){
        class GetExercices extends AsyncTask<Void, Void, List<Exercices>>{

            @Override
            protected List<Exercices> doInBackground(Void... voids) {
                List<Exercices> listExercices = mDb.getAppDatabase()
                        .ExerciceDAO().getAll();
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
        adapter.
        Intent editExerciceIntent = new Intent(ExerciceActivity.this, AddExerciceActivity.class);
        editExerciceIntent.putExtra("exoId", position);
        startActivity(editExerciceIntent);
    }
}