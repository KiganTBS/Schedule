package com.example.schedule.Professor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;

import java.util.ArrayList;

public class ProfessorsFragment extends Fragment {
    private ArrayList<Professor> professors = new ArrayList<Professor>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professors, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_professors);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProfessorAdapter adapter = new ProfessorAdapter(getActivity(), professors);
        recyclerView.setAdapter(adapter);

        setInitData();
        return view;
    }

    private void setInitData() {
        professors.add(new Professor("Think","Lord","aboba"));
        professors.add(new Professor("Системное программное обеспечение","Королькова Т.В.","(Лекция)"));
        professors.add(new Professor("УиАИС","Беленькая М.Н.","(Лекция)"));

    }
}
