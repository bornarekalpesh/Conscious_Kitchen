package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryAddressFragment extends Fragment {
    private View view;
    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};
    private FormEditText et_address, et_delivery_zip, et_delivery_customerName, et_delivery_instruction;
    private CardView cardView_DeliveryAddressContinue;
    private ImageView imageView_BackDeliveryAddress, imageView_RewardDeliveryAddress, imageView_CartDeliveryAddress;

    public static DeliveryAddressFragment newInstance() {
        return new DeliveryAddressFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delivery_address, container, false);
        init();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        return view;
    }

    private void listener() {
        imageView_BackDeliveryAddress.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardDeliveryAddress.setOnClickListener(view -> loadRewardProcessFragment());
        imageView_CartDeliveryAddress.setOnClickListener(view -> loadReviewOrderFragment());

        cardView_DeliveryAddressContinue.setOnClickListener(view -> {
            if (et_address.testValidity() && et_delivery_zip.testValidity() && et_delivery_customerName.testValidity() && et_delivery_instruction.testValidity()) {
                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                Call<CommonResponse> call = apiInterface.addCustomerAddress(HomePageActivity.userId, et_delivery_customerName.getText().toString(), et_address.getText().toString(), et_delivery_zip.getText().toString(), et_delivery_instruction.getText().toString());
                call.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            loadMenuShowFragment();
                        }
                    }
                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void init() {
        StateProgressBar stateProgressBar = view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        cardView_DeliveryAddressContinue = view.findViewById(R.id.cardView_DeliveryAddressContinue);
        imageView_BackDeliveryAddress = view.findViewById(R.id.imageView_BackDeliveryAddress);
        imageView_RewardDeliveryAddress = view.findViewById(R.id.imageView_RewardDeliveryAddress);
        imageView_CartDeliveryAddress = view.findViewById(R.id.imageView_CartDeliveryAddress);

        et_address = view.findViewById(R.id.et_address);
        et_delivery_zip = view.findViewById(R.id.et_delivery_zip);
        et_delivery_customerName = view.findViewById(R.id.et_delivery_customerName);
        et_delivery_instruction = view.findViewById(R.id.et_delivery_instruction);
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
