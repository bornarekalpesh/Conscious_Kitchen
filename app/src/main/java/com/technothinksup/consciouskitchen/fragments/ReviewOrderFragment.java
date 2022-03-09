package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CartAdapter;
import com.technothinksup.consciouskitchen.adapter.RewardAdapter;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.CartResponse;
import com.technothinksup.consciouskitchen.model.CouponCodeResponse;
import com.technothinksup.consciouskitchen.model.GetRewardList;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewOrderFragment extends Fragment {
    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};
    private RecyclerView rv_cartList, rv_reward_item;
    private View view;
    private CardView cardView_YourOrderContinueToPay;
    private ImageView imageView_BackReviewOrder, imageView_RewardReviewOrder, imageView_CartReviewOrder;
    private LinearLayout linearLayout_ReviewOrderMenuFirst, linearLayout_ReviewOrderMenuSecond;
    private TextView textView_ReviewOrderAddMoreMenu, textView_ReviewOrderAddRewards;
    private CartAdapter cartAdapter;
    private RewardAdapter rewardAdapter;
    private EditText et_coupon_code;
    private Button btn_apply;
    private TextView tv_sub_total, tv_tax, tv_total;

    private float total_amt;
    private int total_amt_coupon_code;

    public static ReviewOrderFragment newInstance() {
        return new ReviewOrderFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_review_order, container, false);
        init();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        setData();
        setRewardItemData();
        return view;
    }


    private void setData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CartResponse> call = apiInterface.getCartList(HomePageActivity.userId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                total_amt = Float.valueOf(response.body().getTotalAmount());
                total_amt_coupon_code = Math.round(total_amt);
                cartAdapter = new CartAdapter(getActivity(), response.body().getCartItemList(), response.body().getItemImagePath());
                rv_cartList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_cartList.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                rv_cartList.setHasFixedSize(true);
                tv_sub_total.setText(HomePageActivity.currency + response.body().getTotalBasicAmount());
                tv_tax.setText(HomePageActivity.currency + response.body().getTotalGstAmount());
                float total1 = Float.valueOf(response.body().getTotalBasicAmount()) + Float.valueOf(response.body().getTotalGstAmount());
                tv_total.setText(HomePageActivity.currency + total1);
                Log.e("total--", "" + total1);
            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error", "" + t.getMessage());
            }
        });
    }

    private void setRewardItemData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<GetRewardList> call = apiInterface.getRewardList();
        call.enqueue(new Callback<GetRewardList>() {
            @Override
            public void onResponse(Call<GetRewardList> call, Response<GetRewardList> response) {
                rewardAdapter = new RewardAdapter(getActivity(), response.body().getRewardList(), response.body().getCategoryImagePath(), response.body().getItemImagePath());
                rv_reward_item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_reward_item.setAdapter(rewardAdapter);
                rewardAdapter.notifyDataSetChanged();
                rv_reward_item.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<GetRewardList> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        StateProgressBar stateProgressBar = view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        rv_cartList = view.findViewById(R.id.rv_cartList);
        rv_reward_item = view.findViewById(R.id.rv_reward_item);

        cardView_YourOrderContinueToPay = view.findViewById(R.id.cardView_YourOrderContinueToPay);
        imageView_BackReviewOrder = view.findViewById(R.id.imageView_BackReviewOrder);
        imageView_RewardReviewOrder = view.findViewById(R.id.imageView_RewardReviewOrder);
        imageView_CartReviewOrder = view.findViewById(R.id.imageView_CartReviewOrder);
        linearLayout_ReviewOrderMenuFirst = view.findViewById(R.id.linearLayout_ReviewOrderMenuFirst);
        linearLayout_ReviewOrderMenuSecond = view.findViewById(R.id.linearLayout_ReviewOrderMenuSecond);
        textView_ReviewOrderAddMoreMenu = view.findViewById(R.id.textView_ReviewOrderAddMoreMenu);
        textView_ReviewOrderAddRewards = view.findViewById(R.id.textView_ReviewOrderAddRewards);
        et_coupon_code = view.findViewById(R.id.et_coupon_code);
        btn_apply = view.findViewById(R.id.btn_apply);
        tv_sub_total = view.findViewById(R.id.tv_sub_total);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_total = view.findViewById(R.id.tv_total);
    }

    private void listener() {
        imageView_BackReviewOrder.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardReviewOrder.setOnClickListener(view -> loadRewardProcessFragment());

        imageView_CartReviewOrder.setOnClickListener(view -> loadReviewOrderFragment());

        cardView_YourOrderContinueToPay.setOnClickListener(view -> {

            if (Common.order_type.equalsIgnoreCase("1")) {
                loadPaymentMethodFragment();
            } else if (Common.order_type.equalsIgnoreCase("2")) {
                ((HomePageActivity) getActivity()).loadFragment(new DeliveryPaymentMethodFragment(), true);
            } else if (Common.order_type.equalsIgnoreCase("3")) {
                ((HomePageActivity) getActivity()).loadFragment(new CurbsidePaymentMethodFragment(), true);
            } else if (Common.order_type.equalsIgnoreCase("0")) {
                ((HomePageActivity) getActivity()).loadFragment(new PaymentMethodFragment(), true);
            }
        });

        linearLayout_ReviewOrderMenuFirst.setOnClickListener(view -> loadMenuShowFragment());
        linearLayout_ReviewOrderMenuSecond.setOnClickListener(view -> loadMenuShowFragment());
        textView_ReviewOrderAddMoreMenu.setOnClickListener(view -> loadMenuShowFragment());
        textView_ReviewOrderAddRewards.setOnClickListener(view -> loadMyRewardFragment());

        btn_apply.setOnClickListener(v -> {
            ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
            Call<CouponCodeResponse> call = apiInterface.applyCouponCode(HomePageActivity.userId, et_coupon_code.getText().toString(), total_amt_coupon_code);
            call.enqueue(new Callback<CouponCodeResponse>() {
                @Override
                public void onResponse(Call<CouponCodeResponse> call, Response<CouponCodeResponse> response) {
                    Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<CouponCodeResponse> call, Throwable t) {
                }
            });
        });
    }

    private void loadPaymentMethodFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, PaymentMethodFragment.newInstance())
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

    private void loadMenuShowFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadMyRewardFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MyRewardFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}


