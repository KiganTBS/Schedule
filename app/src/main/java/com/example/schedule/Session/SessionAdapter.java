package com.example.schedule.Session;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Session> sessions;

    public SessionAdapter(Context context, List<Session> sessions) {
        this.inflater = LayoutInflater.from(context);
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_session,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.textViewDateExam.setText(session.getDateExam());
        holder.textViewTimeExam.setText(session.getTimeExam());
        holder.textViewProfessorExam.setText(session.getProfessorExam());

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDateExam, textViewTimeExam, textViewProfessorExam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateExam = itemView.findViewById(R.id.textViewDateExam);
            textViewTimeExam = itemView.findViewById(R.id.textViewTimeExam);
            textViewProfessorExam = itemView.findViewById(R.id.textViewProfessorExam);
        }
    }
}
