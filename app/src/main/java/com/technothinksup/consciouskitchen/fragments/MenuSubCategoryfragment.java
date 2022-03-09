package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.MenuSubCategoryAdapter;
import com.technothinksup.consciouskitchen.model.MenuSubCategoryResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuSubCategoryfragment extends Fragment {

    private View view;

    private String food_cat_id,food_cat_name;

    private MenuSubCategoryAdapter menuSubCategoryAdapter;
    private RecyclerView rv_menuSubCategory;

    private TextView tv_title;

    private ImageView imageView_BackMenu;

    private StateProgressBar stateProgressBar;

    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_menu_sub_categoryfragment, container, false);

        init();
        listener();

        try {
            Bundle bundle = getArguments();
            food_cat_id = bundle.getString("food_cat_id");
            food_cat_name = bundle.getString("food_cat_name");

            tv_title.setText(""+food_cat_name);
        }catch (Exception e){
            e.printStackTrace();
        }




      //  Toast.makeText(getContext(), ""+food_cat_id+food_cat_name, Toast.LENGTH_SHORT).show();

        setSubCategoryMenuAdapter();
        return view;



    }

    private void listener() {

        imageView_BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void init() {
        rv_menuSubCategory=view.findViewById(R.id.rv_menuSubCategory);
        tv_title=view.findViewById(R.id.tv_title);
        imageView_BackMenu=view.findViewById(R.id.imageView_BackMenu);


        stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

    }


    private void setSubCategoryMenuAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<MenuSubCategoryResponse> call = apiInterface.getMenuSubCategoryList(food_cat_id);
        call.enqueue(new Callback<MenuSubCategoryResponse>() {
            @Override
            public void onResponse(Call<MenuSubCategoryResponse> call, Response<MenuSubCategoryResponse> response) {

               // Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
                 menuSubCategoryAdapter = new MenuSubCategoryAdapter(getActivity(), response.body().getItemList(), response.body().getItemImagePath());
                rv_menuSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_menuSubCategory.setAdapter(menuSubCategoryAdapter);
                menuSubCategoryAdapter.notifyDataSetChanged();
                rv_menuSubCategory.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<MenuSubCategoryResponse> call, Throwable t) {

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}