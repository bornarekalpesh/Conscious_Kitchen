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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class



CurbsideTrackNowFragment extends Fragment {
    public static CurbsideTrackNowFragment newInstance() {

        return new CurbsideTrackNowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curbside_track_now, container, false);

        CardView cardView_CurbsideTrackNow = (CardView)view.findViewById(R.id.cardView_CurbsideTrackNow);
        CardView cardView_CurbsideTrackNowReferFriend = (CardView)view.findViewById(R.id.cardView_CurbsideTrackNowReferFriend);

        ImageView imageView_CurbsideBackTrackNow = (ImageView) view.findViewById(R.id.imageView_CurbsideBackTrackNow);
        ImageView imageView_CurbsideRewardTrackNow = (ImageView) view.findViewById(R.id.imageView_CurbsideRewardTrackNow);
        ImageView imageView_CurbsideCartTrackNow = (ImageView) view.findViewById(R.id.imageView_CurbsideCartTrackNow);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_CurbsideBackTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_CurbsideRewardTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CurbsideCartTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        cardView_CurbsideTrackNowReferFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReferFriendFragment();
            }
        });

        cardView_CurbsideTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomeFragment();
            }
        });


        return view;

    }

    private void loadHomeFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, HomeFragment.newInstance())
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
