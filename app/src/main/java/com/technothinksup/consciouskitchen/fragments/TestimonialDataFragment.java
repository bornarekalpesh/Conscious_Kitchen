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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.TesimonialAdapter;
import com.technothinksup.consciouskitchen.model.TestimonialList;
import com.technothinksup.consciouskitchen.model.TestimonialResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimonialDataFragment extends Fragment {
    private List<TestimonialList> testimonialLists;

    private RecyclerView rv_testmonial_list;

    private CardView cardView_AddPostTestimonial;

    private ImageView imageView_BackTestimonialsData, imageView_RewardTestimonialsData, imageView_CartTestimonialsData;

    private RelativeLayout relativeLayout_TestThree;

    private TextView textView_TestimonialDataSeeMore, textView_TestimonialDataSeeMoreArrow;

    private View view;

    private TesimonialAdapter tesimonialAdapter;

    public static TestimonialDataFragment newInstance() {

        return new TestimonialDataFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_testimonial_data, container, false);

        init();

        listener();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        setTestimonialData();


        return view;
    }


    private void init() {
        cardView_AddPostTestimonial = view.findViewById(R.id.cardView_AddPostTestimonial);
        imageView_BackTestimonialsData = view.findViewById(R.id.imageView_BackTestimonialsData);
        imageView_RewardTestimonialsData = view.findViewById(R.id.imageView_RewardTestimonialsData);
        imageView_CartTestimonialsData = view.findViewById(R.id.imageView_CartTestimonialsData);
        relativeLayout_TestThree = view.findViewById(R.id.relativeLayout_TestThree);
        textView_TestimonialDataSeeMore = view.findViewById(R.id.textView_TestimonialDataSeeMore);
        textView_TestimonialDataSeeMoreArrow = view.findViewById(R.id.textView_TestimonialDataSeeMoreArrow);
        rv_testmonial_list = view.findViewById(R.id.rv_testmonial_list);
    }

    private void listener() {

        imageView_BackTestimonialsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardTestimonialsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartTestimonialsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        cardView_AddPostTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTestimonialFragment();
            }
        });

        textView_TestimonialDataSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (relativeLayout_TestThree.getVisibility() == View.GONE) {
                    relativeLayout_TestThree.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout_TestThree.setVisibility(View.GONE);
                }*/
            }
        });

        textView_TestimonialDataSeeMoreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if (relativeLayout_TestThree.getVisibility() == View.GONE) {
                    relativeLayout_TestThree.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout_TestThree.setVisibility(View.GONE);
                }*/
            }
        });

    }

    private void loadTestimonialFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TestimonialFragment.newInstance())
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

    private void setTestimonialData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<TestimonialResponse> call = apiInterface.getTestmonialList();

        call.enqueue(new Callback<TestimonialResponse>() {
            @Override
            public void onResponse(Call<TestimonialResponse> call, Response<TestimonialResponse> response) {


                if (response.body().getStatus() == 1) {

                    try {


                        testimonialLists = response.body().getTestimonialList();


                        tesimonialAdapter = new TesimonialAdapter(getActivity(), response.body().getTestimonialList(), response.body().getTestimonialImagePath());
                        rv_testmonial_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        rv_testmonial_list.setAdapter(tesimonialAdapter);
                        tesimonialAdapter.notifyDataSetChanged();
                        rv_testmonial_list.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<TestimonialResponse> call, Throwable t) {

            }
        });
    }

}
