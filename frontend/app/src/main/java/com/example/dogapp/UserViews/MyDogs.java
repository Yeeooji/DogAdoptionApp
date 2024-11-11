package com.example.dogapp.UserViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.se.omapi.Session;
import android.widget.ListView;

import com.example.dogapp.R;
import com.example.dogapp.adapters.UserDogAdapter;
import com.example.dogapp.adapters.UserMyDogsAdapter;
import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.retrofit.AdoptionApi;
import com.example.dogapp.retrofit.RetrofitService;
import com.example.dogapp.session.SessionManager;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MyDogs extends AppCompatActivity {
    private UserMyDogsAdapter dogAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dogs);

        RetrofitService retrofitService = new RetrofitService();
        ListView listView = findViewById(R.id.myDogsList);
        SessionManager sm = new SessionManager(this);
        String username = sm.getUserName();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);
        dogAdapter = new UserMyDogsAdapter(MyDogs.this, new ArrayList<>());
        listView.setAdapter(dogAdapter);

        Call<List<AdoptionInterest>> call = adoptionApi.getAdoptionInterestByUsername(username);
        call.enqueue(new retrofit2.Callback<List<AdoptionInterest>>() {
            @Override
            public void onResponse(@NonNull Call<List<AdoptionInterest>> call, @NonNull retrofit2.Response<List<AdoptionInterest>> response) {
                if (response.isSuccessful()) {
                    List<AdoptionInterest> adoptionInterestList = response.body();
                    dogAdapter = new UserMyDogsAdapter(MyDogs.this, adoptionInterestList);
                    listView.setAdapter(dogAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AdoptionInterest>> call, @NonNull Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}