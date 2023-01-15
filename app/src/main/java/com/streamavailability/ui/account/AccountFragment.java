package com.streamavailability.ui.account;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.streamavailability.Adapter.moviesResult.SpinnerAdapterGeneric;
import com.streamavailability.Model.AvailableRegion;
import com.streamavailability.Model.User;
import com.streamavailability.auth.LoginActivity;
import com.streamavailability.databinding.FragmentAccountBinding;
import com.streamavailability.service.FirebaseService;
import com.streamavailability.ui.moviesresult.MoviesResult;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseService firebaseService;
    private FirebaseUser currentUser;
    private User user;
    private Spinner spinnerRegions;
    private Spinner spinnerLanguage;
    private SpinnerAdapterGeneric<AvailableRegion> regionAdapter;
    private ArrayAdapter<String> languageAdapter;
    private ArrayList<AvailableRegion> regions;
    private String selectedRegion;
    private String selectedLanguage;
    private ArrayList<String> languages = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Locale[] locales = Locale.getAvailableLocales();

        for(int i=0; i< locales.length; i++) {
            Locale locale = locales[i];
            if(!locale.getCountry().equals("") && !locale.getLanguage().equals("")){
                languages.add( locale.getLanguage() + "-" + locale.getCountry());

            }

        }






        regionAdapter = new SpinnerAdapterGeneric(getContext(),
                android.R.layout.simple_spinner_item,regions);
        regionAdapter.setField("nativeName");
        spinnerRegions = binding.country;


        languageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage = binding.language;
        spinnerLanguage.setAdapter(languageAdapter);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                String language = languageAdapter.getItem(position);
                selectedLanguage = language;
                // Here you can do the action you want to...
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });;

        String apiKey = "895d65ebbdd5b9379ad195b07e0ed023";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MoviesResult.fetchDataRegion(apiKey, retrofit, regionAdapter);


        regions = new ArrayList<>();



        regionAdapter.setValues(regions);


        spinnerRegions.setAdapter(regionAdapter);
        // You can create an anonymous listener to handle the event when is selected an spinner item

        spinnerRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                AvailableRegion provider = regionAdapter.getItem(position);
                selectedRegion = provider.getIso();
                // Here you can do the action you want to...
                Toast.makeText(getContext(), "ID: " + provider.getIso() + "\nName: " + provider.getNativeName(),
                        Toast.LENGTH_SHORT).show(); //TODO : call api there to apply filter
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }

        });







        firebaseService = new FirebaseService();

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
                            user = task.getResult().toObject(User.class);
                            binding.etInput.setText(user.getUsername());
                            binding.emailInput.setText(user.getEmail());
                            binding.etPasswordOne.setText(user.getPassword());
                            binding.country.setSelection(getPosition(regions, user.getCountry()));
                            binding.language.setSelection(languages.indexOf(selectedLanguage));

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
                    user.setEmail(binding.txtEmail.getText().toString());
                    user.setPassword(binding.etPasswordOne.getText().toString());
                    if(selectedRegion !=null ) user.setCountry(selectedRegion);
                    if(selectedLanguage !=null ) user.setLanguage(selectedLanguage);

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


    public Integer getPosition(ArrayList<AvailableRegion> listReg, String val){

        for(int i=0; i<listReg.size(); i++ ){
            if(listReg.get(i).getIso().equals(val)) {
                return i;
            }
        }

        return 0;
    }
}