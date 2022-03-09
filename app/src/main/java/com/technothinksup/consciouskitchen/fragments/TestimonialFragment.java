package com.technothinksup.consciouskitchen.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.andreabaccega.widget.FormEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.AddTestmonialResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimonialFragment extends Fragment {

    private View view;
    LinearLayout linearLayout_TestimonialShowFBInsta, linearLayout_TestimonialSocialWithPhoto;
    FormEditText editText_TestimonialAddContain;
    ImageView imageView_TestimonialEmoji;
    Button button_TestimonialAddEmoji, button_PostTestimonial;
    private TextView textView_TestimonialWherePost, textView_TestimonialAddPhoto, textView_TestimonialUserName,postck;

    private ImageView imageView_BackTestimonial, imageView_RewardTestimonial, imageView_CartTestimonial,relativeLayout_TestimonialTagPeople,imageView_TestimonialPostYourAccount;

    private RequestBody customer_id, testimonial_cust_name, testimonial_cust_address, testimonial_details;
    private MultipartBody.Part fileTestimonialImage;

    private String imgPath = "";

    private String documentType;

    private Intent galleryIntent;

    public static TestimonialFragment newInstance() {
        return new TestimonialFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_testimonial, container, false);

        init();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listener();
        requestPermission();


        return view;
    }

    private void listener() {
        imageView_BackTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        textView_TestimonialWherePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupSocialMedia();
            }
        });

        textView_TestimonialAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                documentType = "ImagePath";

                CropImage.activity().setAspectRatio(6, 6).start(getActivity(), TestimonialFragment.this);

              /*  if (checkCameraPermission()) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
                }*/
                CropImage.activity().setAspectRatio(6, 6).start(getActivity(), TestimonialFragment.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkCameraPermission()) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
                    } else if (checkStoragePermission()) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 112);
                    } else {
                        CropImage.activity().setAspectRatio(6, 6).start(getActivity(), TestimonialFragment.this);
                    }
                } else {
                    CropImage.activity().setAspectRatio(6, 6).start(getActivity(), TestimonialFragment.this);

                }

            }
        });


        button_PostTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loadTestimonialDataFragment();
                if (editText_TestimonialAddContain.testValidity()) {

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), "" + HomePageActivity.userId);
                    testimonial_cust_name = RequestBody.create(MediaType.parse("multipart/form-data"), "" + "");
                    testimonial_cust_address = RequestBody.create(MediaType.parse("multipart/form-data"), "" + "");
                    testimonial_details = RequestBody.create(MediaType.parse("multipart/form-data"), "");


                    if (!imgPath.equalsIgnoreCase("")) {
                        File file1 = new File(imgPath);
                        RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                        fileTestimonialImage = MultipartBody.Part.createFormData("testimonial_image", file1.getName(), requestBody1);
                    }

                    Call<AddTestmonialResponse> call = apiInterface.addTestimonial(customer_id, testimonial_cust_name, testimonial_cust_address, testimonial_details, fileTestimonialImage);

                    call.enqueue(new Callback<AddTestmonialResponse>() {
                        @Override
                        public void onResponse(Call<AddTestmonialResponse> call, Response<AddTestmonialResponse> response) {

                            if (response.body().getStatus() == 1) {

                                Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                loadTestimonialDataFragment();
                            }


                        }

                        @Override
                        public void onFailure(Call<AddTestmonialResponse> call, Throwable t) {


                        }
                    });
                }


                /*if(.equalsIgnoreCase("")){
                    File file1 = new File(profile_image_path);
                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                    fileProfileImage = MultipartBody.Part.createFormData("image", file1.getName(), requestBody1);
                }*/


            }
        });

        button_TestimonialAddEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddEmoji();
            }
        });


        relativeLayout_TestimonialTagPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });


        imageView_TestimonialPostYourAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTestimonialDataFragment();
            }
        });

        postck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTestimonialDataFragment();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }

    private void init() {
        button_PostTestimonial = view.findViewById(R.id.button_PostTestimonial);
        button_TestimonialAddEmoji = view.findViewById(R.id.button_TestimonialAddEmoji);
        textView_TestimonialWherePost = view.findViewById(R.id.textView_TestimonialWherePost);
        linearLayout_TestimonialShowFBInsta = view.findViewById(R.id.linearLayout_TestimonialShowFBInsta);
        editText_TestimonialAddContain = view.findViewById(R.id.editText_TestimonialAddContain);
        linearLayout_TestimonialSocialWithPhoto = view.findViewById(R.id.linearLayout_TestimonialSocialWithPhoto);
        imageView_TestimonialEmoji = view.findViewById(R.id.imageView_TestimonialEmoji);
        relativeLayout_TestimonialTagPeople = view.findViewById(R.id.imageView_TestimonialTagPeople);
        imageView_TestimonialPostYourAccount = view.findViewById(R.id.imageView_TestimonialPostYourAccount);
        textView_TestimonialUserName = view.findViewById(R.id.textView_TestimonialUserName);
        textView_TestimonialUserName.setText(HomePageActivity.name);
        postck =view.findViewById(R.id.postck);
        imageView_BackTestimonial = view.findViewById(R.id.imageView_BackTestimonial);
        imageView_RewardTestimonial = view.findViewById(R.id.imageView_RewardTestimonial);
        imageView_CartTestimonial = view.findViewById(R.id.imageView_CartTestimonial);
        textView_TestimonialAddPhoto = view.findViewById(R.id.textView_TestimonialAddPhoto);
    }

    private void popupAddEmoji() {
        View popUpViewEmoji = getLayoutInflater().inflate(R.layout.popup_add_emoji, null); // inflating popup layout
        final PopupWindow emojiPopup = new PopupWindow(popUpViewEmoji, Toolbar.LayoutParams.FILL_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        emojiPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        emojiPopup.showAtLocation(popUpViewEmoji, Gravity.CENTER, 0, 0);
        TextView textView_AddEmoji = popUpViewEmoji.findViewById(R.id.textView_AddEmoji);
        final CheckBox checkBox_Emoji_One = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_One);
        final CheckBox checkBox_Emoji_Two = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Two);
        final CheckBox checkBox_Emoji_Three = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Three);
        final CheckBox checkBox_Emoji_Four = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Four);
        final CheckBox checkBox_Emoji_Five = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Five);
        final CheckBox checkBox_Emoji_Six = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Six);
        final CheckBox checkBox_Emoji_Seven = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Seven);
        final CheckBox checkBox_Emoji_Eight = popUpViewEmoji.findViewById(R.id.checkBox_Emoji_Eight);


        textView_AddEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emojiPopup.dismiss();
            }
        });
        checkBox_Emoji_One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_One.isChecked()) {
                    checkBox_Emoji_One.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_one);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_One.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Two.isChecked()) {
                    checkBox_Emoji_Two.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_two);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Two.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Three.isChecked()) {
                    checkBox_Emoji_Three.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_three);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Three.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Four.isChecked()) {
                    checkBox_Emoji_Four.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_four);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Four.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Five.isChecked()) {
                    checkBox_Emoji_Five.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_five);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Five.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Six.isChecked()) {
                    checkBox_Emoji_Six.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_six);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Six.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Seven.isChecked()) {
                    checkBox_Emoji_Seven.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_seven);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Seven.setChecked(false);
                }
            }
        });
        checkBox_Emoji_Eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Emoji_Eight.isChecked()) {
                    checkBox_Emoji_Eight.setChecked(true);
                    imageView_TestimonialEmoji.setVisibility(View.VISIBLE);
                    imageView_TestimonialEmoji.setImageResource(R.drawable.emoji_eight);
                    button_TestimonialAddEmoji.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setText("Conscious Kitchen is favorite restaurant in\nCincinnati go check it out !");
                    emojiPopup.dismiss();
                } else {
                    checkBox_Emoji_Eight.setChecked(false);
                }
            }
        });
    }

    private void popupSocialMedia() {
        View popUpViewSocial = getLayoutInflater().inflate(R.layout.popup_social_media, null); // inflating popup layout
        final PopupWindow socialPopup = new PopupWindow(popUpViewSocial, Toolbar.LayoutParams.FILL_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT, true); // Creation of popup
        socialPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        socialPopup.showAtLocation(popUpViewSocial, Gravity.CENTER, 0, 0);
        TextView textView_PointSocialPopUp = popUpViewSocial.findViewById(R.id.textView_PointSocialPopUp);

        final CheckBox checkBox_Social_Yelp = popUpViewSocial.findViewById(R.id.checkBox_Social_Yelp);
        final CheckBox checkBox_Social_FB = popUpViewSocial.findViewById(R.id.checkBox_Social_FB);
        final CheckBox checkBox_Social_Insta = popUpViewSocial.findViewById(R.id.checkBox_Social_Insta);
        final CheckBox checkBox_Social_Twitter = popUpViewSocial.findViewById(R.id.checkBox_Social_Twitter);
        final CheckBox checkBox_Social_Pin = popUpViewSocial.findViewById(R.id.checkBox_Social_Pin);

        textView_PointSocialPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                socialPopup.dismiss();
            }
        });

        checkBox_Social_Yelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Yelp", Toast.LENGTH_SHORT).show();

                if (checkBox_Social_Yelp.isChecked()) {
                    checkBox_Social_Yelp.setChecked(true);
                    linearLayout_TestimonialShowFBInsta.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setVisibility(View.VISIBLE);
                    linearLayout_TestimonialSocialWithPhoto.setVisibility(View.INVISIBLE);
                    socialPopup.dismiss();
                } else {
                    checkBox_Social_Yelp.setChecked(false);
                }
            }
        });
        checkBox_Social_FB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Social_FB.isChecked()) {
                    checkBox_Social_FB.setChecked(true);
                    linearLayout_TestimonialShowFBInsta.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setVisibility(View.INVISIBLE);
                    linearLayout_TestimonialSocialWithPhoto.setVisibility(View.VISIBLE);
                    socialPopup.dismiss();
                } else {
                    checkBox_Social_FB.setChecked(false);
                }
            }
        });
        checkBox_Social_Insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Social_Insta.isChecked()) {
                    checkBox_Social_Insta.setChecked(true);
                    linearLayout_TestimonialShowFBInsta.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setVisibility(View.INVISIBLE);
                    linearLayout_TestimonialSocialWithPhoto.setVisibility(View.VISIBLE);
                    socialPopup.dismiss();
                } else {
                    checkBox_Social_Insta.setChecked(false);
                }
            }
        });
        checkBox_Social_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Social_Twitter.isChecked()) {
                    checkBox_Social_Twitter.setChecked(true);
                    linearLayout_TestimonialShowFBInsta.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setVisibility(View.INVISIBLE);
                    linearLayout_TestimonialSocialWithPhoto.setVisibility(View.VISIBLE);
                    socialPopup.dismiss();
                } else {
                    checkBox_Social_Twitter.setChecked(false);
                }
            }
        });
        checkBox_Social_Pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox_Social_Pin.isChecked()) {
                    checkBox_Social_Pin.setChecked(true);
                    linearLayout_TestimonialShowFBInsta.setVisibility(View.VISIBLE);
                    editText_TestimonialAddContain.setVisibility(View.INVISIBLE);
                    linearLayout_TestimonialSocialWithPhoto.setVisibility(View.VISIBLE);
                    socialPopup.dismiss();
                } else {
                    checkBox_Social_Pin.setChecked(false);
                }
            }
        });

    }

    private void loadTestimonialDataFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TestimonialDataFragment.newInstance())
                .addToBackStack(null)
                .commit();
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


    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (documentType.equalsIgnoreCase("ImagePath")) {

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
                    // imgPath=cursor.getString(columnIndex);

                    imgPath = cursor.getString(columnIndex);

                    Toast.makeText(getActivity(), "" + imgPath, Toast.LENGTH_SHORT).show();


                    // sendAadharFrontImage((textViews.get(0).getText().toString()));

                    cursor.close();

                } else {
                    //Toast.makeText(getApplicationContext(), "You haven't picked Image", //Toast.LENGTH_LONG).show();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result1 = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Bitmap imageBitmap = null;
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result1.getUri());

                    Uri tempUri = getImageUri(getActivity(), imageBitmap);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {

        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                imgPath = cursor.getString(idx);
                cursor.close();

                // textViews.get(0).setText(firstPath);

                Log.d("paths", "" + imgPath);
            }
        }
        return imgPath;
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

                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            // showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //Toast.makeText(getApplicationContext(), "Error occurred! ", //Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}
