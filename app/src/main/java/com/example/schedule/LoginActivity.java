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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPass;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, Main.class);
            startActivity(intent);
        }
    }

    public void checkLogin(View view) {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();
        if(email.isEmpty() || pass.isEmpty()) return;
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, Main.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginActivity.this, R.string.toast_loginError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}