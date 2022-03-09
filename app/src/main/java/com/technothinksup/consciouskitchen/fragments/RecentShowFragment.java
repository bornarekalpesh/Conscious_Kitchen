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

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.MenuRecentAdapter;
import com.technothinksup.consciouskitchen.model.ItemDetail;
import com.technothinksup.consciouskitchen.model.RecentItemResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentShowFragment extends Fragment {

    private View view;
    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private TextView textView_RecentMenu, textView_RecentRecent, textView_RecentFavorite, textView_RecentFeature;

    private ImageView imageView_BackRecent, imageView_RewardRecent, imageView_CartRecent;

    private RecyclerView rv_recent__item;

    private MenuRecentAdapter menuRecentAdapter;

    public List<ItemDetail> itemDetailList = new ArrayList<>();

    public static RecentShowFragment newInstance() {

        return new RecentShowFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent_show, container, false);


        init();
        setRecentData();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_RecentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMenuShowFragment();
            }
        });
        textView_RecentFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFavoriteShowFragment();
            }
        });
        textView_RecentFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFeatureShowFragment();
            }
        });


        return view;
    }

    private void init() {
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        textView_RecentMenu = view.findViewById(R.id.textView_RecentMenu);
        textView_RecentRecent = view.findViewById(R.id.textView_RecentRecent);
        textView_RecentFavorite = view.findViewById(R.id.textView_RecentFavorite);
        textView_RecentFeature = view.findViewById(R.id.textView_RecentFeature);

        imageView_BackRecent = view.findViewById(R.id.imageView_BackRecent);
        imageView_RewardRecent = view.findViewById(R.id.imageView_RewardRecent);
        imageView_CartRecent = view.findViewById(R.id.imageView_CartRecent);

        rv_recent__item = view.findViewById(R.id.rv_recent__item);
    }

    private void setRecentData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<RecentItemResponse> call = apiInterface.getRecentItemList("1");

        call.enqueue(new Callback<RecentItemResponse>() {
            @Override
            public void onResponse(Call<RecentItemResponse> call, Response<RecentItemResponse> response) {

                // Toast.makeText(getContext(), "hiii", Toast.LENGTH_SHORT).show();


             /*   for (int i=0;i<response.body().getRecentItemList().size();i++){

                    ItemDetail itemDetail = new ItemDetail();
                    itemDetail.setItemName(response.body().getRecentItemList().get(i).getItemDetails().get(i).getItemName());
                    itemDetail.setItemImage(response.body().getRecentItemList().get(i).getItemDetails().get(i).getItemImage());

                    itemDetailList.add(itemDetail);



                }*/

                menuRecentAdapter = new MenuRecentAdapter(getActivity(), response.body().getItemList(), response.body().getItemImagePath());
                rv_recent__item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_recent__item.setAdapter(menuRecentAdapter);
                menuRecentAdapter.notifyDataSetChanged();
                rv_recent__item.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<RecentItemResponse> call, Throwable t) {

                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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
