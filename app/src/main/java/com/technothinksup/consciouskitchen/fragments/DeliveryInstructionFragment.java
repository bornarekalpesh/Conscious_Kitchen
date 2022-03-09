package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.DeliveryInstructionResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryInstructionFragment extends Fragment {

    private View view;
    private FormEditText et_add_instruction;

    private Button button_add_instruction;

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
        view= inflater.inflate(R.layout.fragment_delivery_instruction, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();
        getData();
        listener();

        return view;
    }

    private void getData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<DeliveryInstructionResponse> call=apiInterface.getDeliveryInstruction(HomePageActivity.userId);

        call.enqueue(new Callback<DeliveryInstructionResponse>() {
            @Override
            public void onResponse(Call<DeliveryInstructionResponse> call, Response<DeliveryInstructionResponse> response) {
               // Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();

              /*  pickUpInstructionAdapter = new PickUpInstructionAdapter(getActivity(), response.body().getPickupInstruction());
                rv_instruction.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_instruction.setAdapter(pickUpInstructionAdapter);
                pickUpInstructionAdapter.notifyDataSetChanged();
                rv_instruction.setHasFixedSize(true);*/

                et_add_instruction.setText(response.body().getDeliveryInstruction().get(0).getCustomerDeliveryIns());

                et_add_instruction.setSelection(response.body().getDeliveryInstruction().get(0).getCustomerDeliveryIns().length());

                button_add_instruction.setText("Update");
            }

            @Override
            public void onFailure(Call<DeliveryInstructionResponse> call, Throwable t) {

            }
        });

    }

    private void listener() {

        button_add_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_add_instruction.testValidity()){

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse>call=apiInterface.addDeliveryInstruction(HomePageActivity.userId,et_add_instruction.getText().toString());

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Intent i = new Intent(getActivity(),HomePageActivity.class);
                            startActivity(i);

                            Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });
                }
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

    private void init() {
        et_add_instruction=view.findViewById(R.id.et_add_instruction);
        button_add_instruction=view.findViewById(R.id.button_add_instruction);
        imageView_Back=view.findViewById(R.id.imageView_Back);
    }
}