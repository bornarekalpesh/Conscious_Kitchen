package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class SubMenuFragment extends Fragment
{
    public static SubMenuFragment newInstance() {

        return new SubMenuFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_men,container,false);

        RelativeLayout relativeLyout_SubMenuOrderSandwiches = (RelativeLayout)view.findViewById(R.id.relativeLyout_SubMenuOrderSandwiches);
        RelativeLayout relativeLyout_SubMenuOrderSalad = (RelativeLayout)view.findViewById(R.id.relativeLyout_SubMenuOrderSalad);
        RelativeLayout relativeLyout_SubMenuOrderDrinks = (RelativeLayout)view.findViewById(R.id.relativeLyout_SubMenuOrderDrinks);

        ImageView imageView_BackSubMenu = (ImageView) view.findViewById(R.id.imageView_BackSubMenu);
        ImageView imageView_RewardSubMenu = (ImageView) view.findViewById(R.id.imageView_RewardSubMenu);
        ImageView imageView_CartSubMenu = (ImageView) view.findViewById(R.id.imageView_CartSubMenu);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        relativeLyout_SubMenuOrderSandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProductDetailFragment();
            }
        });

        relativeLyout_SubMenuOrderSalad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProductDetailFragment();
            }
        });

        relativeLyout_SubMenuOrderDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProductDetailFragment();
            }
        });



        return view;
    }


    private void loadProductDetailFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ProductDetailFragment.newInstance())
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
