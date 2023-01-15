package com.streamavailability.ui.account;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamavailability.MainActivity;
import com.streamavailability.Model.User;
import com.streamavailability.auth.LoginActivity;
import com.streamavailability.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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
                            binding.etInput.setText(user.getUsername());
                            System.out.println("----------" + user.getUsername());
                            binding.etPasswordOne.setText(user.getPassword());
                            binding.etInputBox.setText(user.getCountry());
                        } else {
                            System.out.println("---- Document doesn't exist-----");
                        }
                    } else {
                        // Handle error
                    }
                }
            });

            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setUsername(binding.etInput.getText().toString());
                    user.setPassword(binding.etPasswordOne.getText().toString());
                    user.setCountry(binding.etInputBox.getText().toString());
                    //save data to Firebase

                }
            });

            binding.btnDeleteaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
// User account deleted
                                        Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
// If deletion fails
                                        Toast.makeText(getContext(), "Deletion failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

            binding.btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    // navigate to login page
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            // No user is signed in
            binding.etInput.setText("Not signed in");
            binding.etPasswordOne.setText("Not signed in");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}