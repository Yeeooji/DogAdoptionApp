package com.example.dogapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dogapp.R;
import com.example.dogapp.UserViews.ViewDog;
import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMyDogsAdapter extends ArrayAdapter<AdoptionInterest> {
    private final Context context;
    private final List<AdoptionInterest> adoptionInterestList;
    DogApi dogApi;

    public UserMyDogsAdapter(Context context, List<AdoptionInterest> adoptionInterestList) {
        super(context, R.layout.user_dog_list, adoptionInterestList);
        this.context = context;
        this.adoptionInterestList = adoptionInterestList;

        RetrofitService retrofitService = new RetrofitService();
        dogApi = retrofitService.getRetrofit().create(DogApi.class);
    }

    @Override
    public int getCount() {
        return adoptionInterestList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_my_dogs_list, parent, false);

        TextView dogName = rowView.findViewById(R.id.dogName);
        TextView dogAdopter = rowView.findViewById(R.id.dogAdopteeName);
        TextView dogAdoptionDate = rowView.findViewById(R.id.dogAdoptionDate);
        TextView dogStatus = rowView.findViewById(R.id.dogStatus);
        TextView dogAdopterEmail = rowView.findViewById(R.id.userEmail);
        ImageView img = rowView.findViewById(R.id.dogImage);



        AdoptionInterest adoptionInterest = adoptionInterestList.get(position);
        dogName.setText(adoptionInterest.getDogName());
        dogAdopter.setText("Adoptee name: " + adoptionInterest.getName());
        dogAdoptionDate.setText("Date: " + adoptionInterest.getDateAndTimeCreated());
        dogStatus.setText("Status: " + adoptionInterest.getStatus());
        dogAdopterEmail.setText("Adoptee Email: " + adoptionInterest.getUserEmail());

        dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                List<Dog> dogs = response.body();
                for (Dog dog: dogs){
                    if (String.valueOf(dog.getId()).equals(adoptionInterest.getId())){
                        System.out.println("Deny");
                        byte[] pictureBytes = Base64.decode(dog.getPicture(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0, pictureBytes.length);
                        img.setImageBitmap(bitmap);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {

            }
        });
        return rowView;
    }
}
