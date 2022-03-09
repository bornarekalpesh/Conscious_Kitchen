package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class DeliveryTrackNowFragment extends Fragment {
    public static DeliveryTrackNowFragment newInstance() {

        return new DeliveryTrackNowFragment();
    }


    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_track_now, container, false);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), HomePageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(i);

                        ((HomePageActivity)getActivity()).finishAndRemoveTask();
                    }
                }, 2000);

        CardView cardView_DeliveryTrackNow = (CardView)view.findViewById(R.id.cardView_DeliveryTrackNow);
        CardView cardView_DeliveryTrackNowReferFriend = (CardView)view.findViewById(R.id.cardView_DeliveryTrackNowReferFriend);

        ImageView imageView_DeliveryBackTrackNow = (ImageView) view.findViewById(R.id.imageView_DeliveryBackTrackNow);
        ImageView imageView_DeliveryRewardTrackNow = (ImageView) view.findViewById(R.id.imageView_DeliveryRewardTrackNow);
        ImageView imageView_DeliveryCartTrackNow = (ImageView) view.findViewById(R.id.imageView_DeliveryCartTrackNow);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_DeliveryBackTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_DeliveryRewardTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_DeliveryCartTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        cardView_DeliveryTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryTrackerFragment();
            }
        });

        cardView_DeliveryTrackNowReferFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReferFriendFragment();
            }
        });

        return view;
    }


    private void loadDeliveryTrackerFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryTrackerFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    private void loadReferFriendFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReferFriendFragment.newInstance())
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
