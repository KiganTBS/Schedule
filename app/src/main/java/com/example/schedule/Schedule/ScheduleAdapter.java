package com.example.schedule.Schedule;

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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> schedules;
    private OnScheduleClickListener onScheduleClickListener;

    public ScheduleAdapter() {
        this.schedules = new ArrayList<>();
    }

    interface OnScheduleClickListener{
        void onLongClick(int position);
    }

    public void setOnScheduleClickListener(OnScheduleClickListener onScheduleClickListener) {
        this.onScheduleClickListener = onScheduleClickListener;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.textViewTimeStart.setText(schedule.getTimeBegining());
        holder.textViewTimeEnd.setText(schedule.getTimeEnd());
        holder.textViewNameLesson.setText(schedule.getSubject());
        holder.textViewTypeLesson.setText(schedule.getType());
        holder.textViewNameProfessor.setText(schedule.getLecturer());
        holder.textViewTypeOfPresence.setText(schedule.getFormat());

        if (schedule.getFormat().equals("Дистанционно"))
            holder.textViewTypeOfPresence.setTextColor(Color.parseColor("#77B577"));

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTimeStart, textViewTimeEnd, textViewNameLesson, textViewTypeLesson, textViewTypeOfPresence, textViewNameProfessor;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTimeStart = itemView.findViewById(R.id.textViewTimeStart);
            textViewTimeEnd = itemView.findViewById(R.id.textViewTimeEnd);
            textViewNameLesson = itemView.findViewById(R.id.textViewNameLesson);
            textViewTypeLesson = itemView.findViewById(R.id.textViewTypeLesson);
            textViewTypeOfPresence = itemView.findViewById(R.id.textViewTypeOfPresence);
            textViewNameProfessor = itemView.findViewById(R.id.textViewNameProfessor);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onScheduleClickListener != null)
                        onScheduleClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
