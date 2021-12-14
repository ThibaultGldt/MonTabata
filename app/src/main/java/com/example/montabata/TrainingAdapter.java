package com.example.montabata;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter .ViewHolder> {

    //DATA
    private List<String> entrainements;
    private int itemId;

    public TrainingAdapter(List<String> entrainements, int itemId){
        this.entrainements = entrainements;
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
        String item = entrainements.get(i);
        viewHolder.trainingName.setText(item);
        viewHolder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return entrainements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trainingName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trainingName = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
