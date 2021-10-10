package com.example.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String userGroup = "null";
    private String userRights = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getInstance().getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else{
            getUserInfo();
        }


    }

    private void getUserInfo() {
        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    if (auth.getUid().equals(doc.get("UID"))) {
                        userGroup = doc.get("group").toString();
                        userRights = doc.get("rights").toString();
                        startIntent();
                    }
                }
            }
        });
    }

    private void startIntent() {
        Intent intent = new Intent(this, Main.class)
                .putExtra("group", userGroup)
                .putExtra("rights", userRights);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}