package com.example.schedule.Professor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;

import java.util.ArrayList;
import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ViewHolder> {
    List<Professor> professors;

    public ProfessorAdapter() { this.professors = new ArrayList<>(); }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Professor professor = professors.get(position);
        holder.textViewNameProfessor.setText(professor.getName());
        holder.textViewNameSubject.setText(professor.getSubject());
        holder.textViewTypeOfSubject.setText(professor.getSubjType());
    }

    @Override
    public int getItemCount() {
        return professors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameProfessor, textViewNameSubject, textViewTypeOfSubject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProfessor = itemView.findViewById(R.id.textViewNameProfessor);
            textViewNameSubject = itemView.findViewById(R.id.textViewNameSubject);
            textViewTypeOfSubject = itemView.findViewById(R.id.textViewTypeOfSubject);
        }
    }
}
