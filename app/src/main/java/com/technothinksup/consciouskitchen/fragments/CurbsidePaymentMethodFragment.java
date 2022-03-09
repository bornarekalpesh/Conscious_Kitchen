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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CartAdapter;
import com.technothinksup.consciouskitchen.adapter.PickUpTimeAdapter;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.CartResponse;
import com.technothinksup.consciouskitchen.model.DefaultBranchResponse;
import com.technothinksup.consciouskitchen.model.NearestBranchResponse;
import com.technothinksup.consciouskitchen.model.PickupTimeResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurbsidePaymentMethodFragment extends Fragment {
    private View view;

    private String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private TextView textView_CurbsidePaymentOptionPickup, textView_CurbsidePaymentOptionDelivery, textView_CurbsidePaymentOptionEdit;

    private CardView cardView_CurbsideWallet, cardView_CurbsideGiftCard, cardView_CurbsideCreditCard, cardView_CurbsideSelectPaymentOption;

    private ImageView imageView_BackCurbsidePaymentMethods, imageView_RewardCurbsidePaymentMethods, imageView_CartCurbsidePaymentMethods;

    private RecyclerView rv_pickuptime;
    private PickUpTimeAdapter pickUpTimeAdapter;
    private TextView tv_branch_address;

    private float total_amt;
    private CartAdapter cartAdapter;

    private TextView tv_sub_total, tv_tax, tv_total;

    public static CurbsidePaymentMethodFragment newInstance() {

        return new CurbsidePaymentMethodFragment();
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_curbside_payment_method, container, false);

        init();
        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();
/*
        if (Common.defaultBranchId.equalsIgnoreCase("0")) {

            //Toast.makeText(getActivity(), "nearest", Toast.LENGTH_SHORT).show();
            setNearestBranchData();
        } else {
            setBranchData();

            //Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
        }*/

        setNearestBranchData();
        setData();

        setPickUpTimeAdapter();


        return view;
    }


    private void init() {
        textView_CurbsidePaymentOptionPickup = view.findViewById(R.id.textView_CurbsidePaymentOptionPickup);
        textView_CurbsidePaymentOptionDelivery = view.findViewById(R.id.textView_CurbsidePaymentOptionDelivery);
        textView_CurbsidePaymentOptionEdit = view.findViewById(R.id.textView_CurbsidePaymentOptionEdit);

        cardView_CurbsideWallet = view.findViewById(R.id.cardView_CurbsideWallet);
        cardView_CurbsideGiftCard = view.findViewById(R.id.cardView_CurbsideGiftCard);
        cardView_CurbsideCreditCard = view.findViewById(R.id.cardView_CurbsideCreditCard);
        cardView_CurbsideSelectPaymentOption = (CardView) view.findViewById(R.id.cardView_CurbsideSelectPaymentOption);

        imageView_BackCurbsidePaymentMethods = view.findViewById(R.id.imageView_BackCurbsidePaymentMethods);
        imageView_RewardCurbsidePaymentMethods = view.findViewById(R.id.imageView_RewardCurbsidePaymentMethods);
        imageView_CartCurbsidePaymentMethods = view.findViewById(R.id.imageView_CartCurbsidePaymentMethods);

        rv_pickuptime = view.findViewById(R.id.rv_pickuptime);
        tv_branch_address = view.findViewById(R.id.tv_branch_address);

        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);

    }

    private void listener() {
        imageView_BackCurbsidePaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardCurbsidePaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartCurbsidePaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_CurbsidePaymentOptionPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPaymentMethodFragment();
            }
        });

        textView_CurbsidePaymentOptionDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryPaymentMethodFragment();
            }
        });

        textView_CurbsidePaymentOptionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadCurbsideInstructionFragment();
                ((HomePageActivity) getActivity()).loadFragment(new CurbsideInstructionListFragment(), true);
            }
        });

        cardView_CurbsideWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadCurbsideWalletFragment();

                WalletFragment walletFragment = new WalletFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                walletFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(walletFragment, true);

            }
        });

        cardView_CurbsideGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadCurbsideGiftCardFragment();
                CurbsideConfirmAndPayFragment curbsideConfirmAndPayFragment = new CurbsideConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "2");
                curbsideConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(curbsideConfirmAndPayFragment, true);
            }
        });

        cardView_CurbsideCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurbsideConfirmAndPayFragment curbsideConfirmAndPayFragment = new CurbsideConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                curbsideConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(curbsideConfirmAndPayFragment, true);
            }
        });

        cardView_CurbsideSelectPaymentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadCurbsideConfirmAndPayFragment();

                CurbsideConfirmAndPayFragment curbsideConfirmAndPayFragment = new CurbsideConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                curbsideConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(curbsideConfirmAndPayFragment, true);
            }
        });
    }

    private void setBranchData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<DefaultBranchResponse> call = apiInterface.getDefaultBranch(HomePageActivity.userId);
        call.enqueue(new Callback<DefaultBranchResponse>() {
            @Override
            public void onResponse(Call<DefaultBranchResponse> call, Response<DefaultBranchResponse> response) {

                tv_branch_address.setText("" + response.body().getBranchDetails().get(0).getBranchAddress());

            }

            @Override
            public void onFailure(Call<DefaultBranchResponse> call, Throwable t) {

            }
        });
    }

    private void setNearestBranchData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);


        Call<NearestBranchResponse> call = apiInterface.getNearestBranchList(Common.latitude, Common.longitude);

        call.enqueue(new Callback<NearestBranchResponse>() {
            @Override
            public void onResponse(Call<NearestBranchResponse> call, Response<NearestBranchResponse> response) {
                // Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();


                //tv_branch_name.setText(response.body().getBranchList().get(0).getBranchName());

                // branchId = response.body().getBranchList().get(0).getBranchId();


                tv_branch_address.setText(response.body().getBranchList().get(0).getBranchAddress());
                // branchContactNo=response.body().getBranchList().get(0).getBranchMobile();
            }

            @Override
            public void onFailure(Call<NearestBranchResponse> call, Throwable t) {

            }
        });
    }

    private void setData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CartResponse> call = apiInterface.getCartList(HomePageActivity.userId);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                total_amt = Float.valueOf(response.body().getTotalAmount());


                tv_sub_total.setText(HomePageActivity.currency + response.body().getTotalBasicAmount());
                tv_tax.setText(HomePageActivity.currency + response.body().getTotalGstAmount());

                float total1 = Float.valueOf(response.body().getTotalBasicAmount()) + Float.valueOf(response.body().getTotalGstAmount());

                tv_total.setText(HomePageActivity.currency + total1);

                // Log.e("total--", "" + total);
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setPickUpTimeAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);


        Call<PickupTimeResponse> call = apiInterface.getPickUpTime("1");

        call.enqueue(new Callback<PickupTimeResponse>() {
            @Override
            public void onResponse(Call<PickupTimeResponse> call, Response<PickupTimeResponse> response) {

                pickUpTimeAdapter = new PickUpTimeAdapter(getActivity(), response.body().getPickupTimeList());
                rv_pickuptime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rv_pickuptime.setAdapter(pickUpTimeAdapter);
                pickUpTimeAdapter.notifyDataSetChanged();
                rv_pickuptime.setHasFixedSize(true);

            }

            @Override
            public void onFailure(Call<PickupTimeResponse> call, Throwable t) {

            }
        });


    }

    private void loadCurbsideConfirmAndPayFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideConfirmAndPayFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsideWalletFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideWalletFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsideGiftCardFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideGiftCardFragment.newInstance())
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


    private void loadCurbsideInstructionFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideInstructionFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadPaymentMethodFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, PaymentMethodFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadDeliveryPaymentMethodFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryPaymentMethodFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
