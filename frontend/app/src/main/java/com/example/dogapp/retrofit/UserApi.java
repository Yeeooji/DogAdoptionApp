package com.example.dogapp.retrofit;

import com.example.dogapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @GET("/")
    Call<List<User>> getAllUser();

    @POST("/add-user")
    Call<User> save(@Body User user);
}
