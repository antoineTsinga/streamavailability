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
import com.streamavailability.MainActivity;
import com.streamavailability.Model.User;
import com.streamavailability.auth.LoginActivity;
import com.streamavailability.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private User user= new User();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is signed in
            String name = currentUser.getDisplayName();
            binding.etInput.setText(name);
            binding.etPasswordOne.setText(user.getPassword());
           // binding.etInputBox.setText(user.getCountry());

            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setUsername(binding.etInput.getText().toString());
                    user.setPassword(binding.etPasswordOne.getText().toString());
                    user.setCountry(binding.etInputBox.getText().toString());
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}