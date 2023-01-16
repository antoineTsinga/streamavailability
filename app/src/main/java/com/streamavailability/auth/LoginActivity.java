package com.streamavailability.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamavailability.MainActivity;
import com.streamavailability.MainViewModel;
import com.streamavailability.Model.User;
import com.streamavailability.R;
import com.streamavailability.databinding.ActivityLogin2Binding;
import com.streamavailability.databinding.FragmentHomeBinding;
import com.streamavailability.service.FirebaseService;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private TextView textToRegistration;
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
        textToRegistration = findViewById(R.id.not_have_account);


        textToRegistration.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (currentUser != null) {
            // User is signed in
            String uid = currentUser.getUid();
            // Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = task.getResult().toObject(User.class);
                            MainViewModel viewModel = new ViewModelProvider(LoginActivity.this).get(MainViewModel.class);
                            viewModel.setUser(user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                        } else {
                            System.out.println("---- Document doesn't exist-----");

                        }
                    } else {
                        // Handle error
                    }
                }
            });
        }

        mSignInButton.setOnClickListener(v -> {


            String username = mUsernameField.getText().toString();
            String password = mPasswordField.getText().toString();

            System.out.println(username);
            System.out.println(password);
            mFirebaseService.signInWithEmailAndPassword(username, password, new OnCompleteListener() {
                @Override
                public void onComplete(Task task) {
                    if (task.isSuccessful()) {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        mFirebaseService.getUserById(currentUser.getUid(), new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    User user = (User) task.getResult();
                                    MainViewModel viewModel = new ViewModelProvider(LoginActivity.this).get(MainViewModel.class);
                                    viewModel.setUser(user);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user", user);

                                    startActivity(intent);
                                }
                            }
                        });

                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}