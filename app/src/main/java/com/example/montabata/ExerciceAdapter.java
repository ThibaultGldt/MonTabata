package com.example.montabata;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montabata.db.DatabaseClient;
import com.example.montabata.db.Exercices;

import java.util.List;

public class ExerciceAdapter extends RecyclerView.Adapter<ExerciceAdapter .ViewHolder>{
    private List<Exercices> m_exercices;
    private int m_itemId;
    private DatabaseClient mDb;
    private OnActionListener m_listener;

    public ExerciceAdapter(List<Exercices> exercices, int itemId){
        this.m_exercices = exercices;
        this.m_itemId = itemId;
    }

    @NonNull
    @Override
    public ExerciceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(m_itemId, parent, false);
        //on récupère la bdd avec le contexte de l'activité parent
        mDb = DatabaseClient.getInstance(parent.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciceAdapter.ViewHolder holder, int position) {
        Exercices exo = m_exercices.get(position);
        holder.exerciceName.setText(exo.getNom());
        holder.exerciceDescription.setText(exo.getDescription());
        holder.exerciceDuree.setText("" + exo.getDuree());
        holder.exerciceRepos.setText(""+ exo.getRepos());

        //On crée l'évènement pour supprimer un exercice
        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                class DeleteExercice extends AsyncTask<Void, Void, Exercices> {

                    @Override
                    protected Exercices doInBackground(Void... voids) {
                        //on supprime l'exercice dans la bdd
                        mDb.getAppDatabase().ExerciceDAO().delete(exo);
                        return exo;
                    }

                    @Override
                    protected void onPostExecute(Exercices exercices) {
                        super.onPostExecute(exercices);
                        //on supprime l'exercice du recyclerview
                        m_exercices.remove(exercices);
                        notifyDataSetChanged();
                    }
                }
                DeleteExercice de = new DeleteExercice();
                de.execute();
            }
        });

        //On crée l'évènement qui permet d'accéder à la modification d'un exercice
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            private ExerciceActivity exoActivity;

            @Override
            public void onClick(View v) {
                // TODO: 13/12/2021 Lancer l'activité AddExerciceActivity en passant l'id de l'exercice dans l'intention pour le modifier

                m_listener.startEditActivity((int) exo.getId());
            }
        });
        holder.itemView.setTag(exo);
    }

    @Override
    public int getItemCount() {
        return m_exercices.size();
    }

    public void clear() {
        int listSize = m_exercices.size();//on sauvegarde la taille avant de vider la liste
        m_exercices.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Exercices> exercices) {
        for (Exercices exo:
             exercices) {
            m_exercices.add(exo);
        }
        notifyDataSetChanged();
    }

    public long getID(int position){
        return m_exercices.get(position).getId();
        //à utiliser pour delete un item depuis l'activity
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView exerciceName;
        public TextView exerciceDescription;
        public TextView exerciceDuree;
        public TextView exerciceRepos;
        public ImageButton deleteButton;
        public ImageButton editButton;

        public ViewHolder(View v) {
            super(v);
            exerciceName = (TextView) v.findViewById(R.id.exerciceName);
            exerciceDescription = (TextView) v.findViewById(R.id.exerciceDescription);
            exerciceDuree = (TextView) v.findViewById(R.id.exerciceDuree);
            exerciceRepos = (TextView) v.findViewById(R.id.exerciceRepos);
            deleteButton =  v.findViewById(R.id.deleteButton);
            editButton = v.findViewById(R.id.editButton);
        }
    }

    interface OnActionListener{
        //on définit une interface qui sera implémentée
        public void startEditActivity(int position);
    }
}
