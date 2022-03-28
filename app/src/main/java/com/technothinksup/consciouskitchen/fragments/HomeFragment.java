package com.technothinksup.consciouskitchen.fragments;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technothinksup.consciouskitchen.BarcodeScanActivity;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.RewardMildStoneAdapter;
import com.technothinksup.consciouskitchen.model.BranchResponse;
import com.technothinksup.consciouskitchen.model.GetCustomerBalance;
import com.technothinksup.consciouskitchen.model.GetPossibleRewardResponse;
import com.technothinksup.consciouskitchen.model.RewardResponse;
import com.technothinksup.consciouskitchen.model.TestimonialList;
import com.technothinksup.consciouskitchen.model.TestimonialResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.warkiz.tickseekbar.TickMarkType;
import com.warkiz.widget.IndicatorSeekBar;
import com.xw.repo.BubbleSeekBar;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class HomeFragment extends Fragment {
    public ActionBarDrawerToggle drawerToggle;
    DrawerLayout mDrawer;
    Toolbar toolbar;
    private View view;
    private CardView cardView_Order, cardView_Location, cardView_QR, cardView_Scan, cardView_Testimonial;
    private RelativeLayout relativeLayout_MyReward, relativeLayout_RewardMilestone, relativeLayout_Call, relativeLayout_Tesimmonial;
    private TextView textView_RewardOption,textView_Rewardbutton, textView_MyREwardHome, tv_testmonial_uname, tv_testmonial_desc, textView_UserAddress, tv_branch_name,textView_UserDate, tv_address, tv_possible_count,textView_AddressTime1,textView_AddressTime;
    private ImageView iv_testimonial_uimage;
    private List<TestimonialList> testimonialLists;
    private String branchContactNo;
    private RecyclerView rv_rewardMildStone;
    ScrollView rl_seekBar;
    private RewardMildStoneAdapter rewardMildStoneAdapter;
    public BubbleSeekBar seek_bar;
    public IndicatorSeekBar seek_bar2;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
         setSeekBar2(0);
        seek_bar.setOnTouchListener((v, event) -> true);
        if (isNetworkAvailable(getActivity())) {
            setTestimonialData();
            setBranchData();
            getCustomerBalance();
            setPossiblePoint();

        } else NetworkError(getActivity());

        listeners();
        rl_seekBar.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            seek_bar.correctOffsetWhenContainerOnScrolling();
            View view = (View) rl_seekBar.getChildAt(rl_seekBar.getChildCount() - 1);
            int diff = (view.getBottom() - (rl_seekBar.getHeight() + rl_seekBar.getScrollY()));
            // if diff is zero, then the bottom has been reached

            if (diff == 0) {
                seek_bar.getConfigBuilder()
                        .alwaysShowBubble();

                // do stuff
            } else {
                seek_bar.getConfigBuilder().hideBubble();
            }
        });

        return view;
    }

    private void showErrorLog(final String message) {
        getActivity().runOnUiThread(() ->
        {
            removeProgressBar(getActivity());
            onErrorSnack(getActivity(), message);
        });
    }

    private void listeners() {
        seek_bar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }
        });

        mDrawer = ((HomePageActivity) Objects.requireNonNull(getActivity())).mDrawer;
        toolbar = ((HomePageActivity) getActivity()).toolbar;

        drawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawer, toolbar, R.string.str_open_drawer, R.string.str_close_drawer) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //  seek_bar.getConfigBuilder().alwaysShowBubble();
                seek_bar.setVisibility(View.VISIBLE);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //seek_bar.getConfigBuilder().hideBubble();
                seek_bar.setVisibility(View.GONE);
            }
        };
        mDrawer.addDrawerListener(drawerToggle);


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        textView_RewardOption.setOnClickListener(view -> {
            if (relativeLayout_RewardMilestone.getVisibility() == View.GONE) {
                relativeLayout_RewardMilestone.setVisibility(View.VISIBLE);
                relativeLayout_MyReward.setVisibility(View.GONE);
                setRewardData();
            } else {
                relativeLayout_RewardMilestone.setVisibility(View.GONE);
                relativeLayout_MyReward.setVisibility(View.VISIBLE);
            }
        });

        textView_Rewardbutton.setOnClickListener(view -> {
            if (relativeLayout_RewardMilestone.getVisibility() == View.GONE) {
                relativeLayout_RewardMilestone.setVisibility(View.VISIBLE);
                relativeLayout_MyReward.setVisibility(View.GONE);
                setRewardData();
            } else {
                relativeLayout_RewardMilestone.setVisibility(View.GONE);
                relativeLayout_MyReward.setVisibility(View.VISIBLE);
            }
        });




        relativeLayout_Call.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.setData(Uri.parse("tel:" + branchContactNo));
            Objects.requireNonNull(getActivity()).startActivity(callIntent);
        });

        relativeLayout_Tesimmonial.setOnClickListener(view -> loadTestimonialFragment());

        cardView_Testimonial.setOnClickListener(view -> loadTestimonialDataFragment());

        cardView_Order.setOnClickListener(view -> {
            loadRestaurantLocationFragment();
        });

        cardView_Location.setOnClickListener(view -> {
            loadRestaurantLocationFragment();
        });

        cardView_QR.setOnClickListener(view -> loadRewardScanFragment());
        cardView_Scan.setOnClickListener(view -> {
            // loadRewardScanCameraFragment();
            startActivity(new Intent(getActivity(), BarcodeScanActivity.class));
        });

        relativeLayout_MyReward.setOnClickListener(view -> loadMyRewardFragment());

        textView_MyREwardHome.setOnClickListener(view -> loadMyRewardFragment());
    }

    public void init() {
        cardView_Order = view.findViewById(R.id.cardView_Order);
        cardView_Location = view.findViewById(R.id.cardView_Location);
        cardView_QR = view.findViewById(R.id.cardView_QR);
        cardView_Scan = view.findViewById(R.id.cardView_Scan);
        cardView_Testimonial = view.findViewById(R.id.cardView_Testimonial);
        rl_seekBar = view.findViewById(R.id.rl_scroll);
        relativeLayout_MyReward = view.findViewById(R.id.relativeLayout_MyReward);
        relativeLayout_RewardMilestone = view.findViewById(R.id.relativeLayout_RewardMilestone);
        relativeLayout_Call = view.findViewById(R.id.relativeLayout_Call);
        relativeLayout_Tesimmonial = view.findViewById(R.id.relativeLayout_Tesimmonial);
        textView_RewardOption = view.findViewById(R.id.textView_RewardOption);
        textView_MyREwardHome = view.findViewById(R.id.textView_MyREwardHome);
        tv_testmonial_uname = view.findViewById(R.id.tv_testmonial_uname);
        tv_testmonial_desc = view.findViewById(R.id.tv_testmonial_desc);
        textView_UserAddress = view.findViewById(R.id.textView_UserAddress);
        iv_testimonial_uimage = view.findViewById(R.id.iv_testimonial_uimage);
        tv_branch_name = view.findViewById(R.id.tv_branch_name);
        textView_UserDate = view.findViewById(R.id.textView_UserDate);
        tv_address = view.findViewById(R.id.tv_address);
        textView_AddressTime = view.findViewById(R.id.textView_AddressTime);
        textView_AddressTime1 = view.findViewById(R.id.textView_AddressTime1);
        textView_Rewardbutton =view.findViewById(R.id.textView_Rewardbutton);
        rv_rewardMildStone = view.findViewById(R.id.rv_rewardMildStone);
        seek_bar = view.findViewById(R.id.seek_bar);
        // seek_bar2 = view.findViewById(R.id.seekbar2);
        tv_possible_count = view.findViewById(R.id.tv_possible_count);
    }

    private void getCustomerBalance() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<GetCustomerBalance> call = apiInterface.getCustomerBalance(HomePageActivity.userId);
        call.enqueue(new Callback<GetCustomerBalance>() {
            @Override
            public void onResponse(Call<GetCustomerBalance> call, Response<GetCustomerBalance> response) {
                if (response.isSuccessful()) {
                    HomePageActivity.reward_point = response.body().getRewardBalance();
                    HomePageActivity.wallet_balance = response.body().getWalletBalance();
                    textView_MyREwardHome.setText("" + Math.round(response.body().getRewardBalance()) + " Rewards");
                    double reward = response.body().getRewardBalance();
                    seek_bar.setProgress((float) (reward));
                    //seek_bar2.setProgress((float) (reward));
                    // seek_bar.getConfigBuilder().sectionCount((int)(reward));
                } else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<GetCustomerBalance> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void setTestimonialData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<TestimonialResponse> call = apiInterface.getTestmonialList();
        call.enqueue(new Callback<TestimonialResponse>() {
            @Override
            public void onResponse(Call<TestimonialResponse> call, Response<TestimonialResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        try {
                            testimonialLists = response.body().getTestimonialList();
                            String testImagePath = response.body().getTestimonialImagePath() + testimonialLists.get(0).getTestimonialImage();
                            Picasso.with(getActivity())
                                    .load(testImagePath)
                                    .into(iv_testimonial_uimage);
                            tv_testmonial_uname.setText("Name: " + testimonialLists.get(0).getTestimonialCustName());
                            textView_UserAddress.setText("Address:" + testimonialLists.get(0).getTestimonialCustAddress());
                           // textView_UserDate.setText("" + testimonialLists.get(0).getTestimonialStatus());
                            tv_testmonial_desc.setText("Comment: " + HtmlCompat.fromHtml(testimonialLists.get(0).getTestimonialDetails(), 0));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<TestimonialResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void setBranchData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody reqLatitude = RequestBody.create(MediaType.parse("multipart/form-data"), "111");
        RequestBody reqLongitude = RequestBody.create(MediaType.parse("multipart/form-data"), "222");
        Call<BranchResponse> call = apiInterface.getBranchList(reqLatitude, reqLongitude);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                if (response.isSuccessful()) {
                    tv_branch_name.setText(response.body().getBranchList().get(0).getBranchName());
                    tv_address.setText(response.body().getBranchList().get(0).getBranchAddress());
                   textView_AddressTime.setText(response.body().getBranch_timing().get(0).getFrom_time());
                   textView_AddressTime1.setText(response.body().getBranch_timing().get(0).getTo_time());


                    branchContactNo = response.body().getBranchList().get(0).getBranchMobile();
                } else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<BranchResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void setRewardData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<RewardResponse> call = apiInterface.getRewardPointList();
        call.enqueue(new Callback<RewardResponse>() {
            @Override
            public void onResponse(Call<RewardResponse> call, Response<RewardResponse> response) {
                if (response.body().getStatus() == 1) {
                    try {
                        rewardMildStoneAdapter = new RewardMildStoneAdapter(getActivity(), response.body().getRewardList());
                        rv_rewardMildStone.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        rv_rewardMildStone.setAdapter(rewardMildStoneAdapter);
                        rewardMildStoneAdapter.notifyDataSetChanged();
                        rv_rewardMildStone.setHasFixedSize(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<RewardResponse> call, Throwable t) {
            }
        });
    }

    private void setPossiblePoint() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<GetPossibleRewardResponse> call = apiInterface.possibleRewardList(HomePageActivity.userId);
        call.enqueue(new Callback<GetPossibleRewardResponse>() {
            @Override
            public void onResponse(Call<GetPossibleRewardResponse> call, Response<GetPossibleRewardResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        tv_possible_count.setText("" + response.body().getRewardCount());
                    } else {
                    }
                } else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<GetPossibleRewardResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void loadRestaurantLocationFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RestaurantLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadTestimonialFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TestimonialFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadTestimonialDataFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TestimonialDataFragment.newInstance())
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

    private void loadRewardScanFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardScanFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /*private void loadConfirmAndPayFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ConfirmAndPayFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }*/

    /*private void loadRewardScanCameraFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardScanCameraFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }*/

    public void showHideBubble(boolean drawerState) {
        if (drawerState) {
            if(seek_bar.getConfigBuilder().isAlwaysShowBubble())
            seek_bar.getConfigBuilder().hideBubble();
        } else
            if(seek_bar.getConfigBuilder().isHideBubble())
            seek_bar.getConfigBuilder().alwaysShowBubble();
    }

    private void setSeekBar2(int progress) {
        seek_bar.getConfigBuilder()
                .min(0)
                .max(800)
                .progress(progress)
                .build();
        //.sectionCount(8)
        //    .trackColor(ContextCompat.getColor(getContext(), R.color.colorRed))
        // // .secondTrackColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
          //.thumbColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
         //showSectionText()
        //  .sectionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
        //  .sectionTextSize(12)
        //   .showThumbText()
        //  .thumbTextColor(ContextCompat.getColor(getContext(), R.color.black))
        //  .thumbTextSize(8)
        //  .bubbleColor(ContextCompat.getColor(getContext(), R.color.colorRed))
        //  .bubbleTextSize(8)
        //  .showSectionMark()
        //  .seekBySection()
        //  .autoAdjustSectionMark()
        //  .animDuration(1000)
        //  .seekStepSection()
        //  .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)

    }

}
