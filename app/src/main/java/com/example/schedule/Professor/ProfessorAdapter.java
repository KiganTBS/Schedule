package com.example.schedule.Professor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;
import com.example.schedule.Scheule.Schedule;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> {
    List<Professor> professors;
    private LayoutInflater inflater;

    public ProfessorAdapter( Context context, List<Professor> professors) {
        this.professors = professors;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_professor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Professor professor = professors.get(position);
        holder.textViewNameProfessor.setText(professor.getNameProfessor());
        holder.textViewNameSubject.setText(professor.getNameSubject());
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameProfessor, textViewNameSubject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProfessor = itemView.findViewById(R.id.textViewNameProfessor);
            textViewNameSubject = itemView.findViewById(R.id.textViewNameSubject);
        }
    }
}
