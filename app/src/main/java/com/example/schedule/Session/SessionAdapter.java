package com.example.schedule.Session;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    private List<Session> sessions;
    private OnSessionClickListener onSessionClickListener;

    public SessionAdapter() {
        this.sessions = new ArrayList<>();
    }

    interface OnSessionClickListener{
        void onSessionLongClick(int position);
    }

    public void setOnSessionClickListener(OnSessionClickListener onSessionClickListener) {
        this.onSessionClickListener = onSessionClickListener;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.textViewDateExam.setText(session.getDateExam());
        holder.textViewTimeExam.setText(session.getTimeExam());
        holder.textViewProfessorExam.setText(session.getSubject());
        holder.textViewFormatExam.setText(session.getFormat());

       if (session.getFormat().equals("Дистанционно"))
           holder.textViewFormatExam.setTextColor(Color.parseColor("#77B577"));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDateExam, textViewTimeExam, textViewProfessorExam, textViewFormatExam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateExam = itemView.findViewById(R.id.textViewDateExam);
            textViewTimeExam = itemView.findViewById(R.id.textViewTimeExam);
            textViewProfessorExam = itemView.findViewById(R.id.textViewProfessorExam);
            textViewFormatExam = itemView.findViewById(R.id.textViewFormatExam);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onSessionClickListener!= null)
                        onSessionClickListener.onSessionLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
