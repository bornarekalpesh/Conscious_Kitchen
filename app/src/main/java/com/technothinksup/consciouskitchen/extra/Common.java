package com.technothinksup.consciouskitchen.extra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.technothinksup.consciouskitchen.model.GetCustomerProfileResponse;
import com.technothinksup.consciouskitchen.model.PaymentResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Common {
    public static final String SHARED_PREF = "userData";
    public static String upsell_category_id, latitude = "111", longitude = "111", defaultBranchId="", ref_code, order_type = "0";
    private static ProgressDialog progressBar;

    public static void saveUserData(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSavedUserData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(key, "");
    }

    public static void showProgressBar(Context context) {
        progressBar = new ProgressDialog(context);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait..");
        progressBar.show();
    }

    public static void removeProgressBar(Context context) {
        if (progressBar != null)
            progressBar.dismiss();
    }

    public static void makePayment(Context context, String name, String email, String card_number, String card_expire_month, String card_expire_year, String cvv, String amount) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<PaymentResponse> call = apiInterface.makePayment(name, email, card_number, card_expire_month, card_expire_year, cvv, amount);

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {

            }
        });
    }

    public static void getProfileData(Context context, String userId) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<GetCustomerProfileResponse> call = apiInterface.getProfileDetail(userId);
        call.enqueue(new Callback<GetCustomerProfileResponse>() {
            @Override
            public void onResponse(Call<GetCustomerProfileResponse> call, Response<GetCustomerProfileResponse> response) {
                //  Toast.makeText(context, "hii"+response.body().getProfileDetails().get(0).getCustomerEmail(), Toast.LENGTH_SHORT).show();


                GetCustomerProfileResponse  ressp = response.body();

                if (ressp!=null){

                    if (ressp.getStatus()==1){
                        defaultBranchId =ressp.getProfileDetails().get(0).getDefaultBranchId();
                        ref_code = ressp.getProfileDetails().get(0).getCustomerRefCode();
                    }else {
                        Toast.makeText(context, "" + ressp.getMessage(), Toast.LENGTH_SHORT).show();

                    }


                }






                // Toast.makeText(context, ""+ref_code, Toast.LENGTH_SHORT).show();
                //defaultBranchId="0";


            }

            @Override
            public void onFailure(Call<GetCustomerProfileResponse> call, Throwable t) {

            }
        });
    }

}
