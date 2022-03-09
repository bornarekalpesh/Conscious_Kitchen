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
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPaymentMethodFragment extends Fragment {

    private View view;

    private String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private ImageView imageView_BackDeliveryPaymentMethiod, imageView_RewardDeliveryPaymentMethiod, imageView_CartDeliveryPaymentMethiod, imageView_DeliveryInformation;

    private TickSeekBar tickSeekBar;

    private TextView textView_DeliveryEditInformation, textView_DeliveryPaymentOptionPickup, textView_DeliveryPaymentOptionCurbside, tv_tip_value;

    private CardView cardView_DeliverySelectPaymentOption, cardView_DeliveryWallet, cardView_DeliveryGiftCard, cardView_DeliveryCreditCard;

    private RecyclerView rv_pickuptime;
    private PickUpTimeAdapter pickUpTimeAdapter;
    private TextView tv_branch_address;

    private float total_amt;
    private CartAdapter cartAdapter;

    private TextView tv_sub_total, tv_tax, tv_total;

    private ImageView iv_tip_minus, iv_tip_plus;

    private int tipCounter = 0;


    public static DeliveryPaymentMethodFragment newInstance() {

        return new DeliveryPaymentMethodFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_delivery_payment_method, container, false);

        init();

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();
       /* if (Common.defaultBranchId.equalsIgnoreCase("0")) {

            // Toast.makeText(getActivity(), "nearest", Toast.LENGTH_SHORT).show();
            setNearestBranchData();
        } else {
            setBranchData();

            //  Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
        }*/
        setNearestBranchData();

        setPickUpTimeAdapter();

        setData();


        return view;
    }


    private void init() {
        imageView_BackDeliveryPaymentMethiod = view.findViewById(R.id.imageView_BackDeliveryPaymentMethiod);
        imageView_RewardDeliveryPaymentMethiod = view.findViewById(R.id.imageView_RewardDeliveryPaymentMethiod);
        imageView_CartDeliveryPaymentMethiod = view.findViewById(R.id.imageView_CartDeliveryPaymentMethiod);
        imageView_DeliveryInformation = view.findViewById(R.id.imageView_DeliveryInformation);

        tickSeekBar = view.findViewById(R.id.listener);

        textView_DeliveryEditInformation = view.findViewById(R.id.textView_DeliveryEditInformation);
        cardView_DeliverySelectPaymentOption = view.findViewById(R.id.cardView_DeliverySelectPaymentOption);
        cardView_DeliveryWallet = view.findViewById(R.id.cardView_DeliveryWallet);
        cardView_DeliveryGiftCard = view.findViewById(R.id.cardView_DeliveryGiftCard);
        cardView_DeliveryCreditCard = view.findViewById(R.id.cardView_DeliveryCreditCard);
        textView_DeliveryPaymentOptionPickup = view.findViewById(R.id.textView_DeliveryPaymentOptionPickup);

        textView_DeliveryPaymentOptionCurbside = view.findViewById(R.id.textView_DeliveryPaymentOptionCurbside);

        rv_pickuptime = view.findViewById(R.id.rv_pickuptime);
        tv_branch_address = view.findViewById(R.id.tv_branch_address);

        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);

        iv_tip_minus = view.findViewById(R.id.iv_tip_minus);
        iv_tip_plus = view.findViewById(R.id.iv_tip_plus);
        tv_tip_value = view.findViewById(R.id.tv_tip_value);

    }

    private void listener() {
        textView_DeliveryPaymentOptionPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPaymentMethodFragment();
            }
        });

        textView_DeliveryPaymentOptionCurbside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsidePaymentMethodFragment();
            }
        });

        imageView_BackDeliveryPaymentMethiod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardDeliveryPaymentMethiod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartDeliveryPaymentMethiod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_DeliveryEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView_DeliveryInformation.getVisibility() == View.GONE) {
                    imageView_DeliveryInformation.setVisibility(View.VISIBLE);
                } else {
                    imageView_DeliveryInformation.setVisibility(View.GONE);
                }
            }
        });

        cardView_DeliverySelectPaymentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loadDeliveryConfirmPayFragment();

                DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                deliveryConfirmPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(deliveryConfirmPayFragment, true);
            }
        });

        cardView_DeliveryWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadDeliveryWalletFragment();

                DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "1");
                deliveryConfirmPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(deliveryConfirmPayFragment, true);


            }
        });

        cardView_DeliveryGiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    loadDeliveryGiftFragment();
                DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "2");
                deliveryConfirmPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(deliveryConfirmPayFragment, true);
            }
        });

        cardView_DeliveryCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loadDeliveryConfirmPayFragment();

                DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                deliveryConfirmPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(deliveryConfirmPayFragment, true);
            }
        });

        tickSeekBar.setProgress(0);

        iv_tip_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipCounter = tipCounter + 10;

               // Toast.makeText(getActivity(), "" + tipCounter, Toast.LENGTH_SHORT).show();

                tickSeekBar.setProgress(tipCounter);

                tv_tip_value.setText("" + tipCounter);


                // tickSeekBar.setProgress(80);
            }
        });

        iv_tip_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "minus tip", Toast.LENGTH_SHORT).show();

                if (tipCounter == 0) {
                    tipCounter = 0;
                    tickSeekBar.setProgress(tipCounter);
                    tv_tip_value.setText("" + tipCounter);
                } else {
                    tipCounter = tipCounter - 10;

                    tickSeekBar.setProgress(tipCounter);

                    tv_tip_value.setText("" + tipCounter);

                  //  Toast.makeText(getActivity(), "" + tipCounter, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tickSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {

                tipCounter = seekBar.getProgress();

                //Toast.makeText(getContext(), "" + tipCounter, Toast.LENGTH_SHORT).show();

                tv_tip_value.setText("" + tipCounter);

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

                tv_tip_value.setText("" + tipCounter);

                // Log.e("total--",""+total);
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


    private void loadDeliveryGiftFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryGiftFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsidePaymentMethodFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsidePaymentMethodFragment.newInstance())
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

    private void loadDeliveryConfirmPayFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryConfirmPayFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadDeliveryWalletFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryWalletFragment.newInstance())
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
