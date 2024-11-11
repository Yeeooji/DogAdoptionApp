package com.example.dogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogapp.model.User;
import com.example.dogapp.retrofit.RetrofitService;
import com.example.dogapp.retrofit.UserApi;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeComponents();
    }

    private void initializeComponents() {
        // get the form fields
        EditText registerUsername = findViewById(R.id.registerUsername);
        EditText registerEmail = findViewById(R.id.registerEmail);
        EditText registerPassword = findViewById(R.id.registerPassword);
        EditText confirmPassword = findViewById(R.id.registerPasswordConfirm);
        Button registerButton = findViewById(R.id.registerButton);
        TextView loginButton = findViewById(R.id.registerLoginButton);

        // create a new retrofit service and user api
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        // when register button is clicked
        registerButton.setOnClickListener(v -> {
            String username = registerUsername.getText().toString();
            String email = registerEmail.getText().toString();
            String password = registerPassword.getText().toString();
            String passwordConfirm = confirmPassword.getText().toString();

            // check if fields are empty
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // check if passwords do not match
            if (!password.equals(passwordConfirm)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // check if username is already taken
            userApi.doesUserExist(username, email).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                    // check if username or email already taken
                    if (response.body() != null && response.body()) {
                        Toast.makeText(Register.this, "Username or email already taken", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // if username and email are not taken, register user
                    // create a new user object
                    User user = new User();
                    // set username, email, and password using the form values
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);

                    // save user to database
                    userApi.save(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            // redirect to login page
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(Register.this, "Save failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                    Toast.makeText(Register.this, "Save failed", Toast.LENGTH_SHORT).show();
                }
            });


        });

        // when login button is clicked, redirect to login page
        loginButton.setOnClickListener(v -> {
            // destroy this activity
            finish();
            // start login activity
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        });
    }
}