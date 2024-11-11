package com.example.dogapp.AdminViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dogapp.R;


public class admin_update_dog extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_update_dog() {
        // Required empty public constructor
    }

    public static admin_update_dog newInstance(String param1, String param2) {
        admin_update_dog fragment = new admin_update_dog();
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
    TextView updateBtn;
    EditText dogName, dogBreed, dogAge,dogBirthdate,dogGender, dogWeight, dogHeight, dogVac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_update_dog, container, false);

        dogName = view.findViewById(R.id.dogName);
        dogBreed = view.findViewById(R.id.dogBreed);
        dogAge = view.findViewById(R.id.dogAge);
        dogBirthdate = view.findViewById(R.id.dogBirthdate);
        dogGender = view.findViewById(R.id.dogGender);
        dogWeight = view.findViewById(R.id.dogWeight);
        dogHeight = view.findViewById(R.id.dogHeight);
        dogVac = view.findViewById(R.id.dogVac);
        updateBtn = view.findViewById(R.id.addBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;
    }
}