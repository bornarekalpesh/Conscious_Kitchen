package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.technothinksup.consciouskitchen.adapter.DeliveryGiftCardAdapter;
import com.technothinksup.consciouskitchen.adapter.DeliveryPaymentCardAdapter;
import com.technothinksup.consciouskitchen.adapter.GiftCardAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryConfirmPayFragment extends Fragment {

    private View view;
    private String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};
    private TextView textView_DeliveryConfirmPayEditInformation, textView_DeliveryConfirmPayAddNewCard, tv_customer_address, tv_customer_info, tv_special_instruction, tv_select_address;

    private ImageView imageView_DeliveryConfirmPayInformation, imageView_BackDeliveryConfirmPay, imageView_RewardDeliveryConfirmPay, imageView_CartDeliveryConfirmPay;

    private RelativeLayout relativeLayout_HoldToPay, relativeLayout_HoldToPay2;

    private RecyclerView recycler_DeliveryConfirmPay;

    private RecyclerView rv_pickuptime;
    private PickUpTimeAdapter pickUpTimeAdapter;
    private TextView tv_branch_address;

    private RecyclerView rv_gift_card, rv_credit_card;
    private DeliveryGiftCardAdapter giftCardAdapter;
    private DeliveryPaymentCardAdapter paymentCardAdapter;

    private String id = "";

    private float total_amt;
    private CartAdapter cartAdapter;

    private TextView tv_sub_total, tv_tax, tv_total;

    private String selectedAddress, selectedCustName, selectedDeliveryInstruction;

    public static String branchId, orderType, deliveryAddress, pickup_delivery_time = "", order_basic_amount, order_tax_amount, order_shipping_amount, order_total_amount;

    private LinearLayout linear_layout_delivery_info;

    private LinearLayout linear_layout_gift_card, linear_layout_credit_card;

    private String payment_id = "";

    public static DeliveryConfirmPayFragment newInstance() {

        return new DeliveryConfirmPayFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_delivery_confirm_pay, container, false);

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        init();

        try {
            Bundle bundle = getArguments();
            id = bundle.getString("id");


            selectedAddress = Common.getSavedUserData(getActivity(), "selectedAddress");

            if (!selectedAddress.equalsIgnoreCase("")) {
                setDeliveryInformationData();
            } else {
                Toast.makeText(getActivity(), "Please Select Delivery Address", Toast.LENGTH_SHORT).show();
            }


            // Toast.makeText(getActivity(), "" + selectedAddress, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();
       /* if (Common.defaultBranchId.equalsIgnoreCase("0")) {

            //Toast.makeText(getActivity(), "nearest", Toast.LENGTH_SHORT).show();
            setNearestBranchData();
        } else {
            setBranchData();

            //Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
        }*/

        setNearestBranchData();

        setPickUpTimeAdapter();

        setData();

        //  getCartList();

        if (id.equalsIgnoreCase("2")) {
            rv_gift_card.setVisibility(View.VISIBLE);
            setGiftCardAdapter();
        } else {
            setAdapter();
        }
        return view;
    }

    private void setDeliveryInformationData() {
        tv_customer_address.setText("" + Common.getSavedUserData(getActivity(), "selectedAddress"));
        tv_customer_info.setText("" + Common.getSavedUserData(getActivity(), "selectedCustName"));
        tv_special_instruction.setText("" + Common.getSavedUserData(getActivity(), "selectedDeliveryInstruction"));
    }


    private void init() {
        textView_DeliveryConfirmPayEditInformation = view.findViewById(R.id.textView_DeliveryConfirmPayEditInformation);
        imageView_DeliveryConfirmPayInformation = view.findViewById(R.id.imageView_DeliveryConfirmPayInformation);
        imageView_BackDeliveryConfirmPay = view.findViewById(R.id.imageView_BackDeliveryConfirmPay);
        imageView_RewardDeliveryConfirmPay = view.findViewById(R.id.imageView_RewardDeliveryConfirmPay);
        imageView_CartDeliveryConfirmPay = view.findViewById(R.id.imageView_CartDeliveryConfirmPay);
        relativeLayout_HoldToPay = view.findViewById(R.id.relativeLayout_HoldToPay);
        // recycler_DeliveryConfirmPay =  view.findViewById(R.id.recycler_DeliveryConfirmPay);
        textView_DeliveryConfirmPayAddNewCard = view.findViewById(R.id.textView_DeliveryConfirmPayAddNewCard);
        tv_branch_address = view.findViewById(R.id.tv_branch_address);

        rv_pickuptime = view.findViewById(R.id.rv_pickuptime);

        rv_gift_card = view.findViewById(R.id.rv_gift_card);
        rv_credit_card = view.findViewById(R.id.rv_credit_card);

        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);

        tv_customer_address = view.findViewById(R.id.tv_customer_address);
        tv_customer_info = view.findViewById(R.id.tv_customer_info);
        tv_special_instruction = view.findViewById(R.id.tv_special_instruction);
        linear_layout_delivery_info = view.findViewById(R.id.linear_layout_delivery_info);
        tv_select_address = view.findViewById(R.id.tv_select_address);

        linear_layout_gift_card = view.findViewById(R.id.linear_layout_gift_card);
        linear_layout_credit_card = view.findViewById(R.id.linear_layout_credit_card);
        relativeLayout_HoldToPay2 = view.findViewById(R.id.relativeLayout_HoldToPay2);

        /*final LinearLayoutManager layoutManagerSecond = new LinearLayoutManager(this.getActivity());
        layoutManagerSecond.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_DeliveryConfirmPay.setLayoutManager(layoutManagerSecond);
        recycler_DeliveryConfirmPay.setAdapter(new HorizontalAdapterForCard(new int[]{R.drawable.card, R.drawable.card, R.drawable.card}));*/
    }

    private void listener() {
        imageView_BackDeliveryConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardDeliveryConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartDeliveryConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        textView_DeliveryConfirmPayAddNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.equalsIgnoreCase("2")) {
                    ((HomePageActivity) getActivity()).loadFragment(new AddGiftCardFragment(), true);
                } else {
                    ((HomePageActivity) getActivity()).loadFragment(new FragmentAddCreditCard(), true);
                }
            }
        });

        tv_select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) getActivity()).loadFragment(new DeliveryLocationFragment(), true);
            }
        });

        textView_DeliveryConfirmPayEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_layout_delivery_info.getVisibility() == View.GONE) {
                    linear_layout_delivery_info.setVisibility(View.VISIBLE);
                } else {
                    linear_layout_delivery_info.setVisibility(View.GONE);
                }
            }
        });

       /* relativeLayout_HoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pickup_delivery_time.equalsIgnoreCase("")) {
                    if (!selectedAddress.equalsIgnoreCase("")) {

                        if (!DeliveryPaymentCardAdapter.card_number.equalsIgnoreCase("")) {

                            makePayment();

                        } else {
                            Toast.makeText(getActivity(), "Please Select Card", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getActivity(), "Please Select the Delivery Address", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please Select Pick Up Time !!", Toast.LENGTH_SHORT).show();
                }
                //loadOrderDeliveryFragment();
            }
        });*/

        relativeLayout_HoldToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loadTrackNowFragment();

              /*  if (pickup_delivery_time.equalsIgnoreCase("")) {
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
*/

                if (id.equalsIgnoreCase("2")) {
                   // Toast.makeText(getContext(), "GiftCardClick", Toast.LENGTH_SHORT).show();

                    if (DeliveryGiftCardAdapter.isCreditCard == 1) {
                      //  Toast.makeText(getActivity(), "Credit card requred", Toast.LENGTH_SHORT).show();

                        linear_layout_gift_card.setVisibility(View.GONE);
                        linear_layout_credit_card.setVisibility(View.VISIBLE);
                        relativeLayout_HoldToPay2.setVisibility(View.VISIBLE);
                        relativeLayout_HoldToPay.setVisibility(View.GONE);

                        setAdapter();
                    } else {


                       // Toast.makeText(getActivity(), "Credit card is not  requred", Toast.LENGTH_SHORT).show();
                        if (pickup_delivery_time.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Please Select Pick Up Time !!", Toast.LENGTH_SHORT).show();
                        } else {
                            //  makePayment();

                            placeWithoutPaymentOrder();
                        }
                    }
                } else {
                    //Toast.makeText(getActivity(), "CreditCardClick", Toast.LENGTH_SHORT).show();


                    // credit card
                    if (pickup_delivery_time.equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please Select Pick Up Time !!", Toast.LENGTH_SHORT).show();
                    } else {

                        if (DeliveryPaymentCardAdapter.card_number.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Please Select Card", Toast.LENGTH_SHORT).show();
                        } else {

                            // Common.makePayment(getActivity(), HomePageActivity.name, HomePageActivity.email, PaymentCardAdapter.card_number, PaymentCardAdapter.card_exp_month, PaymentCardAdapter.card_exp_year, PaymentCardAdapter.card_cvv, order_total_amount);

                            makePayment();


                        }


                        // placeOrder();
                    }
                }


            }
        });

        relativeLayout_HoldToPay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if (pickup_delivery_time.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please Select Pick Up Time !!", Toast.LENGTH_SHORT).show();
                }
                  else
                      {
                    if (DeliveryPaymentCardAdapter.card_number.equalsIgnoreCase("")) {
                        Toast.makeText(getActivity(), "Please Select Card", Toast.LENGTH_SHORT).show();
                    } else {

                        // Common.makePayment(getActivity(), HomePageActivity.name, HomePageActivity.email, PaymentCardAdapter.card_number, PaymentCardAdapter.card_exp_month, PaymentCardAdapter.card_exp_year, PaymentCardAdapter.card_cvv, order_total_amount);

                        makePayment();
                    }


                    // placeOrder();
                }

            }
        });

    }

    private void makePayment() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Common.showProgressBar(getActivity());

        Call<PaymentResponse> call = apiInterface.makePayment(HomePageActivity.name, HomePageActivity.email, DeliveryPaymentCardAdapter.card_number, DeliveryPaymentCardAdapter.card_exp_month, DeliveryPaymentCardAdapter.card_exp_year, DeliveryPaymentCardAdapter.card_cvv, order_total_amount);

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {

                if (response.body().getStatus() == 1) {
                    placeOrder();

                    payment_id = response.body().getPaymentId();

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


                tv_sub_total.setText(HomePageActivity.currency + response.body().getTotalBasicAmount());
                tv_tax.setText(HomePageActivity.currency + response.body().getTotalGstAmount());

                float total1 = Float.valueOf(response.body().getTotalBasicAmount()) + Float.valueOf(response.body().getTotalGstAmount());

                tv_total.setText(HomePageActivity.currency + total1);

                //Log.e("total--", "" + total);
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void setAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetPaymentCardResponse> cardResponseCall = apiInterface.getPaymentCardList(HomePageActivity.userId);

        cardResponseCall.enqueue(new Callback<GetPaymentCardResponse>() {
            @Override
            public void onResponse(Call<GetPaymentCardResponse> call, Response<GetPaymentCardResponse> response) {
                // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                paymentCardAdapter = new DeliveryPaymentCardAdapter(getActivity(), response.body().getPaymentCardList());
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

    private void getCartList() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CartResponse> call = apiInterface.getCartList(HomePageActivity.userId);

        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {

                order_basic_amount = String.valueOf(response.body().getTotalBasicAmount());
                order_total_amount = String.valueOf(response.body().getTotalAmount());
                order_tax_amount = String.valueOf(response.body().getTotalGstAmount());

                Log.e("amt", "" + order_basic_amount);

                // Toast.makeText(getContext(), "" + response.body().getTotalBasicAmount(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    private void setGiftCardAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GiftCardResponse> call = apiInterface.getGiftCardList(HomePageActivity.userId);

        call.enqueue(new Callback<GiftCardResponse>() {
            @Override
            public void onResponse(Call<GiftCardResponse> call, Response<GiftCardResponse> response) {
                giftCardAdapter = new DeliveryGiftCardAdapter(getActivity(), response.body().getGiftCardList(), response.body().getIamgeUrl());
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


    private void placeOrder() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<PlaceOrderResponse> call = apiInterface.placeOrder(branchId, "2", HomePageActivity.userId, "", "", "", "",
                pickup_delivery_time, "", order_basic_amount, order_tax_amount, "", order_total_amount, "", "",
                "", "", String.valueOf(DeliveryGiftCardAdapter.gift_card_used_amt), DeliveryGiftCardAdapter.used_gift_card_code, DeliveryGiftCardAdapter.used_gift_card_id, "", "", "", "", "");

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {

                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    //loadDeliveryTrackNowFragment();
                    // loadOrderDeliveryFragment();

                    ((HomePageActivity) getActivity()).loadFragment(new TrackNowFragment(), true);
                }

                // Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {

            }
        });
    }

    public void placeWithoutPaymentOrder() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<PlaceOrderResponse> call = apiInterface.placeOrder(branchId, "1", HomePageActivity.userId, "", "", "", "",
                pickup_delivery_time, "", order_basic_amount, order_tax_amount, "", order_total_amount, "0", "",
                "", "", String.valueOf(GiftCardAdapter.gift_card_used_amt), GiftCardAdapter.used_gift_card_code, GiftCardAdapter.used_gift_card_id, "", "", "", "", "");

        call.enqueue(new Callback<PlaceOrderResponse>() {
            @Override
            public void onResponse(Call<PlaceOrderResponse> call, Response<PlaceOrderResponse> response) {

                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    ((HomePageActivity) getActivity()).loadFragment(new TrackNowFragment(), true);
                }

                //  Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PlaceOrderResponse> call, Throwable t) {

            }
        });
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


    private void loadDeliveryTrackNowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryTrackNowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadOrderDeliveryFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, OrderDeliveryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


}
