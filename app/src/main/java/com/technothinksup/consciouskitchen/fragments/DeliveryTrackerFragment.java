package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class DeliveryTrackerFragment extends Fragment {

    public static DeliveryTrackerFragment newInstance() {

        return new DeliveryTrackerFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_tracker, container, false);
        ImageView imageView_BackDeliveryTracker = (ImageView) view.findViewById(R.id.imageView_BackDeliveryTracker);
        ImageView imageView_RewardDeliveryTracker = (ImageView) view.findViewById(R.id.imageView_RewardDeliveryTracker);
        ImageView imageView_CartDeliveryTracker = (ImageView) view.findViewById(R.id.imageView_CartDeliveryTracker);
        LinearLayout linearLayout_TrackDelivery = (LinearLayout) view.findViewById(R.id.linearLayout_TrackDelivery);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackDeliveryTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardDeliveryTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartDeliveryTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        linearLayout_TrackDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTrackDeliveryFragment();
            }
        });


        return view;
    }

    private void loadTrackDeliveryFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TrackDeliveryFragment.newInstance())
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
