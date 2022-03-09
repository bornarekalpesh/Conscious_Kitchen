package com.technothinksup.consciouskitchen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textview.MaterialTextView;
import com.technothinksup.consciouskitchen.model.PageContentResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class PageContentActivity extends AppCompatActivity {

    ActionBar actionBar;
    private int page_content = 0;
    AppCompatImageView iv_Back;
    AppCompatTextView tv_Title;
    MaterialTextView mTv_Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_content);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        if(getIntent()!= null){
            if(getIntent().hasExtra("page_content")){
                page_content = getIntent().getIntExtra("page_content",0);
            }
        }
        bindData();
        setData();
    }

    private void bindData(){
        iv_Back = findViewById(R.id.iv_Back);
        tv_Title = findViewById(R.id.tv_Title);
        mTv_Content = findViewById(R.id.mTv_Content);
        iv_Back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setData(){
        if(page_content == 1){
            tv_Title.setText(R.string.about_us);
        }else if(page_content == 2){
            tv_Title.setText(R.string.privacy_policy);
        } else if(page_content == 3){
            tv_Title.setText(R.string.Terms_and_condition);
        } else if(page_content == 4){
            tv_Title.setText(R.string.Customer_Care);
        }

        if (isNetworkAvailable(this)) {
            //call get content
            getContent();
        } else NetworkError(this);
    }

    private void getContent(){
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<PageContentResponse> call=apiInterface.getPageContent(String.valueOf(page_content));
        call.enqueue(new Callback<PageContentResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<PageContentResponse> call, Response<PageContentResponse> response) {

                if (response.isSuccessful()){
                    String content=response.body().getPageContent().get(0).getPageContentDetails();

                    if (Build.VERSION.SDK_INT >= 24) {
                        mTv_Content.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)); // for 24 api and more
                    } else {
                        mTv_Content.setText( Html.fromHtml(content)); // or for older api
                    }
                    // tv_content.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    // error case
                    switch (response.code())
                    {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " "+response.code());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<PageContentResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException) showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException) showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void showErrorLog(final String message) {
        runOnUiThread(() ->
        {
            //hideProgressBar();
            onErrorSnack(this, message);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}