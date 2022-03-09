package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.WriterException;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

public class RewardScanFragment extends Fragment {

    private View view;

    private ImageView idIVQrcode, imageView_BackRewardScan, imageView_RewardRewardScan, imageView_CartRewardScan;

    private CardView cardView_ScanRewardMobile;

    private RelativeLayout relativeLayout_ScanReward;

    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    public static RewardScanFragment newInstance() {

        return new RewardScanFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_rewardscan, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        init();
        listener();
        generateQRCode();
        return view;
    }

    private void generateQRCode() {
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(Common.ref_code, null, QRGContents.Type.TEXT, dimen);
        // getting our qrcode in the form of bitmap.
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        // the bitmap is set inside our image
        // view using .setimagebitmap method.
        idIVQrcode.setImageBitmap(bitmap);

    }


    private void init() {
        idIVQrcode = view.findViewById(R.id.idIVQrcode);

        imageView_BackRewardScan = view.findViewById(R.id.imageView_BackRewardScan);
        imageView_RewardRewardScan = view.findViewById(R.id.imageView_RewardRewardScan);
        imageView_CartRewardScan = view.findViewById(R.id.imageView_CartRewardScan);
        cardView_ScanRewardMobile = view.findViewById(R.id.cardView_ScanRewardMobile);

        relativeLayout_ScanReward = view.findViewById(R.id.relativeLayout_ScanReward);

    }

    private void listener() {


        imageView_BackRewardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager()!= null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardRewardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartRewardScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        relativeLayout_ScanReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });





        cardView_ScanRewardMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });
    }


    private void loadRewardProcessFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
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

}
