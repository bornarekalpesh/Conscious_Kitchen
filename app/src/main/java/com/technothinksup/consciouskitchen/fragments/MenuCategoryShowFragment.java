package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CategoryAdapter;
import com.technothinksup.consciouskitchen.model.CategoryResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCategoryShowFragment extends Fragment {

    private View view;

    private StateProgressBar stateProgressBar;

    private TextView textView_MenuMenu, textView_MenuRecent, textView_MenuFavorite, textView_MenuFeature;

    private RecyclerView rv_category;

    private ImageView imageView_BackMenu, imageView_RewardMenu, imageView_CartMenu;

    private CategoryAdapter categoryMenuAdapter;


    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    public static MenuCategoryShowFragment newInstance() {

        return new MenuCategoryShowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_show, container, false);

        init();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();


        listener();


        setMenuAdapter();




      /*  relativeLyout_OrderSandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubMenuFragment();
            }
        });

        relativeLyout_OrderDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubMenuFragment();
            }
        });
        relativeLyout_OrderSalad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubMenuFragment();
            }
        });
        relativeLyout_OrderDesserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubMenuFragment();
            }
        });
        relativeLyout_OrderSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubMenuFragment();
            }
        });*/


        return view;
    }

    private void setMenuAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CategoryResponse> call = apiInterface.getCategoryList();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

               // Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();

                if(response.body().getStatus()==1){
                    categoryMenuAdapter = new CategoryAdapter(getActivity(), response.body().getCategoryList(), response.body().getCategoryImagePath());
                    rv_category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_category.setAdapter(categoryMenuAdapter);
                    categoryMenuAdapter.notifyDataSetChanged();
                    rv_category.setHasFixedSize(true);
                }else {

                }



            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void init() {
        rv_category = view.findViewById(R.id.rv_category);


        stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        textView_MenuMenu = view.findViewById(R.id.textView_MenuMenu);
        textView_MenuRecent = view.findViewById(R.id.textView_MenuRecent);
        textView_MenuFavorite = view.findViewById(R.id.textView_MenuFavorite);
        textView_MenuFeature = view.findViewById(R.id.textView_MenuFeature);


        imageView_BackMenu = view.findViewById(R.id.imageView_BackMenu);
        imageView_RewardMenu = view.findViewById(R.id.imageView_RewardMenu);
        imageView_CartMenu = view.findViewById(R.id.imageView_CartMenu);


    }


    private void listener() {

        imageView_BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_MenuRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRecentShowFragment();
            }
        });
        textView_MenuFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFavoriteShowFragment();
            }
        });
        textView_MenuFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFeatureShowFragment();
            }
        });


    }

    private void loadRecentShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RecentShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadSubMenuFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, SubMenuFragment.newInstance())
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
