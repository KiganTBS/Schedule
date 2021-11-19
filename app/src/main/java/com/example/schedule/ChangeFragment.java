package com.example.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.schedule.Schedule.ScheduleFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeFragment extends Fragment {
    private FirebaseFirestore firestore;

    private Spinner spinnerName, spinnerTypeOfInfChange, spinnerDayOfWeek, spinnerTime, spinnerTypeSchedule;
    private androidx.constraintlayout.widget.Group group;
    private EditText editTextChange1, editTextChange2, editTextChange3, editTextChange4;
    private Switch switcUpOrDown;
    private FloatingActionButton buttonChange;

    private ArrayList<HashMap<String, Object>> dataMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_change);

        firestore = FirebaseFirestore.getInstance();
        group = view.findViewById(R.id.groupChange);

        spinnerName = view.findViewById(R.id.spinnerNameOfInf);
        spinnerTypeOfInfChange = view.findViewById(R.id.spinnerTypeOfInfChange);
        spinnerDayOfWeek = view.findViewById(R.id.spinnerDayOfWeek2);
        spinnerTime = view.findViewById(R.id.spinnerTime2);
        spinnerTypeSchedule = view.findViewById(R.id.spinnerTypeOfScheduleChange);

        editTextChange1 = view.findViewById(R.id.editTextChange);
        editTextChange2 = view.findViewById(R.id.editTextChange2);
        editTextChange3 = view.findViewById(R.id.editTextChange3);
        editTextChange4 = view.findViewById(R.id.editTextChange4);

        switcUpOrDown = view.findViewById(R.id.switchUpOrDown2);

        buttonChange = view.findViewById(R.id.floatingActionButtonChange);

        selectTypeInfo();
        editInfo();

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEditInfo();
            }
        });
        return view;
    }

    private void selectTypeInfo() {
        spinnerTypeOfInfChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        editTextChange4.setVisibility(View.GONE);
                        group.setVisibility(View.VISIBLE);

                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.title_professors);
                        editTextChange3.setHint(R.string.cab_dist);
                        getListInf(typeOfInf(i));
                        break;
                    case 1:
                        editTextChange4.setVisibility(View.VISIBLE);
                        group.setVisibility(View.GONE);

                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.time);
                        editTextChange3.setHint(R.string.date);

                        getListInf(typeOfInf(i));
                        break;
                    case 2:
                        editTextChange4.setVisibility(View.GONE);
                        group.setVisibility(View.GONE);

                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.title_professors);
                        editTextChange3.setHint(R.string.type_of_occupation);

                        getListInf(typeOfInf(i));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                group.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getListInf(String typeOfInf) {
        Bundle bundle = this.getArguments();

        firestore.collection("groups")
                .document(bundle.getString("group", ""))
                .collection(typeOfInf).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> data = new ArrayList<>();
                            dataMap = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String, Object> initData = new HashMap<>();
                                switch (typeOfInf) {
                                    case "Schedule":
                                        data.add((String) document.get("subject")
                                                + "\n" + document.get("dayOfWeek")
                                                + "\n" + document.get("type")
                                                + "\n" + upOrDown((Boolean) document.get("upOrDown")));

                                        initData.put("id", document.getId());
                                        initData.put("subject", document.get("subject"));
                                        initData.put("upOrDown", document.get("upOrDown"));
                                        initData.put("format", document.get("format"));
                                        initData.put("lecturer", document.get("lecturer"));
                                        initData.put("number", document.get("number"));
                                        initData.put("timeBegining", document.get("timeBegining"));
                                        initData.put("type", document.get("type"));
                                        initData.put("dayOfWeek", document.get("dayOfWeek"));
                                        dataMap.add(initData);
                                        break;
                                    case "Session":
                                        data.add((String) document.get("subject")
                                                + "\n" + document.get("timeExam")
                                                + "\n" + document.get("dateExam"));

                                        initData.put("id", document.getId());
                                        initData.put("dateExam", document.get("dateExam"));
                                        initData.put("format", document.get("format"));
                                        initData.put("subject", document.get("subject"));
                                        initData.put("timeExam", document.get("timeExam"));
                                        dataMap.add(initData);
                                        break;
                                    case "Lecturers":
                                        data.add((String) document.get("name")
                                                + "\n" + document.get("subject")
                                                + "\n" + document.get("subjType"));

                                        initData.put("id", document.getId());
                                        initData.put("name", document.get("name"));
                                        initData.put("subject", document.get("subject"));
                                        initData.put("subjType", document.get("subjType"));
                                        dataMap.add(initData);
                                        break;
                                }
                                ArrayAdapter<String> adapterforListInf = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
                                adapterforListInf.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
                                spinnerName.setAdapter(adapterforListInf);
                            }
                        }
                    }
                });
    }

    private void editInfo() {
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinnerTypeOfInfChange.getSelectedItemPosition()) {
                    case 0:
                        editTextChange1.setText(dataMap.get(i).get("subject").toString());
                        editTextChange2.setText(dataMap.get(i).get("lecturer").toString());
                        editTextChange3.setText(dataMap.get(i).get("format").toString());
                        spinnerTypeSchedule.setSelection(typeSchedule(dataMap.get(i).get("type").toString()));
                        spinnerTime.setSelection(time(dataMap.get(i).get("timeBegining").toString()));
                        spinnerDayOfWeek.setSelection(dayOfWeek(dataMap.get(i).get("dayOfWeek").toString()));
                        switcUpOrDown.setChecked((Boolean) dataMap.get(i).get("upOrDown"));
                        break;
                    case 1:
                        editTextChange1.setText(dataMap.get(i).get("subject").toString());
                        editTextChange2.setText(dataMap.get(i).get("timeExam").toString());
                        editTextChange3.setText(dataMap.get(i).get("dateExam").toString());
                        editTextChange4.setText(dataMap.get(i).get("format").toString());
                        break;
                    case 2:
                        editTextChange1.setText(dataMap.get(i).get("name").toString());
                        editTextChange2.setText(dataMap.get(i).get("subject").toString());
                        editTextChange3.setText(dataMap.get(i).get("subjType").toString());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "i", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendEditInfo() {
        Bundle bundle = this.getArguments();

        DocumentReference document = firestore.collection("groups")
                .document(bundle.getString("group", ""))
                .collection(typeOfInf(spinnerTypeOfInfChange.getSelectedItemPosition()))
                .document(dataMap.get(spinnerName.getSelectedItemPosition()).get("id").toString());

        switch (spinnerTypeOfInfChange.getSelectedItemPosition()) {
            case 0:
                document.update("subject", editTextChange1.getText().toString());
                document.update("lecturer", editTextChange2.getText().toString());
                document.update("format", editTextChange3.getText().toString());
                document.update("type", spinnerTypeSchedule.getSelectedItem().toString());
                document.update("timeBegining", spinnerTime.getSelectedItem().toString().substring(0, spinnerTime.getSelectedItem().toString().indexOf(" ")));
                document.update("timeEnd", spinnerTime.getSelectedItem().toString().substring(spinnerTime.getSelectedItem().toString().indexOf(" ") + 3, spinnerTime.getSelectedItem().toString().length()));
                document.update("dayOfWeek", spinnerDayOfWeek.getSelectedItem().toString());
                document.update("number", spinnerTime.getSelectedItemPosition() + 1);
                document.update("upOrDown", switcUpOrDown.isChecked());

                break;
            case 1:
                document.update("subject", editTextChange1.getText().toString());
                document.update("timeExam", editTextChange2.getText().toString());
                document.update("dateExam", editTextChange3.getText().toString());
                document.update("format", editTextChange4.getText().toString());
                break;
            case 2:
                document.update("name", editTextChange1.getText().toString());
                document.update("subject", editTextChange2.getText().toString());
                document.update("subjType", editTextChange3.getText().toString());

                break;
        }
        Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
    }

    private String upOrDown(boolean typeOfWeek) {
        return typeOfWeek ? "Нижняя" : "Верхняя";
    }

    private int time(String time) {
        switch (time) {
            case "11:20":
                return 1;
            case "13:10":
                return 2;
            case "15:25":
                return 3;
            case "17:15":
                return 4;
            default:
                return 0;
        }
    }

    private int dayOfWeek(String dayOfweek) {
        switch (dayOfweek) {
            case "Вторник":
                return 1;
            case "Среда":
                return 2;
            case "Четверг":
                return 3;
            case "Пятница":
                return 4;
            default:
                return 0;

        }
    }

    private int typeSchedule(String typeSchedule) {
        switch (typeSchedule) {
            case "Практическая":
                return 1;
            case "Лабораторная":
                return 2;
            default:
                return 0;

        }
    }

    private String typeOfInf(int i) {
        switch (i) {
            case 1:
                return "Session";
            case 2:
                return "Lecturers";
            default:
                return "Schedule";
        }
    }

}
