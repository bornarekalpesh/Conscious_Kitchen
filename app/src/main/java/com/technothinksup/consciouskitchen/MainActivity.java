package com.technothinksup.consciouskitchen;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.technothinksup.consciouskitchen.model.AddTestmonialResponse;
import com.technothinksup.consciouskitchen.model.AppImageResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();




        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<AppImageResponse> call = apiInterface.getAppImage();
        call.enqueue(new Callback<AppImageResponse>() {
            @Override
            public void onResponse(Call<AppImageResponse> call, Response<AppImageResponse> response) {
                if(response.body().getApp_image_id()==1){
                    Toast.makeText(MainActivity.this, "" + response.body().getApp_image_image(), LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AppImageResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" , LENGTH_SHORT).show();


            }
        });

        new Handler().postDelayed(
                () -> {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }, 3000);



  }
}