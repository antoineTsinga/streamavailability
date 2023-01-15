package com.streamavailability.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamavailability.Model.User;

public class FirebaseService {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;



    public FirebaseService() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void createUserWithEmailAndPassword(String password, String username, final OnCompleteListener listener) {
        System.out.println("createUserWithEmailAndPassword");
       mAuth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(task -> {

                    System.out.println("addOnCompleteListener");
                    if (task.isSuccessful()) {
                        Log.d("FirebaseService", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        User newUser = new User(username, password);
                        createUserInFirestore(user.getUid(), newUser);
                        listener.onComplete(task);
                    } else {
                        Log.w("FirebaseService", "createUserWithEmail:failure", task.getException());
                        listener.onComplete(task);
                    }
                });



    }

    public void signInWithEmailAndPassword(String username, String password, final OnCompleteListener listener) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("FirebaseService", "signInWithEmail:success");
                            listener.onComplete(task);
                        } else {
                            Log.w("FirebaseService", "signInWithEmail:failure", task.getException());
                            listener.onComplete(task);
                        }
                    }
                });
    }

    public void getUserById(String userId, final OnCompleteListener listener) {
        mFirestore.collection("users").document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("FirebaseService", "getUserById:success");
                            listener.onComplete(task);
                        } else {
                            Log.w("FirebaseService", "getUserById:failure", task.getException());
                            listener.onComplete(task);
                        }
                    }
                });
    }

    public void deleteUserById(String userId ){
        mFirestore.collection("user").document(userId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("FirebaseService", "User deletion success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseService", "Error deleting user to Firestore", e);
                    }
                });
    }





    private void createUserInFirestore(String userId, User user) {
        mFirestore.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("FirebaseService", "User creation success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseService", "Error adding user to Firestore", e);
                    }
                });
    }

}
