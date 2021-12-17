package com.example.montabata;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montabata.db.Exercices;
import com.example.montabata.db.Training;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter .ViewHolder> {

    //DATA
    private List<Training> m_trainings;
    private int itemId;

    public TrainingAdapter(List<Training> m_trainings, int itemId){
        this.m_trainings = m_trainings;
        this.itemId = itemId;
    }
    @NonNull
    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemId, viewGroup, false);
        return new ViewHolder(v);//comprendre pq on fait ça
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingAdapter.ViewHolder viewHolder, int i) {
        Training thisTraining = m_trainings.get(i);
        viewHolder.trainingName.setText(thisTraining.getM_nom());
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: 16/12/2021 Gerer ajout/modification/suppression entrainement
            // TODO: 16/12/2021 Ajouter lien entre tables entrainement / exercice
            trainingName = (TextView) itemView.findViewById(R.id.training);
        }
    }
}
