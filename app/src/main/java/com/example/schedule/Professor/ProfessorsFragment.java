package com.example.schedule.Professor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;
import com.example.schedule.Session.Session;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {
    private List<Professor> professors;
    private FirebaseFirestore db;
    private ProfessorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professors, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_professors);
        db = FirebaseFirestore.getInstance();


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProfessorAdapter();
        recyclerView.setAdapter(adapter);

        setInitData();
        return view;
    }

    private void setInitData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        db.collection("groups").document(bundle.getString("group","")).collection("Lecturers").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value !=null){
                    professors = value.toObjects(Professor.class);
                    adapter.setProfessors(professors);
                }
            }
        });
    }
}
