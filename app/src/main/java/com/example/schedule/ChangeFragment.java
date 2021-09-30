package com.example.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    FirebaseFirestore firestore;
    Spinner spinner;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_change);
        firestore = FirebaseFirestore.getInstance();
        spinner = view.findViewById(R.id.spinnerNameOfInf);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firestore.collection("schedule").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    data = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        data.add((String) document.get("nameLesson") +"\n" +document.get("dayOfWeek")+"\n"+document.get("typeLesson"));
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
                        adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }
                } else {
                    Log.d("aboba", "Error getting documents: ", task.getException());
                }
            }
        });
    }
}
