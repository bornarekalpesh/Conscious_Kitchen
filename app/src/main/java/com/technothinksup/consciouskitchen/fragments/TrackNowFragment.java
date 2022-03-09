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
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

public class TrackNowFragment extends Fragment {
    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    public static TrackNowFragment newInstance() {

        return new TrackNowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracknow, container, false);

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getActivity(), HomePageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("logintype","o");

                        ((HomePageActivity)getActivity()).finish();
                        startActivity(i);


                    }
                }, 2000);


        CardView cardView_TrackNow = (CardView) view.findViewById(R.id.cardView_TrackNow);
        CardView cardView_TrackNowReferFriend = (CardView) view.findViewById(R.id.cardView_TrackNowReferFriend);

        ImageView imageView_BackTrackNow = (ImageView) view.findViewById(R.id.imageView_BackTrackNow);
        ImageView imageView_RewardTrackNow = (ImageView) view.findViewById(R.id.imageView_RewardTrackNow);
        //ImageView imageView_CartTrackNow = (ImageView) view.findViewById(R.id.imageView_CartTrackNow);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardTrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

//        imageView_CartTrackNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadReviewOrderFragment();
//            }
//        });


        cardView_TrackNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTrackerFragment();
            }
        });

        cardView_TrackNowReferFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReferFriendFragment();
            }
        });

        return view;
    }

    private void loadTrackerFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TrackerFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadReferFriendFragment() {

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
