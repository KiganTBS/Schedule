package com.example.schedule.Schedule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ScheduleFragment extends Fragment implements View.OnClickListener {
    ImageView imageViewDown, imageViewUp;
    Button buttonMon, buttonTue, buttonWed, buttonThu, buttonFri;
    Switch aSwitch;
    List<Schedule> schedules;
    FirebaseFirestore db;
    private ScheduleAdapter adapter;

    private String dayOfWeek="mon";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_schedule);
        db = FirebaseFirestore.getInstance();

        imageViewDown = view.findViewById(R.id.imageViewUp);
        imageViewUp = view.findViewById(R.id.imageViewDown);
        aSwitch = view.findViewById(R.id.switchUpDown);

        buttonMon = view.findViewById(R.id.buttonMon);
        buttonTue = view.findViewById(R.id.buttonTue);
        buttonWed = view.findViewById(R.id.buttonWed);
        buttonThu = view.findViewById(R.id.buttonThu);
        buttonFri = view.findViewById(R.id.buttonFri);

        buttonMon.setOnClickListener(this::onClick);
        buttonTue.setOnClickListener(this::onClick);
        buttonWed.setOnClickListener(this::onClick);
        buttonThu.setOnClickListener(this::onClick);
        buttonFri.setOnClickListener(this::onClick);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ScheduleAdapter();
        recyclerView.setAdapter(adapter);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    imageViewDown.setColorFilter(getContext().getResources().getColor(R.color.white));
                    imageViewUp.setColorFilter(getContext().getResources().getColor(R.color.teal_200));
                } else {
                    imageViewDown.setColorFilter(getContext().getResources().getColor(R.color.teal_200));
                    imageViewUp.setColorFilter(getContext().getResources().getColor(R.color.white));
                }
                getData(dayOfWeek);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData("mon");
   }

    @Override
    public void onClick(View v) {
        buttonMon.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonTue.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonWed.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonThu.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonFri.setTextColor(getContext().getResources().getColor(R.color.white));

        switch (v.getId()){
            case R.id.buttonMon:
                buttonMon.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("mon");
                dayOfWeek = "mon";
                break;
            case R.id.buttonTue:
                buttonTue.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("tue");
                dayOfWeek = "tue";
                break;
            case R.id.buttonWed:
                buttonWed.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                break;
            case R.id.buttonThu:
                buttonThu.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                break;
            case R.id.buttonFri:
                buttonFri.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                break;
        }
    }

    private void getData(String dayOfWeek){
        db.collection("schedule")
                .whereEqualTo("dayOfWeek", dayOfWeek)
                .whereEqualTo("upOrDown",aSwitch.isChecked())
                .orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value !=null){
                    schedules = value.toObjects(Schedule.class);
                    adapter.setSchedules(schedules);
                }
            }
        });
    }
}
