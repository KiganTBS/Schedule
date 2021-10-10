package com.example.schedule;

import android.os.Bundle;
import android.util.Log;
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

import java.security.acl.Group;
import java.util.ArrayList;

public class ChangeFragment extends Fragment {
    private FirebaseFirestore firestore;
    private Spinner spinnerName, spinnerTypeOfInfChange;
    private androidx.constraintlayout.widget.Group group;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;
    private EditText editTextChange1, editTextChange2, editTextChange3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_change);
        firestore = FirebaseFirestore.getInstance();
        spinnerName = view.findViewById(R.id.spinnerNameOfInf);
        spinnerTypeOfInfChange = view.findViewById(R.id.spinnerTypeOfInfChange);
        group =  view.findViewById(R.id.groupChange);

        editTextChange1 = view.findViewById(R.id.editTextChange);
        editTextChange2 = view.findViewById(R.id.editTextChange2);
        editTextChange3 = view.findViewById(R.id.editTextChange3);


        spinnerTypeOfInfChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        editTextChange1.setText(R.string.tt);
                        editTextChange2.setText(R.string.title_professors);
                        editTextChange3.setText("Cab/Dis");
                        group.setVisibility(View.VISIBLE);
                        getListInf("Schedule");
                        break;
                    case 1:
                        editTextChange1.setText("Name exam");
                        editTextChange2.setText("Time exam");
                        editTextChange3.setText("Date exam");
                        group.setVisibility(View.GONE);
                        getListInf("Session");
                        break;
                    case 2:
                        editTextChange1.setText("Name of subject");
                        editTextChange2.setText(R.string.title_professors);
                        editTextChange3.setText("Type of subject");
                        group.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                group.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void getListInf(String typeOfInf){
        Bundle bundle = this.getArguments();
        Toast.makeText(getActivity(), bundle.getString("group","")+"\n"+typeOfInf, Toast.LENGTH_SHORT).show();
        firestore.collection("groups").document(bundle.getString("group","")).collection(typeOfInf).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    data = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        switch (typeOfInf){
                            case "Schedule":
                                data.add((String) document.get("subject") +"\n" +document.get("dayOfWeek")+"\n"+document.get("type"));
                                Toast.makeText(getActivity(), "1:"+document.get("subject"), Toast.LENGTH_SHORT).show();
                                break;
                            case "Session":
                                data.add((String) document.get("subject") +"\n" +document.get("timeExam")+"\n"+document.get("dateExam"));
                                break;
                        }
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
                        adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
                        spinnerName.setAdapter(adapter);
                    }
                } else {
                    Log.d("aboba", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
