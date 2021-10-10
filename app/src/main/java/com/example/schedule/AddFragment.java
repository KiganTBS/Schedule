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
    private EditText editTextTitleAdd, editTextTitleAdd2, editTextTitleAdd3;
    private Spinner spinnerTypeOfInf, spinnerDayOfWeek, spinnerTime;
    private Switch switchUpOrDown;
    private FloatingActionButton but;
    private Group group;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_add);
        firestore = FirebaseFirestore.getInstance();

        spinnerDayOfWeek = view.findViewById(R.id.spinnerDayOfWeek);
        spinnerTime = view.findViewById(R.id.spinnerTime);
        spinnerTypeOfInf = view.findViewById(R.id.spinnerTypeOfInfAdd);
        but = view.findViewById(R.id.floatingActionButton);
        group = (Group) view.findViewById(R.id.groupComponents);
        editTextTitleAdd = view.findViewById(R.id.editTextTitleAdd);
        editTextTitleAdd2 = view.findViewById(R.id.editTextTitleAdd2);
        editTextTitleAdd3 = view.findViewById(R.id.editTextTitleAdd3);
        switchUpOrDown = view.findViewById(R.id.switchUpOrDown);

        spinnerTypeOfInf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        group.setVisibility(View.VISIBLE);
                        editTextTitleAdd.setHint(R.string.text_name);

                        break;
                    case 1:
                        group.setVisibility(View.GONE);
                        editTextTitleAdd.setHint("Date");
                        editTextTitleAdd2.setHint("Time");
                        editTextTitleAdd3.setHint("Name of ex");
                        break;
                    case 2:
                        group.setVisibility(View.GONE);
                        editTextTitleAdd.setHint("Name professor");
                        editTextTitleAdd2.setHint("Name subject");
                        editTextTitleAdd3.setHint("Type of subject");
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
                addData();
            }
        });
        return view;
    }

    private void addData(){
        Bundle bundle = this.getArguments();
        HashMap<String,Object> docMap = new HashMap();

        docMap.put("subject",editTextTitleAdd.getText().toString().trim());
        docMap.put("lecturer",editTextTitleAdd2.getText().toString().trim());
        docMap.put("format",editTextTitleAdd3.getText().toString().trim());
        docMap.put("dayOfWeek",spinnerDayOfWeek.getSelectedItem().toString().trim());
        docMap.put("number",spinnerTime.getSelectedItemId()+1);
        docMap.put("timeBegining",spinnerTime.getSelectedItem().toString().substring
                (0,spinnerTime.getSelectedItem().toString().indexOf(" ")));
        docMap.put("timeEnd",spinnerTime.getSelectedItem().toString().substring
                (spinnerTime.getSelectedItem().toString().indexOf(" ")+3,spinnerTime.getSelectedItem().toString().length()));
        docMap.put("type","aaaa");
        docMap.put("upOrDown",switchUpOrDown.isChecked());

        firestore.collection("groups").document(bundle.getString("group","")).collection("Schedule").add(docMap);
    }

}
