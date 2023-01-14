package com.streamavailability.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.streamavailability.MainActivity;
import com.streamavailability.R;
import com.streamavailability.databinding.ActivityLogin2Binding;
import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.service.FirebaseService;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private FirebaseService mFirebaseService;
    private ActivityLogin2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        binding = ActivityLogin2Binding.inflate(getLayoutInflater());

        mFirebaseService = new FirebaseService();

        mUsernameField = findViewById(R.id.username_field_login);
        mSignInButton = findViewById(R.id.sign_in_button2);
        mPasswordField = findViewById(R.id.password_field_login);


        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        mSignInButton.setOnClickListener(v -> {

            System.out.println("ygviberjfvbfojfvjsrnreonob");

            String username = mUsernameField.getText().toString();
            String password = mPasswordField.getText().toString();

            System.out.println(username);
            System.out.println(password);
            mFirebaseService.signInWithEmailAndPassword(username, password, new OnCompleteListener() {
                @Override
                public void onComplete(Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}