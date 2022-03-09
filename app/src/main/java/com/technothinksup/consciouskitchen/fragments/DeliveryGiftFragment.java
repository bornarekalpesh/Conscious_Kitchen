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

public class DeliveryGiftFragment extends Fragment {
    public static DeliveryGiftFragment newInstance() {

        return new DeliveryGiftFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery_gift, container, false);

        ImageView imageView_BackDeliveryGift = (ImageView) view.findViewById(R.id.imageView_BackDeliveryGift);
        ImageView imageView_RewardDeliveryGift = (ImageView) view.findViewById(R.id.imageView_RewardDeliveryGift);
        ImageView imageView_CartDeliveryGift = (ImageView) view.findViewById(R.id.imageView_CartDeliveryGift);
        TextView textView_DeliveryGiftEditInformation = (TextView) view.findViewById(R.id.textView_DeliveryGiftEditInformation);
        final ImageView imageView_DeliveryGiftInformation = (ImageView) view.findViewById(R.id.imageView_DeliveryGiftInformation);
        TextView textView_DeliveryGiftAddNewCard = (TextView) view.findViewById(R.id.textView_DeliveryGiftAddNewCard);
        RelativeLayout relativeLayout_DeliveryGiftHoldToPay = (RelativeLayout) view.findViewById(R.id.relativeLayout_DeliveryGiftHoldToPay);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        textView_DeliveryGiftEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageView_DeliveryGiftInformation.getVisibility() == View.GONE)
                {
                    imageView_DeliveryGiftInformation.setVisibility(View.VISIBLE);
                }
                else
                {
                    imageView_DeliveryGiftInformation.setVisibility(View.GONE);
                }

            }
        });

        relativeLayout_DeliveryGiftHoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderDeliveryFragment();
            }
        });

        textView_DeliveryGiftAddNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddGiftCard();
            }
        });

        imageView_BackDeliveryGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardDeliveryGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartDeliveryGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
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

    private void loadOrderDeliveryFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, OrderDeliveryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

}
