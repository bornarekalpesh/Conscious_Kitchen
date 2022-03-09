package com.technothinksup.consciouskitchen.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andreabaccega.widget.FormEditText;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.LoginActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.GetCustomerProfileResponse;
import com.technothinksup.consciouskitchen.model.UpdateProfileResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Common.showProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;


public class EditProfileFragment extends Fragment {

    private View view;
    //ActionBar actionBar;
    AppCompatButton button_editProfile;
    AppCompatTextView textView_SignUpLogin, textView_SignUpAcceptData;
    AppCompatImageView imageView_SignUpBack;
    private FormEditText et_fname, et_lname, et_dob, et_mobile, et_email, et_password;
    private ImageView iv_profile,imageView_RewardRestaurantLocation,imageView_CartRestaurantLocation;
    private AppCompatCheckBox checkBox_TextNotification, checkBox_EmailNotification, checkBox_PushNotification;
    private MultipartBody.Part fileProfileImage;
    private String cb_txt_msg_value = "1", cb_email_value = "1", cb_push_noti_value = "1", profile_image_path = "";
    private Intent galleryIntent;
    private String documentType;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();

        if (isNetworkAvailable(getActivity())) {
            getProfileData();
        } else NetworkError(getActivity());
        listener();
        imageBackListener();
        return view;
    }

    private void showErrorLog(final String message) {
        getActivity().runOnUiThread(() ->
        {
            removeProgressBar(getActivity());
            onErrorSnack(getActivity(), message);
        });
    }

    public void getProfileData() {
        Common.showProgressBar(getActivity());
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<GetCustomerProfileResponse> call = apiInterface.getProfileDetail(HomePageActivity.userId);
        call.enqueue(new Callback<GetCustomerProfileResponse>() {
            @Override
            public void onResponse(Call<GetCustomerProfileResponse> call, Response<GetCustomerProfileResponse> response) {
                // defaultBranchId=response.body().getProfileDetails().get(0).getDefaultBranchId();
                if (response.isSuccessful()) {
                    removeProgressBar(getActivity());
                    if (response.body().getStatus() == 1) {
                        et_fname.setText(response.body().getProfileDetails().get(0).getCustomerName());
                        et_lname.setText(response.body().getProfileDetails().get(0).getCustomerLname());
                        et_dob.setText(response.body().getProfileDetails().get(0).getCustomerDob());
                        et_mobile.setText(response.body().getProfileDetails().get(0).getCustomerMobile());
                        et_email.setText(response.body().getProfileDetails().get(0).getCustomerEmail());
                        et_password.setText(response.body().getProfileDetails().get(0).getCustomerPassword());
                        Glide.with(getActivity()).asBitmap().load(response.body().getImgUrl() + "/" + response.body().getProfileDetails().get(0).getCustomerImage()).error(R.drawable.profile).into(iv_profile);
                        Common.saveUserData(getActivity(), "image_path", response.body().getImgUrl() + "/");
                        Common.saveUserData(getActivity(), "customer_image", response.body().getProfileDetails().get(0).getCustomerImage());
                        Common.saveUserData(getActivity(), "customer_id", response.body().getProfileDetails().get(0).getCustomerId());
                        Common.saveUserData(getActivity(), "fName", response.body().getProfileDetails().get(0).getCustomerName());
                        Common.saveUserData(getActivity(), "lName", response.body().getProfileDetails().get(0).getCustomerLname());
                        Common.saveUserData(getActivity(), "email", response.body().getProfileDetails().get(0).getCustomerEmail());
                        Common.saveUserData(getActivity(), "mobile", response.body().getProfileDetails().get(0).getCustomerMobile());
                        Common.saveUserData(getActivity(), "password", response.body().getProfileDetails().get(0).getCustomerPassword());
                        Common.saveUserData(getActivity(), "dob", response.body().getProfileDetails().get(0).getCustomerDob());
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
            public void onFailure(Call<GetCustomerProfileResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void init() {
        et_fname = view.findViewById(R.id.et_fname);
        et_lname = view.findViewById(R.id.et_lname);
        et_dob = view.findViewById(R.id.et_dob);
        et_mobile = view.findViewById(R.id.et_mobile);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        iv_profile = view.findViewById(R.id.iv_profile);

        checkBox_TextNotification = view.findViewById(R.id.checkBox_TextNotification);
        checkBox_EmailNotification = view.findViewById(R.id.checkBox_EmailNotification);
        checkBox_PushNotification = view.findViewById(R.id.checkBox_PushNotification);
        imageView_RewardRestaurantLocation = view.findViewById(R.id.imageView_RewardRestaurantLocation);
        imageView_CartRestaurantLocation = view.findViewById(R.id.imageView_CartRestaurantLocation);

        button_editProfile = view.findViewById(R.id.button_editProfile);

        textView_SignUpLogin = view.findViewById(R.id.textView_SignUpLogin);
        imageView_SignUpBack = view.findViewById(R.id.imageView_SignUpBack);
        textView_SignUpAcceptData = view.findViewById(R.id.textView_SignUpAcceptData);
    }

    private void selectDOB() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);

        int year = cldr.get(Calendar.YEAR);

        // date picker dialog
        //sbirthdate = (String.valueOf(year) + "-" + smonth + "-" + sday);
        // age.setText(String.valueOf(iage));
        @SuppressLint("SetTextI18n") DatePickerDialog picker = new DatePickerDialog(getActivity(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String sday, smonth;
                    monthOfYear = monthOfYear + 1;
                    if (dayOfMonth < 10) {
                        sday = "0" + dayOfMonth;
                    } else {
                        sday = String.valueOf(dayOfMonth);
                    }
                    if (monthOfYear < 10) {
                        smonth = "0" + monthOfYear;
                    } else {
                        smonth = String.valueOf(monthOfYear);
                    }
                    //sbirthdate = (String.valueOf(year) + "-" + smonth + "-" + sday);
                    et_dob.setText(year1 + "-" + smonth + "-" + sday);
                    //int cyear = Calendar.getInstance().get(Calendar.YEAR);

                    // age.setText(String.valueOf(iage));

                }, year, month, day);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));
        picker.show();
    }

    private void listener() {

        iv_profile.setOnClickListener(v -> {
            documentType = "paymentFile";
            requestPermission();
        });


        imageView_RewardRestaurantLocation.setOnClickListener(view -> loadRewardProcessFragment());
        imageView_CartRestaurantLocation.setOnClickListener(view -> loadReviewOrderFragment());


        et_dob.setOnClickListener(view -> selectDOB());

        checkBox_TextNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                cb_txt_msg_value = "1";
            } else {
                cb_txt_msg_value = "0";
            }
            // Toast.makeText(SignUpActivity.this, ""+isChecked, Toast.LENGTH_SHORT).show();
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
                cb_push_noti_value = "1";
            } else {
                cb_push_noti_value = "0";
            }
        });


        button_editProfile.setOnClickListener(v -> {
            if (et_fname.testValidity() && et_lname.testValidity() && et_dob.testValidity() && et_mobile.testValidity() && et_email.testValidity() && et_password.testValidity()) {
                if (isNetworkAvailable(getActivity())) {
                    editProfile();
                } else NetworkError(getActivity());
            }
        });
    }

    private void loadReviewOrderFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardProcessFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();

    }

    private void editProfile() {
        showProgressBar(getActivity());
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody reqCustId = RequestBody.create(MediaType.parse("multipart/form-data"), "" + HomePageActivity.userId);
        RequestBody reqFname = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_fname.getText().toString());
        RequestBody reqLname = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_lname.getText().toString());
        RequestBody reqDob = RequestBody.create(MediaType.parse("multipart/form-data"), et_dob.getText().toString());
        RequestBody reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), et_mobile.getText().toString());
        RequestBody reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), et_email.getText().toString());
        RequestBody reqPass = RequestBody.create(MediaType.parse("multipart/form-data"), et_password.getText().toString());
        RequestBody reqTxtMsgNoti = RequestBody.create(MediaType.parse("multipart/form-data"), cb_txt_msg_value);
        RequestBody reqEmailNoti = RequestBody.create(MediaType.parse("multipart/form-data"), cb_email_value);
        RequestBody reqPushNoti = RequestBody.create(MediaType.parse("multipart/form-data"), cb_push_noti_value);
        RequestBody reqDevId = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        if (!profile_image_path.equalsIgnoreCase("")) {
            File file1 = new File(profile_image_path);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
            fileProfileImage = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);
        }
        Call<UpdateProfileResponse> call = apiInterface.updateCustomerProfile(reqCustId, reqFname, reqLname, reqDob, reqMobile, reqEmail, reqPass, reqTxtMsgNoti, reqEmailNoti, reqPushNoti, reqDevId, fileProfileImage);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    removeProgressBar(getActivity());
                    getProfileData();
                    new Handler().postDelayed(() -> Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show(), 1000);
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
            public void onFailure(Call<UpdateProfileResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void imageBackListener() {
        ImageView imageView_Back = view.findViewById(R.id.imageView_Back);
        imageView_Back.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });
    }


    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            galleryIntent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, 0);
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> {
                    //Toast.makeText(getApplicationContext(), "Error occurred! ", //Toast.LENGTH_SHORT).show();
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (documentType.equalsIgnoreCase("paymentFile")) {

                if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    // firstPath = cursor.getString(columnIndex);
                    // roi_path = cursor.getString(columnIndex);
                    //  tv_paymentScreenshotPath.setText(cursor.getString(columnIndex));
                    profile_image_path = cursor.getString(columnIndex);

                    // Toast.makeText(getActivity(), "" + profile_image_path, Toast.LENGTH_SHORT).show();

                    selectedImage = data.getData();
                    InputStream in;
                    try {
                        in = getActivity().getContentResolver().openInputStream(selectedImage);
                        final Bitmap selected_img = BitmapFactory.decodeStream(in);
                        iv_profile.setImageBitmap(selected_img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "An error occured!", Toast.LENGTH_LONG).show();
                    }
                    // Toast.makeText(this, ""+tv_ImagePath.getText().toString(), Toast.LENGTH_SHORT).show();
                    // sendAadharFrontImage((textViews.get(0).getText().toString()));
                    cursor.close();
                } /*else {
                    //Toast.makeText(getApplicationContext(), "You haven't picked Image", //Toast.LENGTH_LONG).show();
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}