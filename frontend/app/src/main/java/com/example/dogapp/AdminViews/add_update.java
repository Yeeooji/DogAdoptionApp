package com.example.dogapp.AdminViews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogapp.R;
import com.example.dogapp.UserViews.Home;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_update#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_update extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Dog dog = new Dog();

    RetrofitService retrofitService = new RetrofitService();
    DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);
    public add_update() {
        // Required empty public constructor
    }

    public static add_update newInstance(String param1, String param2) {
        add_update fragment = new add_update();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    TextView addOrUpdateBtn;
    EditText dogName, dogBreed, dogAge,dogBirthdate,dogGender, dogWeight, dogHeight, dogVac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_update, container, false);

        dogName = view.findViewById(R.id.dogName);
        dogBreed = view.findViewById(R.id.dogBreed);
        dogAge = view.findViewById(R.id.dogAge);
        dogBirthdate = view.findViewById(R.id.dogBirthdate);
        dogGender = view.findViewById(R.id.dogGender);
        dogWeight = view.findViewById(R.id.dogWeight);
        dogHeight = view.findViewById(R.id.dogHeight);
        dogVac = view.findViewById(R.id.dogVac);
        addOrUpdateBtn = view.findViewById(R.id.addBtn);
        addOrUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dogName.getText().toString().trim().isEmpty() ||
                    dogBreed.getText().toString().trim().isEmpty() ||
                    dogAge.getText().toString().trim().isEmpty() ||
                    dogBirthdate.getText().toString().trim().isEmpty() ||
                    dogGender.getText().toString().trim().isEmpty() ||
                    dogWeight.getText().toString().trim().isEmpty() ||
                    dogHeight.getText().toString().trim().isEmpty() ||
                    dogVac.getText().toString().trim().isEmpty()) {

                    Toast.makeText(getContext(), "Fill out all the information", Toast.LENGTH_SHORT).show();

                } else {
                    imageChooser();

//
                }
            }
        });

        return view;
    }
    int SELECT_PICTURE = 200;
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            dog.setName(dogName.getText().toString());
            dog.setBreed(dogBreed.getText().toString());
            dog.setAge(dogAge.getText().toString());
            dog.setDateOfBirth(dogBirthdate.getText().toString());
            dog.setGender(dogGender.getText().toString());
            dog.setHeight(dogHeight.getText().toString());
            dog.setWeight(dogWeight.getText().toString());
            dog.setMedicalConditions(dogVac.getText().toString());

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = new File(data.getData().getPath());

            System.out.println("DATAA: " + path + "/" + image.getName().toString());

//            DocumentFile documentFile = DocumentFile.fromSingleUri(getActivity(), data.getData());



            File file = new File(path+ "/" + image.getName().toString());
            RequestBody requestFile =
                    RequestBody.create(MultipartBody.FORM, file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("dogImage", file.getName(), requestFile);

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), dog.getName().toString());
            RequestBody age = RequestBody.create(MediaType.parse("text/plain"), dog.getAge().toString());
            RequestBody dateOfBirth = RequestBody.create(MediaType.parse("text/plain"), dog.getDateOfBirth().toString());
            RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), dog.getGender().toString());
            RequestBody breed = RequestBody.create(MediaType.parse("text/plain"), dog.getBreed().toString());
            RequestBody height = RequestBody.create(MediaType.parse("text/plain"), dog.getHeight().toString());
            RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), dog.getWeight().toString());
            RequestBody medicalConditions = RequestBody.create(MediaType.parse("text/plain"), dog.getMedicalConditions().toString());

            dogApi.addDog(body,
                    name,
                    age,
                    dateOfBirth,
                    gender,
                    breed,
                    height,
                    weight,
                    medicalConditions).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    System.out.println("successs: " + response);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new dog_list())
                            .commit();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("failedd: " + t);

                }
            });
        }
    }
}