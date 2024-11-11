package com.example.dogapp.UserViews;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.widget.ImageView;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dogapp.R;
import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.model.User;
import com.example.dogapp.retrofit.AdoptionApi;
import com.example.dogapp.retrofit.RetrofitService;
import com.example.dogapp.session.SessionManager;
import com.example.dogapp.retrofit.UserApi;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import retrofit2.Callback;

public class ViewDog extends AppCompatActivity {
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dog);
        Intent intent = getIntent();

        String dogId = intent.getStringExtra("dogId");
        String dogName = intent.getStringExtra("dogName");
        String dogAge = intent.getStringExtra("dogAge");
        String dogBreed = intent.getStringExtra("dogBreed");
        String dogWeight = intent.getStringExtra("dogWeight");
        String dogSex = intent.getStringExtra("dogSex");
        String dogVaccinated = intent.getStringExtra("dogVaccinated");
        String imagePath = intent.getStringExtra("dogImage");



        TextView name = findViewById(R.id.viewDogName);
        TextView age = findViewById(R.id.viewDogAge);
        TextView breed = findViewById(R.id.viewDogBreed);
        TextView weight = findViewById(R.id.viewDogWeight);
        TextView sex = findViewById(R.id.viewDogSex);
        TextView vaccinated = findViewById(R.id.viewDogVaccine);
        ImageView image = findViewById(R.id.viewDogImage);

        name.setText(dogName);
        age.setText(dogAge);
        breed.setText(dogBreed);
        weight.setText(dogWeight);
        sex.setText(dogSex);
        vaccinated.setText(dogVaccinated);

        byte[] decodedString = Base64.decode(imagePath, Base64.DEFAULT);
        Glide.with(this).asBitmap().load(decodedString).into(image);

        RetrofitService retrofitService = new RetrofitService();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        // get user from session
        SessionManager sessionManager = new SessionManager(this);
        String username = sessionManager.getUserName();


        userApi.getUser(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<User> call, @NonNull retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    System.out.println("User: " + user);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<User> call, @NonNull Throwable t) {
                System.out.println("Failed to get user");
            }
        });

//        Button adoptDogBtn = findViewById(R.id.AdoptMeBtn);
//        adoptDogBtn.setOnClickListener(v -> {
//            AdoptionInterest adoptionInterest = new AdoptionInterest();
//            adoptionInterest.setName(username);
//            adoptionInterest.setUserEmail(username);
//            adoptionApi.addAdoptionInterest();
//        });

        Button adoptDogBtn = findViewById(R.id.AdoptMeBtn);
        adoptDogBtn.setOnClickListener(v -> {
            userApi.getUser(username).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<User> call, @NonNull retrofit2.Response<User> response) {
                    // based on username, get user from database into user object


                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            AdoptionInterest adoptionInterest = new AdoptionInterest();
                            System.out.println(Long.parseLong(dogId));
                            adoptionInterest.setId(dogId);
                            adoptionInterest.setUserEmail(user.getEmail());
                            adoptionInterest.setName(user.getUsername());
                            adoptionInterest.setDogName(dogName);
                            adoptionInterest.setDateAndTimeCreated("2021-08-01 12:00:00");
                            adoptionInterest.setStatus("Pending");
                            adoptionApi.addAdoptionInterest(adoptionInterest).enqueue(new Callback<AdoptionInterest>() {
                                @Override
                                public void onResponse(@NonNull retrofit2.Call<AdoptionInterest> call, @NonNull retrofit2.Response<AdoptionInterest> response) {
                                    if (response.isSuccessful()) {
                                        System.out.println("Adoption interest added");
                                        Toast.makeText(ViewDog.this, "Adoption interest added", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ViewDog.this, Home.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull retrofit2.Call<AdoptionInterest> call, @NonNull Throwable t) {
                                    System.out.println("Failed to add adoption interest");
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<User> call, @NonNull Throwable t) {
                    System.out.println("Failed to get user");
                }
            });

        });
    }


}

