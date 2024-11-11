package com.example.dogapp.AdminViews;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.dogapp.R;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogItemAdapter extends BaseAdapter {


    private Context context;
    private List<Dog> dogs;

    public DogItemAdapter(Context context, List<Dog> dogs) {
        this.context = context;
        this.dogs = dogs;
    }

    @Override
    public int getCount() {
        return dogs.size();
    }

    @Override
    public Object getItem(int i) {
        return dogs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private TextView dogName, dogAge;
    private Button editBtn, deleteBtn;
    private ImageView img;

    public void updateData(List<Dog> newDogs) {
        this.dogs = newDogs;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_dog_item, viewGroup, false);
        }

        RetrofitService retrofitService = new RetrofitService();
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);

        img = view.findViewById(R.id.dogImg);
        dogName = view.findViewById(R.id.dogName);
        dogAge = view.findViewById(R.id.dogAge);
//        editBtn = view.findViewById(R.id.editBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        Dog dog = dogs.get(i);


        // Decode Base64 string to byte array
        byte[] pictureBytes = Base64.decode(dog.getPicture(), Base64.DEFAULT);

        // Convert byte array to Bitmap and set it to the ImageView
        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0, pictureBytes.length);
        img.setImageBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        dogName.setText(dog.getName());
        dogAge.setText(dog.getAge() + " Years Old");

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogApi.deleteDogById(dog.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        List<Dog> updatedDogs = new ArrayList<>(dogs);
                        updatedDogs.remove(dog);
                        updateData(updatedDogs);
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("failed");
                    }
                });

            }
        });

        return view;
    }

}
