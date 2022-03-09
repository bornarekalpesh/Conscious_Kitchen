package com.technothinksup.consciouskitchen.fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.technothinksup.consciouskitchen.R;
import java.util.Objects;

import afu.org.checkerframework.checker.igj.qual.I;
import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;

public class TrackerFragment extends Fragment {
    private View view;
    ProgressBar vprogressbar;
    Button buttonStart;
    StackedHorizontalProgressBar stackedHorizontalProgressBar;
    LinearLayout ll_tracker;
    TextView textView_TrackerEdits;

    public static TrackerFragment newInstance() {
        return new TrackerFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tracker,container,false);
//        LinearLayout linearLayout_TrackDelivery = (LinearLayout)view.findViewById(R.id.linearLayout_TrackDelivery);
        buttonStart = view.findViewById(R.id.start);
        ll_tracker= view.findViewById(R.id.ll_tracker);
        textView_TrackerEdits = view.findViewById(R.id.textView_TrackerEdits);
        vprogressbar =view.findViewById(R.id.vprogressbar);
       /* LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        final ProgressBar pb = new ProgressBar(getActivity(), null, android.R.attr.progress);
        Drawable d = new ProgressDrawable();
        pb.setProgressDrawable(d);
        pb.setPadding(20, 20, 20, 0);
        ll.addView(pb);
        SeekBar.OnSeekBarChangeListener l = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newProgress = pb.getMax() * progress / seekBar.getMax();
                Log.d(TAG, "onProgressChanged " + newProgress);
                pb.setProgress(newProgress);
            }
        };
        ll_tracker.addView(ll);
*/
        stackedHorizontalProgressBar = view. findViewById(R.id.stackedhorizontalprogressbar);
        new asyncTaskUpdateProgress().execute();
        buttonStart.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                buttonStart.setClickable(false);
                new asyncTaskUpdateProgress().execute();
            }});
        //int primary_pts = 3;
        //int secondary_pts = 3;
        int max = 30;
        stackedHorizontalProgressBar.setMax(max);
        stackedHorizontalProgressBar.setProgress(12);
        stackedHorizontalProgressBar.setSecondaryProgress(15);

        stackedHorizontalProgressBar.setProgress(20);
        stackedHorizontalProgressBar.setSecondaryProgress(25);
        stackedHorizontalProgressBar.setProgress(30);

        ImageView imageView_BackTracker = view.findViewById(R.id.imageView_BackTracker);
        ImageView imageView_RewardTracker = view.findViewById(R.id.imageView_RewardTracker);
        ImageView imageView_CartTracker = view.findViewById(R.id.imageView_CartTracker);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackTracker.setOnClickListener(view1 -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardTracker.setOnClickListener(view12 -> loadRewardProcessFragment());
        imageView_CartTracker.setOnClickListener(view13 -> loadReviewOrderFragment());

        textView_TrackerEdits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             lodeDeliveryAddressFragment();
            }

            private void lodeDeliveryAddressFragment() {

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryAddressFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });

//
//        linearLayout_TrackDelivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadTrackDeliveryFragment();
//            }
//        });
        return view;
    }




    private void loadTrackDeliveryFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TrackDeliveryFragment.newInstance())
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

    public class asyncTaskUpdateProgress extends AsyncTask<Void, Integer, Void> {
        int progress;
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            buttonStart.setClickable(true);
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progress = 0;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            vprogressbar.setProgress(values[0]);
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            while(progress<100){
                progress++;
                publishProgress(progress);
                SystemClock.sleep(2000);
            }
            return null;
        }
    }
}