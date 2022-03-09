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

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.MenuFavoriteAdapter;
import com.technothinksup.consciouskitchen.model.FavortiteResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteShowFragment extends Fragment {

    private RecyclerView rv_favorite_item;
    private View view;

    private MenuFavoriteAdapter menuFavoriteAdapter;


    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    public static FavoriteShowFragment newInstance() {

        return new FavoriteShowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_favorite_show, container, false);


        init();

        setfavoriteData();

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);



        TextView textView_FavoriteMenu = (TextView) view.findViewById(R.id.textView_FavoriteMenu);
        TextView textView_FavoriteRecent = (TextView) view.findViewById(R.id.textView_FavoriteRecent);
        TextView textView_FavoriteFavorite = (TextView) view.findViewById(R.id.textView_FavoriteFavorite);
        TextView textView_FavoriteFeature = (TextView) view.findViewById(R.id.textView_FavoriteFeature);

        ImageView imageView_BackFavorite = (ImageView) view.findViewById(R.id.imageView_BackFavorite);
        ImageView imageView_RewardFavorite = (ImageView) view.findViewById(R.id.imageView_RewardFavorite);
        ImageView imageView_CartFavorite = (ImageView) view.findViewById(R.id.imageView_CartFavorite);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_FavoriteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMenuShowFragment();
            }
        });
        textView_FavoriteRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRecentShowFragment();
            }
        });
        textView_FavoriteFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFeatureShowFragment();
            }
        });


        return view;
    }

    private void init() {
        rv_favorite_item = view.findViewById(R.id.rv_favorite_item);
    }

    private void setfavoriteData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<FavortiteResponse> call = apiInterface.getFavouriteItemList("3");

        call.enqueue(new Callback<FavortiteResponse>() {
            @Override
            public void onResponse(Call<FavortiteResponse> call, Response<FavortiteResponse> response) {

                if(response.body().getStatus()==1){
                    menuFavoriteAdapter = new MenuFavoriteAdapter(getActivity(), response.body().getItemList(), response.body().getItemImagePath());
                    rv_favorite_item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_favorite_item.setAdapter(menuFavoriteAdapter);
                    menuFavoriteAdapter.notifyDataSetChanged();
                    rv_favorite_item.setHasFixedSize(true);
                }




            }

            @Override
            public void onFailure(Call<FavortiteResponse> call, Throwable t) {

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

    private void loadFeatureShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, FeatureShowFragment.newInstance())
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
