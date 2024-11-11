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
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserDogAdapter extends ArrayAdapter<Dog> {
    private final Context context;
    private final List<Dog> dogList;
    DogApi dogApi;

    public UserDogAdapter(Context context, List<Dog> dogList) {
        super(context, R.layout.user_dog_list, dogList);
        this.context = context;
        this.dogList = dogList;

        RetrofitService retrofitService = new RetrofitService();
        dogApi = retrofitService.getRetrofit().create(DogApi.class);
    }

    @Override
    public int getCount() {
        return dogList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_dog_list, parent, false);

        ImageView dogImage = rowView.findViewById(R.id.dogImage);
        TextView dogName = rowView.findViewById(R.id.dogName);
        TextView dogAge = rowView.findViewById(R.id.dogAge);
        TextView dogBreed = rowView.findViewById(R.id.dogBreed);
        TextView dogGender = rowView.findViewById(R.id.dogSex);

        Dog currentDog = dogList.get(position);

        dogName.setText(currentDog.getName());
        dogGender.setText("Sex: " + currentDog.getGender());
        dogAge.setText("Age: " + currentDog.getAge());
        dogBreed.setText("Breed: " + currentDog.getBreed());

        // Decode Base64 string to byte array
        byte[] pictureBytes = Base64.decode(currentDog.getPicture(), Base64.DEFAULT);

        // Convert byte array to Bitmap and set it to the ImageView
        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0, pictureBytes.length);
        dogImage.setImageBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        Button aboutMeBtn = rowView.findViewById(R.id.aboutMeBtn);
        aboutMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the detailed page for the selected dog
                Intent intent = new Intent(context, ViewDog.class);
//                String imagePath = saveImageLocally(Base64.decode(currentDog.getPicture(), Base64.DEFAULT), currentDog.getPictureName());
                intent.putExtra("dogId", String.valueOf(currentDog.getId()));
                intent.putExtra("dogName", currentDog.getName());
                intent.putExtra("dogAge", currentDog.getAge() + " years");
                intent.putExtra("dogBreed", currentDog.getBreed());
                intent.putExtra("dogWeight", currentDog.getWeight() + " kg");
                intent.putExtra("dogSex", currentDog.getGender());
                intent.putExtra("dogVaccinated", currentDog.getMedicalConditions());
                // pass the base64 string to the next activity
                intent.putExtra("dogImage", currentDog.getPicture());
                context.startActivity(intent);
            }
        });


        return rowView;
    }

    // Save the image locally and return the file path
    private String saveImageLocally(byte[] imageData, String imageName) {
        try {
            // Get the internal storage directory
            File storageDir = context.getFilesDir();

            // Create a File object for the image file
            File imageFile = new File(storageDir, imageName);

            // Write the image data to the file
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(imageData);
            outputStream.close();

            // Return the absolute path of the saved image file
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
