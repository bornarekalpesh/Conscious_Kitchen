package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class DeliveryWalletFragment extends Fragment
{
    public static DeliveryWalletFragment newInstance() {

        return new DeliveryWalletFragment();
    }


    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery_wallet, container, false);

        TextView textView_DeliveryWalletEditInformation = (TextView)view.findViewById(R.id.textView_DeliveryWalletEditInformation);
        final ImageView imageView_DeliveryWalletInformation = (ImageView) view.findViewById(R.id.imageView_DeliveryWalletInformation);

        ImageView imageView_BackDeliveryWallet = (ImageView) view.findViewById(R.id.imageView_BackDeliveryWallet);
        ImageView imageView_RewardDeliveryWallet = (ImageView) view.findViewById(R.id.imageView_RewardDeliveryWallet);
        ImageView imageView_CartDeliveryWallet = (ImageView) view.findViewById(R.id.imageView_CartDeliveryWallet);

        TextView textView_DeliveryAddNewCardWallet = (TextView) view.findViewById(R.id.textView_DeliveryAddNewCardWallet);
        RelativeLayout relativeLayout_DeliveryWalletHoldToPay = (RelativeLayout) view.findViewById(R.id.relativeLayout_DeliveryWalletHoldToPay);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        textView_DeliveryAddNewCardWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddWallet();
            }
        });

        relativeLayout_DeliveryWalletHoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderDeliveryFragment();
            }
        });

        imageView_BackDeliveryWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardDeliveryWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartDeliveryWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });



        textView_DeliveryWalletEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView_DeliveryWalletInformation.getVisibility() == View.GONE)
                {
                    imageView_DeliveryWalletInformation.setVisibility(View.VISIBLE);
                }
                else
                {
                    imageView_DeliveryWalletInformation.setVisibility(View.GONE);
                }
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

    private void loadDeliveryTrackerFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryTrackerFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
    private void popupAddWallet()
    {
        View popUpViewAddWallet= getLayoutInflater().inflate(R.layout.popup_add_wallet, null); // inflating popup layout
        final PopupWindow addWalletPopup = new PopupWindow(popUpViewAddWallet, Toolbar.LayoutParams.FILL_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        addWalletPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        addWalletPopup.showAtLocation(popUpViewAddWallet, Gravity.CENTER, 0, 0);

        ImageView imageView_BackAddWallet = popUpViewAddWallet.findViewById(R.id.imageView_BackAddWallet);
        TextView textVIew_WalletAdd = popUpViewAddWallet.findViewById(R.id.textVIew_WalletAdd);

        textVIew_WalletAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWalletPopup.dismiss();
            }
        });

        imageView_BackAddWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWalletPopup.dismiss();
            }
        });
    }


    private void loadOrderDeliveryFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, OrderDeliveryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }




}
