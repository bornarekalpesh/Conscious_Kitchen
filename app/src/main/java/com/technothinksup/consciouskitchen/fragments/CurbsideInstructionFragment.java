package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.technothinksup.consciouskitchen.R;

import java.util.Objects;

public class CurbsideInstructionFragment extends Fragment
{
    public static CurbsideInstructionFragment newInstance()
    {
        return new CurbsideInstructionFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_curbside_instructions,container,false);

        CardView cardView_CurbsideContinue = (CardView)view.findViewById(R.id.cardView_CurbsideContinue);
        EditText editText_CurbsideColor = view.findViewById(R.id.editText_CurbsideColor);
        EditText  editText_CurbsideModel = view.findViewById(R.id.editText_CurbsideModel);
        EditText editText_CurbsideMake = view.findViewById(R.id.editText_CurbsideMake);
        ImageView imageView_BackCurbsideInstructions = (ImageView) view.findViewById(R.id.imageView_BackCurbsideInstructions);
        ImageView imageView_RewardCurbsideInstructions = (ImageView) view.findViewById(R.id.imageView_RewardCurbsideInstructions);
        ImageView imageView_CartCurbsideInstructions = (ImageView) view.findViewById(R.id.imageView_CartCurbsideInstructions);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackCurbsideInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardCurbsideInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartCurbsideInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });



        cardView_CurbsideContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"CurbsideInstruction Save Successfully !" ,Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private void loadMenuShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
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
