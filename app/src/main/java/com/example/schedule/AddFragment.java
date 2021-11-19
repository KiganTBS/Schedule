package com.example.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddFragment extends Fragment {
    private EditText editTextTitleAdd, editTextTitleAdd2, editTextTitleAdd3, editTextTitleAdd4;
    private Spinner spinnerTypeOfInf, spinnerDayOfWeek, spinnerTime, spinnerTypeOfSchedule;
    private Switch switchUpOrDown;
    private FloatingActionButton but;
    private Group group;
    private FirebaseFirestore firestore;
    private String typeOfSubj = "Schedule";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_add);
        firestore = FirebaseFirestore.getInstance();

        spinnerDayOfWeek = view.findViewById(R.id.spinnerDayOfWeek);
        spinnerTime = view.findViewById(R.id.spinnerTime);
        spinnerTypeOfInf = view.findViewById(R.id.spinnerTypeOfInfAdd);
        spinnerTypeOfSchedule = view.findViewById(R.id.spinnerTypeScheduleAdd);

        but = view.findViewById(R.id.floatingActionButton);

        group = (Group) view.findViewById(R.id.groupComponents);

        editTextTitleAdd = view.findViewById(R.id.editTextTitleAdd);
        editTextTitleAdd2 = view.findViewById(R.id.editTextTitleAdd2);
        editTextTitleAdd3 = view.findViewById(R.id.editTextTitleAdd3);
        editTextTitleAdd4 = view.findViewById(R.id.editTextTitleAdd4);
        switchUpOrDown = view.findViewById(R.id.switchUpOrDown);

        spinnerTypeOfInf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        group.setVisibility(View.VISIBLE);
                        editTextTitleAdd4.setVisibility(View.GONE);
                        editTextTitleAdd.setHint(R.string.text_name);
                        typeOfSubj = "Schedule";
                        break;
                    case 1:
                        group.setVisibility(View.GONE);
                        editTextTitleAdd4.setVisibility(View.VISIBLE);
                        editTextTitleAdd.setHint("Name of ex");
                        editTextTitleAdd2.setHint("Date");
                        editTextTitleAdd3.setHint("Time");
                        editTextTitleAdd4.setHint("Format");
                        typeOfSubj = "Session";
                        break;
                    case 2:
                        group.setVisibility(View.GONE);
                        editTextTitleAdd4.setVisibility(View.GONE);
                        editTextTitleAdd.setHint("Name professor");
                        editTextTitleAdd2.setHint("Name subject");
                        editTextTitleAdd3.setHint("Type of subject");
                        typeOfSubj = "Lecturers";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                group.setVisibility(View.VISIBLE);
            }
        });

        this.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isField()) addData(typeOfSubj);
            }
        });
        return view;
    }

    private void addData(String typeOfInf) {
        Bundle bundle = this.getArguments();
        HashMap<String, Object> docMap = new HashMap();
            switch (typeOfInf) {
                case "Schedule":
                    docMap.put("subject", editTextTitleAdd.getText().toString().trim());
                    docMap.put("lecturer", editTextTitleAdd2.getText().toString().trim());
                    docMap.put("format", editTextTitleAdd3.getText().toString().trim());
                    docMap.put("dayOfWeek", spinnerDayOfWeek.getSelectedItem().toString().trim());
                    docMap.put("number", spinnerTime.getSelectedItemId() + 1);
                    docMap.put("timeBegining", spinnerTime.getSelectedItem().toString().substring(0, spinnerTime.getSelectedItem().toString().indexOf(" ")));
                    docMap.put("timeEnd", spinnerTime.getSelectedItem().toString().substring(spinnerTime.getSelectedItem().toString().indexOf(" ") + 3, spinnerTime.getSelectedItem().toString().length()));
                    docMap.put("type", spinnerTypeOfSchedule.getSelectedItem().toString());
                    docMap.put("upOrDown", switchUpOrDown.isChecked());

                    firestore.collection("groups")
                            .document(bundle.getString("group", ""))
                            .collection(typeOfInf).add(docMap);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AddFragment()).commit();
                    break;

                case "Session":
                    docMap.put("subject", editTextTitleAdd.getText().toString().trim());
                    docMap.put("dateExam", editTextTitleAdd2.getText().toString().trim());
                    docMap.put("timeExam", editTextTitleAdd3.getText().toString().trim());
                    docMap.put("format", editTextTitleAdd4.getText().toString().trim());

                    firestore.collection("groups")
                            .document(bundle.getString("group", ""))
                            .collection(typeOfInf).add(docMap);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AddFragment()).commit();
                    break;

                case "Lecturers":
                    docMap.put("name",editTextTitleAdd.getText().toString().trim());
                    docMap.put("subject",editTextTitleAdd2.getText().toString().trim());
                    docMap.put("subjType",editTextTitleAdd3.getText().toString().trim());

                    firestore.collection("groups")
                            .document(bundle.getString("group", ""))
                            .collection(typeOfInf).add(docMap);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AddFragment()).commit();
                    break;
            }

        Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
    }

    private boolean isField() {
        return !editTextTitleAdd.getText().toString().isEmpty()
                && !editTextTitleAdd2.getText().toString().isEmpty()
                && !editTextTitleAdd3.getText().toString().isEmpty();
    }

}
