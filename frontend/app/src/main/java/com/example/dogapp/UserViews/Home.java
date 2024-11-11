package com.example.dogapp.UserViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogapp.MainActivity;
import com.example.dogapp.R;
import com.example.dogapp.adapters.UserDogAdapter;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;
import com.example.dogapp.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    private ListView listView;
    private UserDogAdapter dogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeComponents();
    }

    private void initializeComponents() {
        TextView usernameTextView = findViewById(R.id.username);
        Button homeLogoutButton = findViewById(R.id.homeLogoutButton);
        listView = findViewById(R.id.dogListView); // Make sure to use the correct ID for your ListView in the layout

        // get username from session
        SessionManager sm = new SessionManager(this);
        String username = sm.getUserName();

        // set username
        usernameTextView.setText(username);

        // Retrofit service
        RetrofitService retrofitService = new RetrofitService();
        ListView listView = findViewById(R.id.dogListView);
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);
        dogAdapter = new UserDogAdapter(Home.this, new ArrayList<>());
        listView.setAdapter(dogAdapter);
        Call<List<Dog>> call = dogApi.getAllDogs();
        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dog>> call, @NonNull Response<List<Dog>> response) {
                List<Dog> dogList = new ArrayList<>();
                if (response.isSuccessful()) {
                    List<Dog> dogs = response.body();
                    for (Dog dog : dogs) {
                        dogList.add(dog);
                    }
                    dogAdapter.addAll(dogList);
                    dogAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Home.this, "Failed to get dogs", Toast.LENGTH_SHORT).show();
                    System.out.println("Dog list:" + response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Dog>> call, @NonNull Throwable t) {
                Toast.makeText(Home.this, "Failed to get dogs", Toast.LENGTH_SHORT).show();
                System.out.println("Dog list:" + t.getMessage());
            }
        });

        // logout button
        homeLogoutButton.setOnClickListener(v -> {
            sm.clearSession();
            Toast.makeText(Home.this, "Logged out", Toast.LENGTH_SHORT).show();
            // redirect to login page
            Intent intent = new Intent(Home.this, MainActivity.class);
            finishActivity(0);
            startActivity(intent);
        });

        // my dogs button
        Button myDogsButton = findViewById(R.id.myDogsBtn);
        myDogsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, MyDogs.class);
            startActivity(intent);
        });
    }

}
