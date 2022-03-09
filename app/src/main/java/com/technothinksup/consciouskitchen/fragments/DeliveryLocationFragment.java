package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CustomerAddressAdapter;
import com.technothinksup.consciouskitchen.model.CustomerAddressResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryLocationFragment extends Fragment {

    private View view;
    private RecyclerView rv_customer_address;
    private TextView textView_DeliveryEnterNewAddress, textView_DeliveryUseLocation;
    private ImageView imageView_BackDeliveryLocation, imageView_RewardDeliveryLocation, imageView_CartDeliveryLocation;
    private CustomerAddressAdapter customerAddressAdapter;

    public static DeliveryLocationFragment newInstance() {
        return new DeliveryLocationFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delivery_location, container, false);
        init();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CustomerAddressResponse> call = apiInterface.getCustomerAddressList(HomePageActivity.userId);
        call.enqueue(new Callback<CustomerAddressResponse>() {
            @Override
            public void onResponse(Call<CustomerAddressResponse> call, Response<CustomerAddressResponse> response) {
                customerAddressAdapter = new CustomerAddressAdapter(getActivity(), response.body().getCustAddressList());
                rv_customer_address.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_customer_address.setAdapter(customerAddressAdapter);
                customerAddressAdapter.notifyDataSetChanged();
                rv_customer_address.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<CustomerAddressResponse> call, Throwable t) {
            }
        });
    }

    private void init() {
        textView_DeliveryEnterNewAddress = view.findViewById(R.id.textView_DeliveryEnterNewAddress);
        textView_DeliveryUseLocation = view.findViewById(R.id.textView_DeliveryUseLocation);
        imageView_BackDeliveryLocation = view.findViewById(R.id.imageView_BackDeliveryLocation);
        imageView_RewardDeliveryLocation = view.findViewById(R.id.imageView_RewardDeliveryLocation);
        imageView_CartDeliveryLocation = view.findViewById(R.id.imageView_CartDeliveryLocation);
        rv_customer_address = view.findViewById(R.id.rv_customer_address);
    }

    private void listener() {
        imageView_BackDeliveryLocation.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardDeliveryLocation.setOnClickListener(view -> loadRewardProcessFragment());

        imageView_CartDeliveryLocation.setOnClickListener(view -> loadReviewOrderFragment());

        textView_DeliveryEnterNewAddress.setOnClickListener(view -> loadDeliveryAddressFragment());

        textView_DeliveryUseLocation.setOnClickListener(view -> loadMenuShowFragment());
    }

    private void loadDeliveryAddressFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryAddressFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadMenuShowFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    private void loadReviewOrderFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardProcessFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

}
