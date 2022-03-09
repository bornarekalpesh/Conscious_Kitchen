package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.PossibleRewardAdapter;
import com.technothinksup.consciouskitchen.model.GetPossibleRewardResponse;
import com.technothinksup.consciouskitchen.model.ItemDetailResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRewardFragment extends Fragment {

    private RecyclerView rv_rewardList;

    private TextView tv_reward_point;

    private View view;

    private PossibleRewardAdapter possibleRewardAdapter;

    public static MyRewardFragment newInstance() {

        return new MyRewardFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myrewards, container, false);

        init();


           setRewardData();

        TextView textView_RewardScanToRedeem = view.findViewById(R.id.textView_RewardScanToRedeem);
        TextView textView_RewardScanToRedeemTwo = view.findViewById(R.id.textView_RewardScanToRedeemTwo);
        TextView textView_RewardAddToCart = view.findViewById(R.id.textView_RewardAddToCart);
        TextView textView_RewardAddToCartTwo = view.findViewById(R.id.textView_RewardAddToCartTwo);

        CardView cardView_Reward = view.findViewById(R.id.cardView_Reward);

        ImageView imageView_BackMyReward = (ImageView) view.findViewById(R.id.imageView_BackMyReward);
        ImageView imageView_RewardMyReward = (ImageView) view.findViewById(R.id.imageView_RewardMyReward);
        ImageView imageView_CartMyReward = (ImageView) view.findViewById(R.id.imageView_CartMyReward);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackMyReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardMyReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartMyReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        textView_RewardAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        textView_RewardAddToCartTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        cardView_Reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        textView_RewardScanToRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardScanRestaurantFragment();
            }
        });

        textView_RewardScanToRedeemTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardScanRestaurantFragment();
            }
        });

        return view;
    }

    private void init() {

        rv_rewardList = view.findViewById(R.id.rv_rewardList);
        tv_reward_point = view.findViewById(R.id.tv_reward_point);

        tv_reward_point.setText("" + Math.round(HomePageActivity.reward_point) + " Rewards");
    }



    private void setRewardData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetPossibleRewardResponse> call = apiInterface.possibleRewardList(HomePageActivity.userId);
        call.enqueue(new Callback<GetPossibleRewardResponse>() {
            @Override
            public void onResponse(Call<GetPossibleRewardResponse> call, Response<GetPossibleRewardResponse> response) {
                if (response.body().getStatus() == 1) {



                    possibleRewardAdapter = new PossibleRewardAdapter(getActivity(), response.body().getPossibleRewardList());
                    rv_rewardList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_rewardList.setAdapter(possibleRewardAdapter);
                    possibleRewardAdapter.notifyDataSetChanged();
                    rv_rewardList.setHasFixedSize(true);


                } else {

                }
            }

            @Override
            public void onFailure(Call<GetPossibleRewardResponse> call, Throwable t) {

            }
        });
    }

    private void loadReviewOrderFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    private void loadRewardScanRestaurantFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardScanRestaurantFragment.newInstance())
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
