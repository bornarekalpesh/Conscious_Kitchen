package com.technothinksup.consciouskitchen;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.andreabaccega.widget.FormEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.CountryListModel;
import com.technothinksup.consciouskitchen.model.CustomerSignUpResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class SignUpActivity extends AppCompatActivity {
    ActionBar actionBar;
    AppCompatButton button_SignUp;
    AppCompatTextView textView_SignUpLogin, textView_SignUpAcceptData;
    AppCompatImageView imageView_SignUpBack;
    private FormEditText et_fName, et_lName, et_dob, et_mobile, et_email, et_password, et_referral_code;
    private AppCompatCheckBox checkBox_TextNotification, checkBox_EmailNotification, checkBox_PushNotification;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String cb_txt_msg_value = "1", cb_email_value = "1", cb_push_notification_value = "1";
    public static final String TAG = "MyTag";


    List<CountryListModel> countryModel = new ArrayList<>();
    ArrayList<String> countryNameList = new ArrayList<>();

    String strCodeSelected="";
    Spinner sspin;



    String fcm_token = "";



//    <com.toptoche.searchablespinnerlibrary.SearchableSpinner
//    android:id="@+id/sspin"
//    android:layout_width="@dimen/_65sdp"
//    android:layout_height="0dp"
//    android:layout_marginTop="@dimen/_5sdp"
//    android:layout_marginEnd="0dp"
//    android:backgroundTint="@color/white"
//    android:gravity="center_vertical"
//    android:paddingEnd="1dp"/>

    //searchablespinner
    //implementation 'com.toptoche.searchablespinner:searchablespinnerlibrary:1.3.1'


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        init();
        getCountryList();
        listener();
        setString();



        button_SignUp.setOnClickListener(view -> {
            if (et_fName.testValidity() && et_lName.testValidity() && et_dob.testValidity() && et_mobile.testValidity() && et_email.testValidity() && et_password.testValidity()) {
                if (isNetworkAvailable(SignUpActivity.this)) {
                    //call sign_up
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                        return;
                                    }

                                    fcm_token = task.getResult();
                                    sign_up(fcm_token);
                                }
                            });

                } else NetworkError(SignUpActivity.this);
            }


        });




    }

    private void getCountryList() {

        String jsonFileString = getJsonFromAssets(this, "countrylist.json");

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<CountryListModel>>() {
        }.getType();


        countryModel.clear();
        countryModel = gson.fromJson(jsonFileString, listUserType);

        for (int i = 0; i < countryModel.size(); i++) {
            countryNameList.add(countryModel.get(i).getDial_code());        }

       // sspin.setTitle("Select Country Code");

       /* ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_country, R.id.text, countryNameList);

        aAdapter.setDropDownViewResource(R.layout.spinner_layout_country);

        sspin.setAdapter(aAdapter);

        sspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCodeSelected = countryModel.get(position).getDial_code();
                //((TextView) parent.getChildAt(0)).setText(strCodeSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                R.layout.spinner_layout_country,
                countryNameList);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(R.layout
                        .spinner_layout_country);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        sspin.setAdapter(ad);

        sspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strCodeSelected = countryModel.get(i).getDial_code();
                ((TextView) view).setText("+1");
                ((TextView) view).setTextColor(Color.WHITE);
                Log.d("sscl : ", strCodeSelected);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void sign_up(String fcm_token) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody reqFName = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_fName.getText().toString());
        RequestBody reqLName = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_lName.getText().toString());
        RequestBody reqDob = RequestBody.create(MediaType.parse("multipart/form-data"), et_dob.getText().toString());
        RequestBody reqCountryCode = RequestBody.create(MediaType.parse("multipart/form-data"),sspin.getSelectedItem().toString());
        RequestBody reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), et_mobile.getText().toString());
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), et_email.getText().toString());
        RequestBody reqPass = RequestBody.create(MediaType.parse("multipart/form-data"), et_password.getText().toString());
        RequestBody reqTxtMsgNotification = RequestBody.create(MediaType.parse("multipart/form-data"), cb_txt_msg_value);
        RequestBody reqEmailNotification = RequestBody.create(MediaType.parse("multipart/form-data"), cb_email_value);
        RequestBody reqPushNotification = RequestBody.create(MediaType.parse("multipart/form-data"), cb_push_notification_value);
        RequestBody reqDevId = RequestBody.create(MediaType.parse("multipart/form-data"), fcm_token);
        Log.d ("fcm_token",fcm_token);
        RequestBody req_referral_code = RequestBody.create(MediaType.parse("multipart/form-data"), et_referral_code.getText().toString());

        Call<CustomerSignUpResponse> call = apiInterface.saveCustomer(reqFName, reqLName, reqDob,reqCountryCode, reqMobile, reqEmail, reqPass, reqTxtMsgNotification, reqEmailNotification, reqPushNotification, reqDevId, req_referral_code);

        call.enqueue(new Callback<CustomerSignUpResponse>() {
            @Override
            public void onResponse(Call<CustomerSignUpResponse> call, Response<CustomerSignUpResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus()==1) {
                        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("UserLogin", "UserLoginSuccessful");
                        editor.apply();
                        editor.commit();
                        Common.saveUserData(SignUpActivity.this, "customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        Common.saveUserData(SignUpActivity.this, "customer_image", response.body().getCustomerInfo().get(0).getCustomerImage());
                        Common.saveUserData(SignUpActivity.this, "fName", response.body().getCustomerInfo().get(0).getCustomerName());
                        Common.saveUserData(SignUpActivity.this, "lName", response.body().getCustomerInfo().get(0).getCustomerLname());
                        Common.saveUserData(SignUpActivity.this, "email", response.body().getCustomerInfo().get(0).getCustomerEmail());
                        Common.saveUserData(SignUpActivity.this, "mobile", response.body().getCustomerInfo().get(0).getCustomerMobile());
                        Common.saveUserData(SignUpActivity.this, "password", response.body().getCustomerInfo().get(0).getCustomerPassword());
                        Common.saveUserData(SignUpActivity.this, "dob", response.body().getCustomerInfo().get(0).getCustomer_dob());
                       // Common.saveUserData(SignUpActivity.this, "", response.body().getCustomerInfo().get(0).());


                        Toast.makeText(SignUpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, HomePageActivity.class);
                        i.putExtra("logintype", "o");
                        startActivity(i);
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(SignUpActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<CustomerSignUpResponse> call, Throwable e) {
                removeProgressBar(SignUpActivity.this);
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }


    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    private void showErrorLog(final String message) {
        runOnUiThread(() ->
        {
            removeProgressBar(SignUpActivity.this);
            onErrorSnack(this, message);
        });
    }


    private void setString() {
        String first = "By clicking on sign up you agree to the join the Conscious Kitchen, Reward Program, the ";
        String second = "<font color='#fff28e'><u>Terms and Conditions</u></font>";
        String third = " and ";
        String forth = "<font color='#fff28e'><u>Privacy Policy</u></font>";
        String five = " ,are 13 years of age also agree to get notifications about rewards, promotions, events and free stuff through.";

        String main = first + second + third + forth + five;
        SpannableString ss = new SpannableString(Html.fromHtml(main));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent i = new Intent(SignUpActivity.this, PageContentActivity.class);
                i.putExtra("page_content", 3);
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent i = new Intent(SignUpActivity.this, PageContentActivity.class);
                i.putExtra("page_content", 2);
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //   ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 89, 108, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan1, 114, 127, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
       textView_SignUpAcceptData.setText(ss);
        textView_SignUpAcceptData.setText(ss);

        textView_SignUpAcceptData.setMovementMethod(LinkMovementMethod.getInstance());
        textView_SignUpAcceptData.setHighlightColor(Color.TRANSPARENT);
    }

    private void listener() {
        textView_SignUpLogin.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
        imageView_SignUpBack.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
        et_dob.setOnClickListener(view -> selectDOB());
        checkBox_TextNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb_txt_msg_value = "1";
            } else {
                cb_txt_msg_value = "0";
            }
        });

        checkBox_EmailNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb_email_value = "1";
            } else {
                cb_email_value = "0";
            }
        });

        checkBox_PushNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cb_push_notification_value = "1";

            } else {
                cb_push_notification_value = "0";
            }
        });
    }

    private void init() {
        et_fName = findViewById(R.id.et_fname);
        et_lName = findViewById(R.id.et_lname);
        et_dob = findViewById(R.id.et_dob);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_referral_code = findViewById(R.id.et_referal_code);
        checkBox_TextNotification = findViewById(R.id.checkBox_TextNotification);
        checkBox_EmailNotification = findViewById(R.id.checkBox_EmailNotification);
        checkBox_PushNotification = findViewById(R.id.checkBox_PushNotification);
        button_SignUp = findViewById(R.id.button_SignUp);
        textView_SignUpLogin = findViewById(R.id.textView_SignUpLogin);
        imageView_SignUpBack = findViewById(R.id.imageView_SignUpBack);
        textView_SignUpAcceptData = findViewById(R.id.textView_SignUpAcceptData);

        sspin = findViewById(R.id.sspin);
    }

    private void selectDOB() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        @SuppressLint("SetTextI18n") DatePickerDialog picker = new DatePickerDialog(SignUpActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String sDay, sMonth;
                    monthOfYear = monthOfYear + 1;
                    if (dayOfMonth < 10) {
                        sDay = "0" + dayOfMonth;
                    } else {
                        sDay = String.valueOf(dayOfMonth);
                    }
                    if (monthOfYear < 10) {
                        sMonth = "0" + monthOfYear;
                    } else {
                        sMonth = String.valueOf(monthOfYear);
                    }
                    et_dob.setText(year1 + "-" + sMonth + "-" + sDay);

                }, year, month, day);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));
        picker.show();
    }
}