package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.OrderListAdapter;
import com.technothinksup.consciouskitchen.model.OrderResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderListFragment extends Fragment {

    private View view;

    private RecyclerView rv_order;

    private OrderListAdapter orderListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_order_list, container, false);

        init();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();


        getOrderList();
        imageBackListener();

        return view;
    }

    private void init() {
        rv_order=view.findViewById(R.id.rv_order);
    }

    private void imageBackListener() {
        ImageView imageView_Back=view.findViewById(R.id.imageView_Back);

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void getOrderList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<OrderResponse>call=apiInterface.getOrderList(HomePageActivity.userId);

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {

                orderListAdapter = new OrderListAdapter(getActivity(), response.body().getOrderList());
                rv_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_order.setAdapter(orderListAdapter);
                orderListAdapter.notifyDataSetChanged();
                rv_order.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });



    }
}