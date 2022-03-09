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

public class CurbsideGiftCardFragment extends Fragment
{

    public static CurbsideGiftCardFragment newInstance() {

        return new CurbsideGiftCardFragment();
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curbside_giftcard, container, false);

        RelativeLayout relativeLayout_CurbsideGiftHoldToPay = (RelativeLayout) view.findViewById(R.id.relativeLayout_CurbsideGiftHoldToPay);
        TextView textView_CurbsideGiftAddNewCard = (TextView) view.findViewById(R.id.textView_CurbsideGiftAddNewCard);
        TextView textView_CurbsidePaymentOptionGiftEdit = (TextView) view.findViewById(R.id.textView_CurbsidePaymentOptionGiftEdit);

        ImageView imageView_BackCurbsideGiftCard = (ImageView) view.findViewById(R.id.imageView_BackCurbsideGiftCard);
        ImageView imageView_RewardCurbsideGiftCard = (ImageView) view.findViewById(R.id.imageView_RewardCurbsideGiftCard);
        ImageView imageView_CartCurbsideGiftCard = (ImageView) view.findViewById(R.id.imageView_CartCurbsideGiftCard);


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackCurbsideGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardCurbsideGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartCurbsideGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        relativeLayout_CurbsideGiftHoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsideTrackNowFragment();
            }
        });

        textView_CurbsideGiftAddNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddGiftCard();
            }
        });

        textView_CurbsidePaymentOptionGiftEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsideInstructionFragment();
            }
        });


        return view;
    }


    private void loadCurbsideTrackNowFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideTrackNowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsideInstructionFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideInstructionFragment.newInstance())
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


    private void popupAddGiftCard()
    {
        View popUpViewAddGiftCard= getLayoutInflater().inflate(R.layout.popup_add_giftcard, null); // inflating popup layout
        final PopupWindow addGiftCardPopup = new PopupWindow(popUpViewAddGiftCard, Toolbar.LayoutParams.FILL_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        addGiftCardPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        addGiftCardPopup.showAtLocation(popUpViewAddGiftCard, Gravity.CENTER, 0, 0);

        ImageView imageView_BackGiftCard = popUpViewAddGiftCard.findViewById(R.id.imageView_BackGiftCard);
        TextView textView_GiftCardScan = popUpViewAddGiftCard.findViewById(R.id.textView_GiftCardScan);
        TextView textView_GiftCardUse = popUpViewAddGiftCard.findViewById(R.id.textView_GiftCardUse);

        imageView_BackGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGiftCardPopup.dismiss();
            }
        });

        textView_GiftCardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGiftCardPopup.dismiss();
            }
        });

        textView_GiftCardUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGiftCardPopup.dismiss();
            }
        });
    }




}
