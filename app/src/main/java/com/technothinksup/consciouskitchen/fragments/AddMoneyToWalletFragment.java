package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.WalletBalanceResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMoneyToWalletFragment extends Fragment {

    private View view;

    private TextView tv_wallet_balance;

    private FormEditText et_amount;

    private Button button_add_balance;
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
        view= inflater.inflate(R.layout.fragment_add_money_to_wallet, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();

        getBalanceData();
        listener();

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
        tv_wallet_balance=view.findViewById(R.id.tv_wallet_balance);
        et_amount=view.findViewById(R.id.et_amount);
        button_add_balance=view.findViewById(R.id.button_add_balance);
        imageView_Back=view.findViewById(R.id.imageView_Back);

    }

    private void getBalanceData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<WalletBalanceResponse>call=apiInterface.getWalletBalance(HomePageActivity.userId);

        call.enqueue(new Callback<WalletBalanceResponse>() {
            @Override
            public void onResponse(Call<WalletBalanceResponse> call, Response<WalletBalanceResponse> response) {

                tv_wallet_balance.setText(""+response.body().getWalletBalance());

            }

            @Override
            public void onFailure(Call<WalletBalanceResponse> call, Throwable t) {

            }
        });
    }
}