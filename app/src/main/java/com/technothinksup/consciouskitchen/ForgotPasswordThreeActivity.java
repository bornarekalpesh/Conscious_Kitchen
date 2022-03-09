package com.technothinksup.consciouskitchen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.ChangePasswordResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class ForgotPasswordThreeActivity extends AppCompatActivity {
    private AppCompatTextView textView_ForgotThreeLogin;
    private AppCompatButton button_ForgotChangePassword;
    private AppCompatImageView imageView_ForgotPasswordThreeBack;
    private String customer_id;
    private FormEditText et_newPass, et_reenterpass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_three);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        init();

        Intent intent = getIntent();
        if (intent != null) {
            customer_id = intent.getStringExtra("customer_id");
        }

        listener();
    }

    private void init() {
        button_ForgotChangePassword = findViewById(R.id.button_ForgotChangePassword);
        textView_ForgotThreeLogin = findViewById(R.id.textView_ForgotThreeLogin);
        imageView_ForgotPasswordThreeBack = findViewById(R.id.imageView_ForgotPasswordThreeBack);
        et_newPass = findViewById(R.id.et_newPass);
        et_reenterpass = findViewById(R.id.et_reenterpass);
    }

    private void listener() {
        button_ForgotChangePassword.setOnClickListener(view -> {
            if (et_newPass.testValidity() && et_reenterpass.testValidity()) {
                if (et_newPass.getText().toString().equalsIgnoreCase(et_reenterpass.getText().toString())) {
                    if (isNetworkAvailable(ForgotPasswordThreeActivity.this)) {
                        changePassword();
                    } else NetworkError(ForgotPasswordThreeActivity.this);

                } else {
                    Toast.makeText(ForgotPasswordThreeActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView_ForgotThreeLogin.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordThreeActivity.this, LoginActivity.class)));
        imageView_ForgotPasswordThreeBack.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordThreeActivity.this, ForgotPasswordTwoActivity.class)));
    }

    private void changePassword() {
        Common.showProgressBar(ForgotPasswordThreeActivity.this);
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody reqCustomer_id = RequestBody.create(MediaType.parse("multipart/form-data"), "" + customer_id);
        RequestBody reqNewPass = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_reenterpass.getText().toString());
        Call<ChangePasswordResponse> call = apiInterface.changePassword(reqCustomer_id, reqNewPass);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful()) {
                    Common.removeProgressBar(ForgotPasswordThreeActivity.this);
                    if (response.body().getStatus() == 1) {
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("UserLogin", "UserLoginSuccessful");
                        editor.apply();
                        editor.commit();

                        Common.saveUserData(ForgotPasswordThreeActivity.this, "customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "fName", response.body().getCustomerInfo().get(0).getCustomerName());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "lName", response.body().getCustomerInfo().get(0).getCustomerLname());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "email", response.body().getCustomerInfo().get(0).getCustomerEmail());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "mobile", response.body().getCustomerInfo().get(0).getCustomerMobile());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "password", response.body().getCustomerInfo().get(0).getCustomerPassword());
                        Common.saveUserData(ForgotPasswordThreeActivity.this, "dob", response.body().getCustomerInfo().get(0).getCustomer_dob());

                        Intent i = new Intent(ForgotPasswordThreeActivity.this, LoginActivity.class);
                        i.putExtra("FromChangePasswordScreen", true);
                        startActivity(i);

                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(ForgotPasswordThreeActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ChangePasswordResponse> call, Throwable e) {
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
            removeProgressBar(ForgotPasswordThreeActivity.this);
            onErrorSnack(this, message);
        });
    }
}