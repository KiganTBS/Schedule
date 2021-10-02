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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SessionFragment extends Fragment {
    private List<Session> sessions;
    private FirebaseFirestore db;
    private SessionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_session);
        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSession);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SessionAdapter();
        recyclerView.setAdapter(adapter);

        setInitData();
        return view;
    }

    private void setInitData(){
        db.collection("session").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value !=null){
                    sessions = value.toObjects(Session.class);
                    adapter.setSessions(sessions);
                }
            }
        });
    }
}
