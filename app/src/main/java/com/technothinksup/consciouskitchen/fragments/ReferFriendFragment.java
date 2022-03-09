package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.ReferralAcceptAdapter;
import com.technothinksup.consciouskitchen.adapter.ReferralPendingAdapter;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.GetRefResponseList;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferFriendFragment extends Fragment {

    private RelativeLayout relativeLayout_SelectContact,relative_sendinvite;

    private View view;

    private ImageView imageView_BackReferFriends, imageView_RewardReferFriends, imageView_CartReferFriends;
    private TextView tv_ref_code;

    private RecyclerView rv_pending_list, rv_referral_accepted_list;
    private ReferralPendingAdapter referralPendingAdapter;
    private ReferralAcceptAdapter referralAcceptAdapter;

    private TextView tv_pending_count, tv_pending_amt;

    public static ReferFriendFragment newInstance() {
        return new ReferFriendFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refer_friend, container, false);

        init();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();

        setData();


        return view;
    }

    private void setData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetRefResponseList> call = apiInterface.getReferResponseList(HomePageActivity.userId);
        call.enqueue(new Callback<GetRefResponseList>() {
            @Override
            public void onResponse(Call<GetRefResponseList> call, Response<GetRefResponseList> response) {

                if(response.body().getStatus()==1) {
                    referralPendingAdapter = new ReferralPendingAdapter(getActivity(), response.body().getPendingReferralList());
                    rv_pending_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_pending_list.setAdapter(referralPendingAdapter);
                    referralPendingAdapter.notifyDataSetChanged();
                    rv_pending_list.setHasFixedSize(true);


                    referralAcceptAdapter = new ReferralAcceptAdapter(getActivity(), response.body().getAcceptedReferralList());
                    rv_referral_accepted_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_referral_accepted_list.setAdapter(referralAcceptAdapter);
                    referralAcceptAdapter.notifyDataSetChanged();
                    rv_referral_accepted_list.setHasFixedSize(true);
                }
                try {
                    tv_pending_count.setText("" + response.body().getTotPendingCount());
                    tv_pending_amt.setText("" + response.body().getTotPendingAmt());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<GetRefResponseList> call, Throwable t) {

            }
        });

    }

    private void listener() {

        imageView_BackReferFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardReferFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartReferFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        relativeLayout_SelectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageActivity) getActivity()).loadFragment(new ContactListFragment(), true);
            }
        });


        relative_sendinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomePageActivity) getActivity()).loadFragment(new ContactListFragment(), true);

            }
        });
    }

    private void init() {
        imageView_BackReferFriends = view.findViewById(R.id.imageView_BackReferFriends);
        imageView_RewardReferFriends = view.findViewById(R.id.imageView_RewardReferFriends);
        imageView_CartReferFriends = view.findViewById(R.id.imageView_CartReferFriends);
        relative_sendinvite = view.findViewById(R.id.relative_sendinvite);
        relativeLayout_SelectContact = view.findViewById(R.id.relativeLayout_SelectContact);
        tv_ref_code = view.findViewById(R.id.tv_ref_code);
        rv_pending_list = view.findViewById(R.id.rv_pending_list);
        rv_referral_accepted_list = view.findViewById(R.id.rv_referral_accepted_list);
        tv_pending_count = view.findViewById(R.id.tv_pending_count);
        tv_pending_amt = view.findViewById(R.id.tv_pending_amt);


        tv_ref_code.setText("" + Common.ref_code);

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
