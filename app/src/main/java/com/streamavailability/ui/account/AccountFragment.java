package com.streamavailability.ui.account;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
import com.streamavailability.service.FirebaseService;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseService firebaseService;
    private FirebaseUser currentUser;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        firebaseService = new FirebaseService();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is signed in
            String uid = currentUser.getUid();
            // Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(uid);
            System.out.println("-----------------------id :"+uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        System.out.println("----------document----------"+document.toObject(User.class));
                        if (document.exists()) {
                            user = task.getResult().toObject(User.class);
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
                    docRef.set(user).addOnCompleteListener(v->{
                        Toast.makeText(getContext(), "Success update", Toast.LENGTH_SHORT).show();
                    });


                }
            });

            binding.btnDeleteaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Delete account");
                    builder.setMessage("Are you sure you want to delete your account ? This action is irreversible;");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AuthCredential authCredential = EmailAuthProvider.getCredential(user.getUsername(), user.getPassword());
                                    currentUser.reauthenticate(authCredential).addOnCompleteListener(t->{
                                        if(t.isSuccessful()){
                                            currentUser.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // User account deleted
                                                                firebaseService.deleteUserById(uid);
                                                                Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                                startActivity(intent);

                                                            } else {
                                                                // If deletion failed
                                                                System.out.println("----------------- "+  task.getException());
                                                                Toast.makeText(getContext(), "Deletion failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });

                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


                    AlertDialog dialog = builder.create();
                    dialog.show();

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