package com.example.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPass;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String userGroup = "null";
    private String userRights = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    public void checkLogin(View view) {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) return;

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getUserInfo();
                } else
                    Toast.makeText(LoginActivity.this, R.string.toast_loginError, Toast.LENGTH_SHORT).show();
            }
        });
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
    private void startIntent(){
       Intent intent = new Intent(LoginActivity.this, Main.class)
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