package com.example.dogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogapp.model.User;
import com.example.dogapp.retrofit.RetrofitService;
import com.example.dogapp.retrofit.UserApi;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        EditText input_username = findViewById(R.id.usernameEditTxt);
        EditText input_email = findViewById(R.id.emailEditTxt);
        Button btn_add = findViewById(R.id.btnAdd);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        btn_add.setOnClickListener(v -> {
            String username = input_username.getText().toString();
            String email = input_email.getText().toString();

            User user = new User(username, email);
            userApi.save(user).enqueue(
                    new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            Toast.makeText(MainActivity.this, "Save successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, admin_landing_page.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                            Toast.makeText(MainActivity.this, "Save failed", Toast.LENGTH_SHORT).show();
                            Logger logger = Logger.getLogger(MainActivity.class.getName());
                            logger.severe(t.getMessage());
                        }
                    }
            );


        });
    }
}