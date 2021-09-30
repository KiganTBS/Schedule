package com.example.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class AddFragment extends Fragment {
    private EditText editTextTitleAdd, editTextTitleAdd2, editTextTitleAdd3;
    private Spinner spinnerTypeOfInf;
    private Switch switchUpOrDown;
    private FloatingActionButton but;
    private Group group;
    FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_add);
        firestore = FirebaseFirestore.getInstance();


        spinnerTypeOfInf = view.findViewById(R.id.spinnerTypeOfInf);
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
        return view;
    }

}
