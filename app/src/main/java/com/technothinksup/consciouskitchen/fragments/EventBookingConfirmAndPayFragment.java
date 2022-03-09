package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.GiftCardAdapter;
import com.technothinksup.consciouskitchen.adapter.PaymentCardAdapter;
import com.technothinksup.consciouskitchen.model.GetPaymentCardResponse;
import com.technothinksup.consciouskitchen.model.GiftCardResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventBookingConfirmAndPayFragment extends Fragment {

    private View view;

    private GiftCardAdapter giftCardAdapter;
    private PaymentCardAdapter paymentCardAdapter;

    private RecyclerView rv_gift_card,rv_credit_card;

    private String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_event_booking_gift_card, container, false);

        init();

        try {
            Bundle bundle = getArguments();
            id = bundle.getString("id");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(id.equalsIgnoreCase("1")){
            rv_gift_card.setVisibility(View.VISIBLE);
            setGiftCardAdapter();
        }else {
            setAdapter();
        }

        return view;
    }

    private void init() {
        rv_gift_card=view.findViewById(R.id.rv_gift_card);
        rv_credit_card=view.findViewById(R.id.rv_credit_card);
    }

    private void setAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetPaymentCardResponse> cardResponseCall=apiInterface.getPaymentCardList(HomePageActivity.userId);

        cardResponseCall.enqueue(new Callback<GetPaymentCardResponse>() {
            @Override
            public void onResponse(Call<GetPaymentCardResponse> call, Response<GetPaymentCardResponse> response) {
               // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                paymentCardAdapter = new PaymentCardAdapter(getActivity(), response.body().getPaymentCardList());
                rv_credit_card.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rv_credit_card.setAdapter(paymentCardAdapter);
                paymentCardAdapter.notifyDataSetChanged();
                rv_credit_card.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<GetPaymentCardResponse> call, Throwable t) {

            }
        });

    }

    private void setGiftCardAdapter() {

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
}