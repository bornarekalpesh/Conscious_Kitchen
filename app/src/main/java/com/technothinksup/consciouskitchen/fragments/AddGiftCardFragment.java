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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.GiftCardAdapter;
import com.technothinksup.consciouskitchen.model.AddGiftCardResponse;
import com.technothinksup.consciouskitchen.model.GiftCardResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddGiftCardFragment extends Fragment {

    private View view;

    private RecyclerView rv_gift_card;

    private FormEditText et_gift_card_code;

    private Button button_add_card;

    private GiftCardAdapter giftCardAdapter;

    private LinearLayout linear_layout_add,linear_layout_list;

    private TextView tv_add_gift_card;
    private ImageView imageView_Back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_gift_card, container, false);


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();
        listener();

        getData();

        return view;
    }



    private void init() {
        rv_gift_card=view.findViewById(R.id.rv_gift_card);
        et_gift_card_code=view.findViewById(R.id.et_gift_card_code);
        button_add_card=view.findViewById(R.id.button_add_card);
        linear_layout_add=view.findViewById(R.id.linear_layout_add);
        linear_layout_list=view.findViewById(R.id.linear_layout_list);
        tv_add_gift_card=view.findViewById(R.id.tv_add_gift_card);
        imageView_Back=view.findViewById(R.id.imageView_Back);
    }


    private void getData() {

        ApiInterface apiInterface=Api.getClient().create(ApiInterface.class);

        Call<GiftCardResponse>call=apiInterface.getGiftCardList(HomePageActivity.userId);

        call.enqueue(new Callback<GiftCardResponse>() {
            @Override
            public void onResponse(Call<GiftCardResponse> call, Response<GiftCardResponse> response) {

                giftCardAdapter = new GiftCardAdapter(getActivity(), response.body().getGiftCardList(),response.body().getIamgeUrl());
                rv_gift_card.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rv_gift_card.setAdapter(giftCardAdapter);
                giftCardAdapter.notifyDataSetChanged();
                rv_gift_card.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<GiftCardResponse> call, Throwable t) {

            }
        });
    }
    private void listener(){

        button_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<AddGiftCardResponse>call=apiInterface.addGiftCard(HomePageActivity.userId,et_gift_card_code.getText().toString());

                call.enqueue(new Callback<AddGiftCardResponse>() {
                    @Override
                    public void onResponse(Call<AddGiftCardResponse> call, Response<AddGiftCardResponse> response) {


                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            ((HomePageActivity)getActivity()).loadFragment(new AddGiftCardFragment(),true);




                    }

                    @Override
                    public void onFailure(Call<AddGiftCardResponse> call, Throwable t) {

                    }
                });

            }
        });

        tv_add_gift_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_layout_add.setVisibility(View.VISIBLE);
                linear_layout_list.setVisibility(View.GONE);
            }
        });

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }
}