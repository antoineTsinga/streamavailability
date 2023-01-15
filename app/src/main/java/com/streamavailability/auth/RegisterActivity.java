package com.streamavailability.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.streamavailability.MainActivity;
import com.streamavailability.R;
import com.streamavailability.service.FirebaseService;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailField;
    private TextView textToLogin;
    private EditText mPasswordField;
    private EditText mUsernameField;
    private Button mSignUpButton;
    private FirebaseService mFirebaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseService = new FirebaseService();

        mEmailField = findViewById(R.id.emailField);
        mPasswordField = findViewById(R.id.passwordField);
        mUsernameField = findViewById(R.id.usernameField);
        mSignUpButton = findViewById(R.id.signUpButton);



        mSignUpButton.setOnClickListener(v -> {
            String email = mEmailField.getText().toString();
            String password = mPasswordField.getText().toString();
            String username = mUsernameField.getText().toString();

            System.out.println("Click " + email +" "+ password);

            mFirebaseService.createUserWithEmailAndPassword(password,username, email, new OnCompleteListener() {
                @Override
                public void onComplete(Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}