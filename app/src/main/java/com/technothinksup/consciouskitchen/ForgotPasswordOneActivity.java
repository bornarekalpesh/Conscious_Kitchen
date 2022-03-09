package com.technothinksup.consciouskitchen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.ForgotPasswordResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class ForgotPasswordOneActivity extends AppCompatActivity {
    ActionBar actionBar;
    AppCompatButton button_ForgotSendCode;
    AppCompatTextView textView_ForgotLogin, textView_ForgotReSendCode;
    AppCompatImageView imageView_ForgotPasswordOneBack;
    private FormEditText et_mobile, et_email;
    private RadioButton rb_email, rb_mobile_no;
    private String type = "", value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_one);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        init();
        listener();
    }

    private void init() {
        button_ForgotSendCode = findViewById(R.id.button_ForgotSendCode);
        textView_ForgotLogin = findViewById(R.id.textView_ForgotLogin);
        textView_ForgotReSendCode = findViewById(R.id.textView_ForgotReSendCode);
        imageView_ForgotPasswordOneBack = findViewById(R.id.imageView_ForgotPasswordOneBack);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        rb_email = findViewById(R.id.rb_email);
        rb_mobile_no = findViewById(R.id.rb_mobile_no);
    }

    private void listener() {
        button_ForgotSendCode.setOnClickListener(view -> {
            if (!type.equalsIgnoreCase("")) {
                if (type.equalsIgnoreCase("1")) {
                    value = et_mobile.getText().toString();
                    if (et_mobile.testValidity()) {
                        if (isNetworkAvailable(ForgotPasswordOneActivity.this)) {
                            getOTP(value);
                        } else NetworkError(ForgotPasswordOneActivity.this);
                    }
                } else if (type.equalsIgnoreCase("2")) {
                    value = et_email.getText().toString();
                    if (et_email.testValidity()) {
                        if (isNetworkAvailable(ForgotPasswordOneActivity.this)) {
                            getOTP(value);
                        } else NetworkError(ForgotPasswordOneActivity.this);
                    }
                }
            } else {
                Toast.makeText(ForgotPasswordOneActivity.this, "Please Select Email Or Phone !!", Toast.LENGTH_SHORT).show();
            }
        });

        rb_email.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (rb_email.isChecked()) {
                et_email.setVisibility(View.VISIBLE);
                et_mobile.setVisibility(View.GONE);
                type = "2";
            } else {
                type = "1";
                et_mobile.setVisibility(View.VISIBLE);
                et_email.setVisibility(View.GONE);
            }
        });

        rb_mobile_no.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (rb_mobile_no.isChecked()) {
                type = "1";
                et_mobile.setVisibility(View.VISIBLE);
                et_email.setVisibility(View.GONE);
            } else {
                et_email.setVisibility(View.VISIBLE);
                et_mobile.setVisibility(View.GONE);
                type = "2";
            }
        });

        textView_ForgotLogin.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordOneActivity.this, LoginActivity.class)));

        textView_ForgotReSendCode.setOnClickListener(view -> {
            if (!type.equalsIgnoreCase("")) {
                if (type.equalsIgnoreCase("1")) {
                    value = et_mobile.getText().toString();
                    if (et_mobile.testValidity()) {
                        if (isNetworkAvailable(ForgotPasswordOneActivity.this)) {
                            getOTP(value);
                        } else NetworkError(ForgotPasswordOneActivity.this);
                    }
                } else if (type.equalsIgnoreCase("2")) {
                    value = et_email.getText().toString();
                    if (et_email.testValidity()) {
                        if (isNetworkAvailable(ForgotPasswordOneActivity.this)) {
                            getOTP(value);
                        } else NetworkError(ForgotPasswordOneActivity.this);
                    }
                }
            } else {
                Toast.makeText(ForgotPasswordOneActivity.this, "Please Select Email Or Phone !!", Toast.LENGTH_SHORT).show();
            }
        });
        imageView_ForgotPasswordOneBack.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordOneActivity.this, LoginActivity.class)));
    }

    private void getOTP(String value1) {
        Common.showProgressBar(ForgotPasswordOneActivity.this);
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<ForgotPasswordResponse> call = apiInterface.forgotPassGetOtp(value1, type);
        call.enqueue(new Callback<ForgotPasswordResponse>() {

            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    removeProgressBar(ForgotPasswordOneActivity.this);
                    if (response.body().getStatus() == 1) {
                        Intent i = new Intent(ForgotPasswordOneActivity.this, ForgotPasswordTwoActivity.class);
                        i.putExtra("OTP", "" + response.body().getOtp());
                        i.putExtra("customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        Common.saveUserData(ForgotPasswordOneActivity.this, "user_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        startActivity(i);
                    } else {
                        Toast.makeText(ForgotPasswordOneActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void showErrorLog(final String message) {
        runOnUiThread(() ->
        {
            removeProgressBar(ForgotPasswordOneActivity.this);
            onErrorSnack(this, message);
        });
    }
}