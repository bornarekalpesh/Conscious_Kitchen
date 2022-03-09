package com.technothinksup.consciouskitchen.fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class RewardProcessFragment extends Fragment {

    private TextView tv_reward_point,textView_RewardProcess;

    private View view;
    private CardView cardView_RewardProcessReferFriend;
    private CircularProgressBar circularProgressBar;

    private ImageView imageView_BackRewardProcess, imageView_RewardRewardProcess, imageView_CartRewardProcess;
    float chk = 0;
    private RelativeLayout relativeLayout_SelectContact;

    public static RewardProcessFragment newInstance() {

        return new RewardProcessFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reward_process, container, false);
      init();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();



        return view;
    }



    private void init() {

        tv_reward_point = view.findViewById(R.id.tv_reward_point);
        textView_RewardProcess= view.findViewById(R.id.textView_RewardProcess);
        tv_reward_point.setText("" + Math.round(HomePageActivity.reward_point));

        float reward_point=Math.round(HomePageActivity.reward_point);
       // float reward_point=(float)(HomePageActivity.reward_point);

        cardView_RewardProcessReferFriend = view.findViewById(R.id.cardView_RewardProcessReferFriend);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        imageView_BackRewardProcess = view.findViewById(R.id.imageView_BackRewardProcess);
        imageView_RewardRewardProcess =  view.findViewById(R.id.imageView_RewardRewardProcess);
        imageView_CartRewardProcess =  view.findViewById(R.id.imageView_CartRewardProcess);
        relativeLayout_SelectContact =  view.findViewById(R.id.relativeLayout_SelectContact);

     //   circularProgressBar.setProgress(0);
        circularProgressBar.setProgressWithAnimation(chk, 2000L); // =1s
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                // need to do tasks on the UI thread

                if(chk <=reward_point){
                    tv_reward_point.setText(""+Math.round(chk));
                    circularProgressBar.setProgressWithAnimation(chk, 2000L); // =1s

                }
                else {
                    tv_reward_point.setText(""+Math.round(reward_point));
                    circularProgressBar.setProgressWithAnimation(reward_point, 2000L); // =1s


                }



                Log.d(TAG, "Run test count: " + chk);
                chk = chk+100;
                if (chk<=800) {
                    handler.postDelayed(this, 1000);
                }


            }
        };

// trigger first time
        handler.post(runnable);
        circularProgressBar.setProgressMax(800);
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(0f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);




        if(reward_point == 0){
            textView_RewardProcess.setText("100 Points until your next reward");
        }
        else if(reward_point<100){
            textView_RewardProcess.setText(Math.round(100-reward_point)+" Points until your next reward");

        }
        else if(reward_point<200 && reward_point>100){
            textView_RewardProcess.setText(Math.round(200-reward_point)+" Points until your next reward");

        }
        else if(reward_point<300 && reward_point>200){
            textView_RewardProcess.setText(Math.round(300-reward_point)+" Points until your next reward");

        }
        else if(reward_point<400 && reward_point>300){
            textView_RewardProcess.setText(Math.round(400-reward_point)+" Points until your next reward");

        }
        else if(reward_point<600 && reward_point>400){
            textView_RewardProcess.setText(Math.round(600-reward_point)+" Points until your next reward");

        }
        else if(reward_point<800 && reward_point>600){
            textView_RewardProcess.setText(Math.round(800-reward_point)+" Points until your next reward");

        }


    }

    private void listener() {
        imageView_BackRewardProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardRewardProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartRewardProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        cardView_RewardProcessReferFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReferFriendFragment();
            }
        });





    }

    private void loadReferFriendFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReferFriendFragment.newInstance())
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
