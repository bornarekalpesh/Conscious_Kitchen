package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.TasteAdapter;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.TasteResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddTasteFragment extends Fragment {

    private View view;
    public static FormEditText et_add_taste;
    public static Button button_add_taste;
    private RecyclerView rv_taste;

    private TasteAdapter tasteAdapter;
    private ImageView imageView_Back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_taste, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();

        listener();

        getData();
        return view;
    }



    private void init() {
        rv_taste = view.findViewById(R.id.rv_taste);
        et_add_taste = view.findViewById(R.id.et_add_taste);
        button_add_taste = view.findViewById(R.id.button_add_taste);
        imageView_Back = view.findViewById(R.id.imageView_Back);
    }

    private void listener() {
        button_add_taste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<CommonResponse>call=apiInterface.addTaste(HomePageActivity.userId,AddTasteFragment.et_add_taste.getText().toString());

                call.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        if(response.body().getStatus()==1){

                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ((HomePageActivity)getActivity()).loadFragment(new AddTasteFragment(),true);

                        }



                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });

            }
        });

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void getData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<TasteResponse>call=apiInterface.getTasteList(HomePageActivity.userId);

        call.enqueue(new Callback<TasteResponse>() {
            @Override
            public void onResponse(Call<TasteResponse> call, Response<TasteResponse> response) {

                tasteAdapter  = new TasteAdapter(getActivity(), response.body().getTasteList());
                rv_taste.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_taste.setAdapter(tasteAdapter);
                tasteAdapter.notifyDataSetChanged();
                rv_taste.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<TasteResponse> call, Throwable t) {

            }
        });
    }


}