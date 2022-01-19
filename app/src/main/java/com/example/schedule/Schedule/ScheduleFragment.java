package com.example.schedule.Schedule;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment implements View.OnClickListener {
    private ImageView imageViewDown, imageViewUp;
    private Button buttonMon, buttonTue, buttonWed, buttonThu, buttonFri;
    private Switch aSwitch;

    Bundle bundle;

    private List<Schedule> schedules;

    private FirebaseFirestore db;
    private ScheduleAdapter adapter;

    private String dayOfWeek = "Понедельник";

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_schedule);
        db = FirebaseFirestore.getInstance();
        bundle = this.getArguments();
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
        getData(dayOfWeek);
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
        if (bundle.getString("rights", "").equals("admin")) {
            adapter.setOnScheduleClickListener(new ScheduleAdapter.OnScheduleClickListener() {
                @Override
                public void onLongClick(int position) {
                    dellDialog(schedules.get(position).getSubject(), position);
                }
            });
        }
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onClick(View v) {
        buttonMon.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonTue.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonWed.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonThu.setTextColor(getContext().getResources().getColor(R.color.white));
        buttonFri.setTextColor(getContext().getResources().getColor(R.color.white));

        switch (v.getId()) {
            case R.id.buttonMon:
                buttonMon.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("Понедельник");
                dayOfWeek = "Понедельник";
                break;
            case R.id.buttonTue:
                buttonTue.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("Вторник");
                dayOfWeek = "Вторник";
                break;
            case R.id.buttonWed:
                buttonWed.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("Среда");
                dayOfWeek = "Среда";
                break;
            case R.id.buttonThu:
                buttonThu.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("Четверг");
                dayOfWeek = "Четверг";
                break;
            case R.id.buttonFri:
                buttonFri.setTextColor(getContext().getResources().getColor(R.color.teal_200));
                getData("Пятница");
                dayOfWeek = "Пятница";
                break;
        }

    }

    private void getData(String dayOfWeek) {
        Bundle bundle = this.getArguments();
        db.collection("groups")
                .document(bundle.getString("group", ""))
                .collection("Schedule")
                .whereEqualTo("dayOfWeek", dayOfWeek)
                .whereEqualTo("upOrDown", aSwitch.isChecked())
                .orderBy("number", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(
                            @Nullable QuerySnapshot value,
                            @Nullable FirebaseFirestoreException error) {
                        schedules = new ArrayList<>();
                        if (value != null) {
                            for (QueryDocumentSnapshot s : value) {
                                Schedule schedule = new Schedule(
                                        s.get("timeBegining").toString(),
                                        s.get("timeEnd").toString(),
                                        s.get("subject").toString(),
                                        s.get("type").toString(),
                                        s.get("format").toString(),
                                        s.get("lecturer").toString(),
                                        s.getId().toString()
                                );
                                schedules.add(schedule);
                            }
                            adapter.setSchedules(schedules);
                        }
                    }
                });
    }

    private void dellDialog(String name, int position) {
        Bundle bundle = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Удалить заметку?\n " + name);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db
                        .collection("groups")
                        .document(bundle.getString("group"))
                        .collection("Schedule")
                        .document(schedules.get(position).getPathName()).delete();
                Toast.makeText(getContext(), "Заметка успешно удалена " + name, Toast.LENGTH_SHORT).show();
            }

        });
        builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
