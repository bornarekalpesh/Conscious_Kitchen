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

public class CurbsideWalletFragment extends Fragment {
    public static CurbsideWalletFragment newInstance() {

        return new CurbsideWalletFragment();
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curbside_wallet, container, false);

        RelativeLayout relativeLayout_CurbsideWalletHoldToPay = (RelativeLayout) view.findViewById(R.id.relativeLayout_CurbsideWalletHoldToPay);
        TextView textView_CurbsideAddNewCardWallet = (TextView) view.findViewById(R.id.textView_CurbsideAddNewCardWallet);
        TextView textView_CurbsideWalletEdit = (TextView) view.findViewById(R.id.textView_CurbsideWalletEdit);

        ImageView imageView_BackCurbsideWallet = (ImageView) view.findViewById(R.id.imageView_BackCurbsideWallet);
        ImageView imageView_RewardCurbsideWallet = (ImageView) view.findViewById(R.id.imageView_RewardCurbsideWallet);
        ImageView imageView_CartCurbsideWallet = (ImageView) view.findViewById(R.id.imageView_CartCurbsideWallet);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackCurbsideWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardCurbsideWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartCurbsideWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        relativeLayout_CurbsideWalletHoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsideTrackNowFragment();
            }
        });

        textView_CurbsideAddNewCardWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddWallet();
            }
        });

        textView_CurbsideWalletEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsideInstructionFragment();
            }
        });


        return view;
    }

    private void loadCurbsideInstructionFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideInstructionFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsideTrackNowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideTrackNowFragment.newInstance())
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

    private void popupAddWallet() {
        View popUpViewAddWallet = getLayoutInflater().inflate(R.layout.popup_add_wallet, null); // inflating popup layout
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

}
