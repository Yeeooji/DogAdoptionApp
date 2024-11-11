package com.example.dogapp.retrofit;

import com.example.dogapp.model.Dog;

import com.example.dogapp.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DogApi {

    @GET("dogs")
    Call<List<Dog>> getAllDogs();

    @Multipart
    @POST("/dog")
    Call<Void> addDog(
            @Part MultipartBody.Part dogImage,
            @Part("name") RequestBody name,
            @Part("age") RequestBody age,
            @Part("dateOfBirth") RequestBody dateOfBirth,
            @Part("gender") RequestBody gender,
            @Part("breed") RequestBody breed,
            @Part("height") RequestBody height,
            @Part("weight") RequestBody weight,
            @Part("medicalConditions") RequestBody medicalConditions
    );

    @DELETE("dog/{id}")
    Call<Void> deleteDogById(@Path("id") Long id);
}
