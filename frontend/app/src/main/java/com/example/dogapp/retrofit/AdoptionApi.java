package com.example.dogapp.retrofit;

import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.model.Dog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdoptionApi {
    @POST("/adoption/create")
    Call<AdoptionInterest> addAdoptionInterest(@Body AdoptionInterest adoptionInterest);

    @GET("/adoption/user/{username}")
    Call<List<AdoptionInterest>> getAdoptionInterestByUsername(@Path("username") String username);
  
    @GET("/adoption/list")
    Call<List<AdoptionInterest>> getAllAdoptionInterest();

    @DELETE("/adoption/delete/{id}")
    Call<Void> deleteAdoptionInterest(@Path("id") String id);

    @PUT("/adoption/update/{id}")
    Call<Void> updateAdoptionInterest( @Body AdoptionInterest adoptionInterest, @Path("id") String id);

}
