package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class OrderDeliveryFragment extends Fragment
{
    public static OrderDeliveryFragment newInstance()
    {
        return new OrderDeliveryFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_delivery,container,false);
        ImageView imageView_BackOrderDelivery = (ImageView) view.findViewById(R.id.imageView_BackOrderDelivery);
        ImageView imageView_RewardOrderDelivery = (ImageView) view.findViewById(R.id.imageView_RewardOrderDelivery);
        ImageView imageView_CartOrderDelivery = (ImageView) view.findViewById(R.id.imageView_CartOrderDelivery);

        ImageView imageView_DeliveryDoor = (ImageView) view.findViewById(R.id.imageView_DeliveryDoor);
        ImageView imageView_DeliveryUber = (ImageView) view.findViewById(R.id.imageView_DeliveryUber);
        ImageView imageView_DeliveryGrub = (ImageView) view.findViewById(R.id.imageView_DeliveryGrub);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackOrderDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardOrderDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartOrderDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        imageView_DeliveryDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryTrackNowFragment();
            }
        });

        imageView_DeliveryUber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryTrackNowFragment();
            }
        });

        imageView_DeliveryGrub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryTrackNowFragment();
            }
        });

        return view;
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

    private void loadDeliveryTrackNowFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryTrackNowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


}
