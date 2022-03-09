package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CartAdapter;
import com.technothinksup.consciouskitchen.adapter.GiftCardAdapter;
import com.technothinksup.consciouskitchen.adapter.PaymentCardAdapter;
import com.technothinksup.consciouskitchen.adapter.PickUpTimeAdapter;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.CartResponse;
import com.technothinksup.consciouskitchen.model.DefaultBranchResponse;
import com.technothinksup.consciouskitchen.model.GetPaymentCardResponse;
import com.technothinksup.consciouskitchen.model.GiftCardResponse;
import com.technothinksup.consciouskitchen.model.NearestBranchResponse;
import com.technothinksup.consciouskitchen.model.PaymentResponse;
import com.technothinksup.consciouskitchen.model.PickupTimeResponse;
import com.technothinksup.consciouskitchen.model.PlaceOrderResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiftCardFragment extends Fragment {
    private View view;

    private RecyclerView rv_pickuptime;

    private RelativeLayout relativeLayout_HoldToPay;
    private TextView textView_AddNewCard;

    private ImageView imageView_BackConfirmAndPay, imageView_RewardConfirmAndPay, imageView_CartConfirmAndPay;


    private PickUpTimeAdapter pickUpTimeAdapter;

    private RequestBody reqLatitide, reqLongitude;

    private TextView tv_branch_address;

    private RecyclerView rv_gift_card, rv_credit_card;
    private GiftCardAdapter giftCardAdapter;
    private PaymentCardAdapter paymentCardAdapter;

    private String id = "";
    private float total_amt;
    private CartAdapter cartAdapter;

    private TextView tv_sub_total, tv_tax, tv_total;

    public static String CARD_NUMBER = "", EXPIRATION_MONTH, EXPIRATION_YEAR, CVV;


    public static String branchId, orderType, deliveryAddress, pickup_delivery_time = "", order_basic_amount, order_tax_amount, order_shipping_amount, order_total_amount;

    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    public static ConfirmAndPayFragment newInstance() {

        return new ConfirmAndPayFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gift_card, container, false);


        init();

        try {
            Bundle bundle = getArguments();
            id = bundle.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
        }


        listener();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        if (id.equalsIgnoreCase("2")) {
            rv_gift_card.setVisibility(View.VISIBLE);
            setGiftCardAdapter();
        } else {
            setAdapter();
        }


        setPickUpTimeAdapter();

        if (Common.defaultBranchId.equalsIgnoreCase("0")) {

            //Toast.makeText(getActivity(), "nearest", Toast.LENGTH_SHORT).show();
            setNearestBranchData();
        } else {
            setBranchData();

            //Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
        }


        setData();


        return view;
    }


    private void setData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CartResponse> call = apiInterface.getCartList(HomePageActivity.userId);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                total_amt = Float.valueOf(response.body().getTotalAmount());

                order_basic_amount = String.valueOf(response.body().getTotalBasicAmount());
                order_total_amount = String.valueOf(response.body().getTotalAmount());
                order_tax_amount = String.valueOf(response.body().getTotalGstAmount());

                Log.e("amt", "" + order_basic_amount);

                //  Toast.makeText(getContext(), "" + response.body().getTotalBasicAmount(), Toast.LENGTH_SHORT).show();


                tv_sub_total.setText(HomePageActivity.currency + response.body().getTotalBasicAmount());
                tv_tax.setText(HomePageActivity.currency + response.body().getTotalGstAmount());

                float total1 = Float.valueOf(response.body().getTotalBasicAmount()) + Float.valueOf(response.body().getTotalGstAmount());

                tv_total.setText(HomePageActivity.currency + total1);

                //  Log.e("total--", "" + total);
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetPaymentCardResponse> cardResponseCall = apiInterface.getPaymentCardList(HomePageActivity.userId);

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

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GiftCardResponse> call = apiInterface.getGiftCardList(HomePageActivity.userId);

        call.enqueue(new Callback<GiftCardResponse>() {
            @Override
            public void onResponse(Call<GiftCardResponse> call, Response<GiftCardResponse> response) {

                giftCardAdapter = new GiftCardAdapter(getActivity(), response.body().getGiftCardList(), response.body().getIamgeUrl());
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

    private void init() {

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        relativeLayout_HoldToPay = view.findViewById(R.id.relativeLayout_HoldToPay);

        textView_AddNewCard = view.findViewById(R.id.textView_AddNewCard);

        imageView_BackConfirmAndPay = view.findViewById(R.id.imageView_BackConfirmAndPay);
        imageView_RewardConfirmAndPay = view.findViewById(R.id.imageView_RewardConfirmAndPay);
        imageView_CartConfirmAndPay = view.findViewById(R.id.imageView_CartConfirmAndPay);


        rv_pickuptime = view.findViewById(R.id.rv_pickuptime);

        rv_gift_card = view.findViewById(R.id.rv_gift_card);
        rv_credit_card = view.findViewById(R.id.rv_credit_card);

        tv_branch_address = view.findViewById(R.id.tv_branch_address);

        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);


    }

    private void listener() {

        imageView_BackConfirmAndPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardConfirmAndPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartConfirmAndPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        relativeLayout_HoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loadTrackNowFragment();

                if (pickup_delivery_time.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please Select Pick Up Time !!", Toast.LENGTH_SHORT).show();
                } else {

                    if (PaymentCardAdapter.card_number.equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please Select Card", Toast.LENGTH_SHORT).show();
                    } else {

                        // Common.makePayment(getActivity(), HomePageActivity.name, HomePageActivity.email, PaymentCardAdapter.card_number, PaymentCardAdapter.card_exp_month, PaymentCardAdapter.card_exp_year, PaymentCardAdapter.card_cvv, order_total_amount);

                        makePayment();
                    }


                    // placeOrder();
                }


            }
        });

        textView_AddNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.equalsIgnoreCase("2")) {
                    ((HomePageActivity) getActivity()).loadFragment(new AddGiftCardFragment(), true);
                } else {
                    ((HomePageActivity) getActivity()).loadFragment(new FragmentAddCreditCard(), true);
                }
                //loadFragmentAddCreditCard();
            }
        });
    }

    private void makePayment() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Common.showProgressBar(getActivity());

        Call<PaymentResponse> call = apiInterface.makePayment(HomePageActivity.name, HomePageActivity.email, PaymentCardAdapter.card_number, PaymentCardAdapter.card_exp_month, PaymentCardAdapter.card_exp_year, PaymentCardAdapter.card_cvv, order_total_amount);

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {

                if (response.body().getStatus() == 1) {
                    placeOrder();

                    Common.removeProgressBar(getActivity());
                } else {
                    Common.removeProgressBar(getActivity());
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Common.removeProgressBar(getActivity());

            }
        });
    }

    public void placeOrder() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<PlaceOrderResponse> call = apiInterface.placeOrder(branchId, "1", HomePageActivity.userId, "", "", "", "",
                pickup_delivery_time, "", order_basic_amount, order_tax_amount, "", order_total_amount, "", "",
                "", "", "", "", "", "", "", "", "", "");

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {

                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    loadTrackNowFragment();
                }

                //  Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {

            }
        });
    }

    private void loadTrackNowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TrackNowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadFragmentAddCreditCard() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, FragmentAddCreditCard.newInstance())
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


    private void setBranchData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<DefaultBranchResponse> call = apiInterface.getDefaultBranch(HomePageActivity.userId);
        call.enqueue(new Callback<DefaultBranchResponse>() {
            @Override
            public void onResponse(Call<DefaultBranchResponse> call, Response<DefaultBranchResponse> response) {

                tv_branch_address.setText("" + response.body().getBranchDetails().get(0).getBranchAddress());
                branchId = response.body().getBranchDetails().get(0).getBranchId();

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


                branchId = response.body().getBranchList().get(0).getBranchId();

                tv_branch_address.setText(response.body().getBranchList().get(0).getBranchAddress());
                // branchContactNo=response.body().getBranchList().get(0).getBranchMobile();
            }

            @Override
            public void onFailure(Call<NearestBranchResponse> call, Throwable t) {

            }
        });
    }
}
