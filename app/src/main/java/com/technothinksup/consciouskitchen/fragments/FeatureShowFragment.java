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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.MenuFeaturedAdapter;
import com.technothinksup.consciouskitchen.model.FeaturedResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeatureShowFragment extends Fragment {

    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private RecyclerView rv_featured;

    private View view;

    private TextView textView_FeatureMenu, textView_FeatureRecent, textView_FeatureFavorite, textView_FeatureFeature;

    private ImageView imageView_BackFeature, imageView_RewardFeature, imageView_CartFeature;

    private MenuFeaturedAdapter menuFeaturedAdapter;


    public static FeatureShowFragment newInstance() {

        return new FeatureShowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feature_show, container, false);


        init();

        listener();

        setFeaturedData();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();


        return view;
    }

    private void setFeaturedData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<FeaturedResponse> call = apiInterface.getFeatureItemList("2");

        call.enqueue(new Callback<FeaturedResponse>() {
            @Override
            public void onResponse(Call<FeaturedResponse> call, Response<FeaturedResponse> response) {

                menuFeaturedAdapter = new MenuFeaturedAdapter(getActivity(), response.body().getItemList(), response.body().getItemImagePath());
                rv_featured.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_featured.setAdapter(menuFeaturedAdapter);
                menuFeaturedAdapter.notifyDataSetChanged();
                rv_featured.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<FeaturedResponse> call, Throwable t) {

            }
        });


    }

    private void init() {

        rv_featured = view.findViewById(R.id.rv_featured);

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);



        textView_FeatureMenu = view.findViewById(R.id.textView_FeatureMenu);
        textView_FeatureRecent = view.findViewById(R.id.textView_FeatureRecent);
        textView_FeatureFavorite = view.findViewById(R.id.textView_FeatureFavorite);
        textView_FeatureFeature = view.findViewById(R.id.textView_FeatureFeature);

        imageView_BackFeature = (ImageView) view.findViewById(R.id.imageView_BackFeature);
        imageView_RewardFeature = (ImageView) view.findViewById(R.id.imageView_RewardFeature);
        imageView_CartFeature = (ImageView) view.findViewById(R.id.imageView_CartFeature);
    }

    private void listener() {

        imageView_BackFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_FeatureMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMenuShowFragment();
            }
        });
        textView_FeatureRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRecentShowFragment();
            }
        });
        textView_FeatureFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFavoriteShowFragment();
            }
        });
    }


    private void loadMenuShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRecentShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RecentShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadFavoriteShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, FavoriteShowFragment.newInstance())
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
