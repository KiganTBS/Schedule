package com.example.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChangeFragment extends Fragment {
    private FirebaseFirestore firestore;

    private Spinner spinnerName, spinnerTypeOfInfChange;
    private androidx.constraintlayout.widget.Group group;
    private EditText editTextChange1, editTextChange2, editTextChange3;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_change);
        firestore = FirebaseFirestore.getInstance();
        spinnerName = view.findViewById(R.id.spinnerNameOfInf);
        spinnerTypeOfInfChange = view.findViewById(R.id.spinnerTypeOfInfChange);
        group = view.findViewById(R.id.groupChange);

        editTextChange1 = view.findViewById(R.id.editTextChange);
        editTextChange2 = view.findViewById(R.id.editTextChange2);
        editTextChange3 = view.findViewById(R.id.editTextChange3);

        selectTypeInfo();
        editInfo();

        return view;
    }

    private void selectTypeInfo() {
        spinnerTypeOfInfChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.title_professors);
                        editTextChange3.setHint(R.string.cab_dist);
                        group.setVisibility(View.VISIBLE);
                        getListInf("Schedule");
                        break;
                    case 1:
                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.time);
                        editTextChange3.setHint(R.string.date);
                        group.setVisibility(View.GONE);
                        getListInf("Session");
                        break;
                    case 2:
                        editTextChange1.setHint(R.string.text_name);
                        editTextChange2.setHint(R.string.title_professors);
                        editTextChange3.setHint(R.string.type_of_occupation);
                        group.setVisibility(View.GONE);
                        getListInf("Lecturers");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                firestore.collection("groups").document(bundle.getString("group", "")).collection(typeOfInf).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> data = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                switch (typeOfInf) {
                                    case "Schedule":
                                        data.add((String) document.get("subject")
                                                + "\n" + document.get("dayOfWeek")
                                                + "\n" + document.get("type")
                                                + "\n" + upOrDown((Boolean) document.get("upOrDown")));
                                        break;
                                    case "Session":
                                        data.add((String) document.get("subject")
                                                + "\n" + document.get("timeExam")
                                                + "\n" + document.get("dateExam"));
                                        break;
                                    case "Lecturers":
                                        data.add((String) document.get("name")
                                                + "\n" + document.get("subject")
                                                + "\n" + document.get("subjType"));
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
        }).start();


    }

    private String upOrDown(boolean typeOfWeek) {
        return typeOfWeek ? "Нижняя" : "Верхняя";
    }

    private void editInfo() {
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editTextChange1.setText(spinnerName.getSelectedItem().toString().trim());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "i", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
