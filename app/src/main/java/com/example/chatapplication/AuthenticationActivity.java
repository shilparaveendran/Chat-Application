package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chatapplication.databinding.ActivityAuthenticationBinding;
import com.example.chatapplication.databinding.ActivityStartBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationActivity extends AppCompatActivity {
    ActivityAuthenticationBinding binding;
    String name,email,password;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.edEmail.getText().toString();
                password = binding.edPassword.getText().toString();
                login();

            }
        });
        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.edName.getText().toString();
                email = binding.edEmail.getText().toString();
                password = binding.edPassword.getText().toString();
                signUp();

            }
        });
    }
    protected  void onStart() {

        super.onStart();
        Toast.makeText(this, "started", Toast.LENGTH_SHORT).show();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(AuthenticationActivity.this,StartActivity.class));
            finish();
        }
    }
    private void login(){
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(AuthenticationActivity.this,StartActivity.class));
                        finish();
                    }
                });
    }
    private void signUp(){
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        startActivity(new Intent(AuthenticationActivity.this,StartActivity.class));
                        finish();
                    }
                });
    }
   
    

}