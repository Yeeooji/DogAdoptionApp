package com.example.dogapp.AdminViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dogapp.R;
import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.AdoptionApi;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAdapter extends BaseAdapter {
    private Context context;
    private List<AdoptionInterest> dogsInterest;
    private TextView dogName, nameAdopts;
    private Button acceptBtn, denyBtn;
    private ImageView img;

    public PendingAdapter(Context context, List<AdoptionInterest> dogsInterest) {
        this.context = context;
        this.dogsInterest = dogsInterest;
    }
    @Override
    public int getCount() {
        return dogsInterest.size();
    }

    @Override
    public Object getItem(int i) {
        return dogsInterest.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void updateData(List<AdoptionInterest> newAdopts) {
        this.dogsInterest = newAdopts;
        notifyDataSetChanged();
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_pending_dog_item, viewGroup, false);
        }

        RetrofitService retrofitService = new RetrofitService();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);

        img = view.findViewById(R.id.dogImgPending);
        dogName = view.findViewById(R.id.dogNamePending);
        nameAdopts = view.findViewById(R.id.nameAdopt);
        denyBtn = view.findViewById(R.id.denyBtn);
        acceptBtn = view.findViewById(R.id.acceptBtn);

        AdoptionInterest adopt = dogsInterest.get(i);
        System.out.println("adopt: " + adopt);

        dogName.setText(adopt.getDogName());
        nameAdopts.setText(adopt.getUserEmail());
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);
        dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                List<Dog> dogs = response.body();
                for (Dog dog: dogs){
                    if (String.valueOf(dog.getId()).equals(adopt.getId())){
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

        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);
                adopt.setStatus("Denied");
                adoptionApi.updateAdoptionInterest(adopt, adopt.getDogName().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("Success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Error: "+ t);
                    }
                });
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);
                adopt.setStatus("Accept");
                adoptionApi.updateAdoptionInterest(adopt, adopt.getDogName().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("Success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Error: "+ t);
                    }
                });
            }

        });

        return view;
    }
}
