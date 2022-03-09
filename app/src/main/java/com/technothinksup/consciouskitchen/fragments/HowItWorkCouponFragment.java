package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.technothinksup.consciouskitchen.R;


public class HowItWorkCouponFragment extends Fragment {

    private View view;
    private ImageView imageView_howitwork;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_how_it_work_coupon, container, false);

        WebView mWebView = null;
        mWebView = (WebView) view.findViewById(R.id.webview);
        imageView_howitwork = view.findViewById(R.id.imageView_howitwork);
        mWebView.loadUrl("file:///android_asset/couponsandreward.html"); //new.html is html file name.
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        return view;
    }



}