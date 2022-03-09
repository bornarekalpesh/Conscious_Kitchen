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
import com.technothinksup.consciouskitchen.adapter.DeviceAdapter;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.DeviceResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddDeviceFragment extends Fragment {

    private View view;

    private RecyclerView rv_device;

    public static FormEditText et_add_device;

    public static Button button_add_device;

    private DeviceAdapter deviceAdapter;

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
        view= inflater.inflate(R.layout.fragment_add_device, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();

        listener();

        getData();

        return view;
    }

    private void listener() {
        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void init() {
        rv_device=view.findViewById(R.id.rv_device);
        et_add_device=view.findViewById(R.id.et_add_device);
        button_add_device=view.findViewById(R.id.button_add_device);
        imageView_Back=view.findViewById(R.id.imageView_Back);

        button_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<CommonResponse>call=apiInterface.addDevice(HomePageActivity.userId,AddDeviceFragment.et_add_device.getText().toString());

                call.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        if(response.body().getStatus()==1){
                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ((HomePageActivity)getActivity()).loadFragment(new AddDeviceFragment(),true);
                        }


                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void getData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<DeviceResponse> call=apiInterface.getDeviceList(HomePageActivity.userId);

        call.enqueue(new Callback<DeviceResponse>() {
            @Override
            public void onResponse(Call<DeviceResponse> call, Response<DeviceResponse> response) {

                deviceAdapter  = new DeviceAdapter(getActivity(), response.body().getDeviceList());
                rv_device.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_device.setAdapter(deviceAdapter);
                deviceAdapter.notifyDataSetChanged();
                rv_device.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<DeviceResponse> call, Throwable t) {

            }
        });
    }
}