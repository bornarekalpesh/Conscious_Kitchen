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
import com.technothinksup.consciouskitchen.model.PickUpInstructionResponse;
import com.technothinksup.consciouskitchen.model.PickupTimeResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodFragment extends Fragment {

    private View view;

    private String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private CardView cardView_SelectPaymentOption, cardView_Wallet, cardView_GiftCard, cardView_CreditCard;

    private ImageView imageView_BackPaymentMethods, imageView_RewardPaymentMethods, imageView_CartPaymentMethods;

    private TextView textView_PaymentOptionPickup, textView_PaymentOptionDelivery, textView_PaymentOptionCurbside, tv_branch_address, tv_pickup_instruction_value;

    private RecyclerView rv_pickuptime;
    private PickUpTimeAdapter pickUpTimeAdapter;

    private float total_amt;
    private CartAdapter cartAdapter;

    private TextView tv_sub_total, tv_tax, tv_total;

    public static PaymentMethodFragment newInstance() {

        return new PaymentMethodFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_paymet_methods, container, false);

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        init();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();

        setPickUpTimeAdapter();
        getPickUpInstruction();

       /* if (Common.defaultBranchId.equals("0")) {

            //  Toast.makeText(getActivity(), "nearest", Toast.LENGTH_SHORT).show();
            setNearestBranchData();
        }
        else
            {
            setBranchData();

            // Toast.makeText(getActivity(), "default", Toast.LENGTH_SHORT).show();
        }
*/
        setNearestBranchData();

        setData();


        return view;
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

                // Log.e("total--",""+total1);
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

    private void getPickUpInstruction() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<PickUpInstructionResponse> call = apiInterface.getPickUpInstruction(HomePageActivity.userId);
        //Toast.makeText(getActivity(),HomePageActivity.userId,Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<PickUpInstructionResponse>() {
            @Override
            public void onResponse(Call<PickUpInstructionResponse> call, Response<PickUpInstructionResponse> response) {
                // Toast.makeText(getContext(), "hii", Toast.LENGTH_SHORT).show();

                tv_pickup_instruction_value.setText("" + response.body().getPickupInstruction().get(0).getCustomerPickupIns());
            }

            @Override
            public void onFailure(Call<PickUpInstructionResponse> call, Throwable t) {

            }
        });

    }


    private void init() {
        cardView_SelectPaymentOption = view.findViewById(R.id.cardView_SelectPaymentOption);
        cardView_Wallet = view.findViewById(R.id.cardView_Wallet);
        cardView_GiftCard = view.findViewById(R.id.cardView_GiftCard);
        cardView_CreditCard = view.findViewById(R.id.cardView_CreditCard);

        imageView_BackPaymentMethods = view.findViewById(R.id.imageView_BackPaymentMethods);
        imageView_RewardPaymentMethods = view.findViewById(R.id.imageView_RewardPaymentMethods);
        imageView_CartPaymentMethods = view.findViewById(R.id.imageView_CartPaymentMethods);

        textView_PaymentOptionPickup = view.findViewById(R.id.textView_PaymentOptionPickup);
        textView_PaymentOptionDelivery = view.findViewById(R.id.textView_PaymentOptionDelivery);
        textView_PaymentOptionCurbside = view.findViewById(R.id.textView_PaymentOptionCurbside);

        rv_pickuptime = view.findViewById(R.id.rv_pickuptime);
        tv_branch_address = view.findViewById(R.id.tv_branch_address);

        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);

        tv_pickup_instruction_value = view.findViewById(R.id.tv_pickup_instruction_value);

    }

    private void listener() {

        textView_PaymentOptionPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textView_PaymentOptionDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDeliveryPaymentMethodFragment();
            }
        });

        textView_PaymentOptionCurbside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCurbsidePaymentMethodFragment();
            }
        });

        imageView_BackPaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardPaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartPaymentMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        cardView_CreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadConfirmAndPayFragment();

                ConfirmAndPayFragment confirmAndPayFragment = new ConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                confirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(confirmAndPayFragment, true);
            }
        });


        cardView_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadWalletFragment();

                ConfirmAndPayFragment walletFragment = new ConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "1");
                walletFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(walletFragment, true);

            }
        });

        cardView_GiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ConfirmAndPayFragment confirmAndPayFragment = new ConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "2");
                confirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(confirmAndPayFragment, true);
            }
        });
        cardView_SelectPaymentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loadConfirmAndPayFragment();

                ConfirmAndPayFragment confirmAndPayFragment = new ConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                confirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(confirmAndPayFragment, true);
            }
        });
    }

    private void loadDeliveryPaymentMethodFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryPaymentMethodFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadConfirmAndPayFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ConfirmAndPayFragment.newInstance())
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

    private void loadWalletFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, WalletFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCGiftCardFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, GiftCardFragment.newInstance())
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


}
