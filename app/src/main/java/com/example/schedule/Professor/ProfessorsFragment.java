package com.example.schedule.Professor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schedule.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {
    private List<Professor> professors;
    private FirebaseFirestore db;
    private ProfessorAdapter adapter;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professors, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_professors);
        db = FirebaseFirestore.getInstance();
        bundle = getArguments();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProfessorAdapter();
        recyclerView.setAdapter(adapter);

        setInitData();
        if (bundle.getString("rights", "").equals("admin")) {
            adapter.setOnProfessorClickListener(new ProfessorAdapter.OnProfessorClickListener() {
                @Override
                public void onProfessorClickListener(int position) {
                    dellDialog(professors.get(position).getName(), position);
                }
            });
        }
        return view;
    }

    private void setInitData() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        db.collection("groups")
                .document(bundle.getString("group", ""))
                .collection("Lecturers")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        professors = new ArrayList<>();
                        if (value != null) {
                            for (QueryDocumentSnapshot s : value) {
                                Professor professor = new Professor(
                                        s.get("subject").toString(),
                                        s.get("name").toString(),
                                        s.get("subjType").toString(),
                                        s.getId().toString()
                                );
                                professors.add(professor);
                            }
                            adapter.setProfessors(professors);

                        }
                    }
                });
    }

    private void dellDialog(String name, int position) {
        Bundle bundle = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Удалить заметку?\n " + name);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db
                        .collection("groups")
                        .document(bundle.getString("group"))
                        .collection("Lecturers")
                        .document(professors.get(position).getPathName()).delete();
                Toast.makeText(getContext(), "Заметка успешно удалена " + name, Toast.LENGTH_SHORT).show();
            }

        });
        builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
