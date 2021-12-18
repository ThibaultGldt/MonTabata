package com.example.montabata;


import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;
import com.example.montabata.db.Training;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter .ViewHolder> {

    //DATA
    private List<Training> m_trainings;
    private int itemId;

    private DatabaseClient mDb;
    private TrainingAdapter.OnActionListener m_listener;

    public TrainingAdapter(List<Training> m_trainings, int itemId, OnActionListener listener){
        this.m_trainings = m_trainings;
        this.itemId = itemId;
        this.m_listener = listener;
    }
    @NonNull
    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemId, viewGroup, false);
        mDb = DatabaseClient.getInstance(viewGroup.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.ViewHolder viewHolder, int i) {
        Training thisTraining = m_trainings.get(i);
        viewHolder.trainingName.setText(thisTraining.getM_nom());
        viewHolder.itemView.setTag(thisTraining);

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                class DeleteTraining extends AsyncTask<Void, Void, Training> {

                    @Override
                    protected Training doInBackground(Void... voids) {
                        //on supprime l'exercice dans la bdd
                        mDb.getAppDatabase().TrainingDAO().delete(thisTraining);
                        return thisTraining;
                    }

                    @Override
                    protected void onPostExecute(Training training) {
                        super.onPostExecute(training);
                        //on supprime l'exercice du recyclerview
                        m_trainings.remove(training);
                        notifyDataSetChanged();
                    }
                }
                DeleteTraining dT = new DeleteTraining();
                dT.execute();
            }
        });

        //On crée l'évènement qui permet d'accéder à la modification d'un exercice
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            private TrainingsActivity trainingsActivity;

            @Override
            public void onClick(View v) {
                // TODO: 13/12/2021 Lancer l'activité AddExerciceActivity en passant l'id de l'exercice dans l'intention pour le modifier

                m_listener.startEditActivity((int) thisTraining.getM_id());
            }
        });
        viewHolder.itemView.setTag(thisTraining);
    }

    public void clear() {
        int listSize = m_trainings.size();//on sauvegarde la taille avant de vider la liste
        m_trainings.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Training> trainingList) {
        for (Training training:
                trainingList) {
            m_trainings.add(training);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return m_trainings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trainingName;
        public ImageButton deleteButton;
        public ImageButton editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: 16/12/2021 Gerer ajout/modification/suppression entrainement
            // TODO: 16/12/2021 Ajouter lien entre tables entrainement / exercice
            trainingName = (TextView) itemView.findViewById(R.id.training);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

    interface OnActionListener{
        //on définit une interface qui sera implémentée
        public void startEditActivity(int position);
    }
}
