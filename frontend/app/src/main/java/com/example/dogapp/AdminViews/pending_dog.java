package com.example.dogapp.AdminViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dogapp.R;
import com.example.dogapp.model.AdoptionInterest;
import com.example.dogapp.model.Dog;
import com.example.dogapp.retrofit.AdoptionApi;
import com.example.dogapp.retrofit.DogApi;
import com.example.dogapp.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pending_dog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pending_dog extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pending_dog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pending_dog.
     */
    // TODO: Rename and change types and number of parameters
    public static pending_dog newInstance(String param1, String param2) {
        pending_dog fragment = new pending_dog();
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
    ListView dogList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_dog, container, false);

        RetrofitService retrofitService = new RetrofitService();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);

        dogList = view.findViewById(R.id.dogListPending);

        adoptionApi.getAllAdoptionInterest().enqueue(new Callback<List<AdoptionInterest>>() {
            @Override
            public void onResponse(Call<List<AdoptionInterest>> call, Response<List<AdoptionInterest>> response) {
                List<AdoptionInterest> adopts = response.body();
                PendingAdapter pendingAdopter = new PendingAdapter(getContext(), adopts);
                dogList.setAdapter(pendingAdopter);
            }

            @Override
            public void onFailure(Call<List<AdoptionInterest>> call, Throwable t) {

            }
        });

        return view;
    }
}