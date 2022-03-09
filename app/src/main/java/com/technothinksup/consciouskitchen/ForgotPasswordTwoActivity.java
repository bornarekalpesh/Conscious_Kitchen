package com.technothinksup.consciouskitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.extra.Common;

public class ForgotPasswordTwoActivity extends AppCompatActivity {
    private AppCompatButton button_ForgotVerify;
    private AppCompatTextView textView_ForgotTwoLogin;
    private AppCompatImageView imageView_ForgotPasswordTwoBack;
    private String otp = "",customer_id = "";
    private FormEditText et_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_two);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Intent intent = getIntent();
        if(intent != null){
            otp = intent.getStringExtra("OTP");
            customer_id = intent.getStringExtra("customer_id");
        }

        init();
        listener();
    }

    private void listener() {
        button_ForgotVerify.setOnClickListener(view -> {
            if (et_otp.testValidity()) {
                if ((otp.equalsIgnoreCase(et_otp.getText().toString()))) {
                    Common.showProgressBar(ForgotPasswordTwoActivity.this);
                    Intent i = new Intent(ForgotPasswordTwoActivity.this,ForgotPasswordThreeActivity.class);
                    i.putExtra("customer_id",""+customer_id);
                    startActivity(i);
                    Common.removeProgressBar(ForgotPasswordTwoActivity.this);
                } else {
                    Common.removeProgressBar(ForgotPasswordTwoActivity.this);
                    Toast.makeText(ForgotPasswordTwoActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView_ForgotTwoLogin.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordTwoActivity.this, LoginActivity.class)));
        imageView_ForgotPasswordTwoBack.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordTwoActivity.this, ForgotPasswordOneActivity.class)));
    }

    private void init() {
        button_ForgotVerify = findViewById(R.id.button_ForgotVerify);
        textView_ForgotTwoLogin = findViewById(R.id.textView_ForgotTwoLogin);
        imageView_ForgotPasswordTwoBack = findViewById(R.id.imageView_ForgotPasswordTwoBack);
        et_otp = findViewById(R.id.et_otp);
        //et_otp.setText(otp);
    }
}