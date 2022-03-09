package com.technothinksup.consciouskitchen.extra;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.technothinksup.consciouskitchen.R;

public class Helper {

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void NetworkError(Activity activity) {
        //Snackbar.make(activity.findViewById(android.R.id.content), "Network is not Available", Snackbar.LENGTH_LONG).show();

        Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), "Network not available!", Snackbar.LENGTH_LONG);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snack.getView().getLayoutParams();
        params.setMargins(15, 10, 15, 20);
        snack.show();

    }

    public static void onErrorSnack(Activity activity, String errorMsg) {
        //Snackbar.make(activity.findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG).show();
        Snackbar snack = Snackbar.make(activity.findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.color_claim_now_red));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snack.getView().getLayoutParams();
        params.setMargins(15, 10, 15, 30);
        snack.show();

    }
}
