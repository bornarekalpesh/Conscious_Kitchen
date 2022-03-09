package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.PaymentCardAdapter;
import com.technothinksup.consciouskitchen.model.GetPaymentCardResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreditCardListFragment extends Fragment {

    private View view;

    private RecyclerView rv_credit_card;
    private PaymentCardAdapter paymentCardAdapter;

    private TextView tv_add_credit_card;

    private ImageView imageView_Back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_credit_card_list, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        init();
        listener();
        setAdapter();
        return view;
    }

    private void listener() {
        tv_add_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ((HomePageActivity) getActivity()).loadFragment(new FragmentAddCreditCard(), true);
               // ((HomePageActivity) getActivity()).loadFragment(new FragmentOnlyAddCreditCard(), true);


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

    private void init() {
        rv_credit_card = view.findViewById(R.id.rv_credit_card);
        tv_add_credit_card = view.findViewById(R.id.tv_add_credit_card);
        imageView_Back = view.findViewById(R.id.imageView_Back);
    }

    private void setAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetPaymentCardResponse> cardResponseCall = apiInterface.getPaymentCardList(HomePageActivity.userId);

        cardResponseCall.enqueue(new Callback<GetPaymentCardResponse>() {
            @Override
            public void onResponse(Call<GetPaymentCardResponse> call, Response<GetPaymentCardResponse> response) {
                // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                if(response.body().getStatus()==1) {
                    paymentCardAdapter = new PaymentCardAdapter(getActivity(), response.body().getPaymentCardList());
                    rv_credit_card.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rv_credit_card.setAdapter(paymentCardAdapter);
                    paymentCardAdapter.notifyDataSetChanged();
                    rv_credit_card.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<GetPaymentCardResponse> call, Throwable t) {

            }
        });

    }
}