package com.example.schedule.Session;

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

public class SessionFragment extends Fragment {
    ArrayList<Session> sessions = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_session);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSession);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SessionAdapter adapter = new SessionAdapter(getActivity(), sessions);
        recyclerView.setAdapter(adapter);

        setInitData();
        return view;
    }

    private void setInitData(){
        sessions.add(new Session("21.01.2022","9:30","Компьютерные сети"));
        sessions.add(new Session("29.01.2022","9:30","УиАИС"));
        sessions.add(new Session("06.02.2022","13:10","ПКСП"));
    }
}
