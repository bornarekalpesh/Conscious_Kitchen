package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationPreferenceFragment extends Fragment {

    private View view;

    private AppCompatCheckBox checkBox_TextNotification, checkBox_EmailNotification, checkBox_PushNotification;

    private String cb_txt_msg_value="",cb_email_value="",cb_push_noti_value="";

    private Button button_update;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_notification_preference, container, false);

        init();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        imageBackListener();
        return view;
    }



    private void init() {
        checkBox_TextNotification = view.findViewById(R.id.checkBox_TextNotification);
        checkBox_EmailNotification = view.findViewById(R.id.checkBox_EmailNotification);
        checkBox_PushNotification = view.findViewById(R.id.checkBox_PushNotification);
        button_update = view.findViewById(R.id.button_update);
    }

    private void imageBackListener() {
        ImageView imageView_Back=view.findViewById(R.id.imageView_Back);

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void listener() {
        checkBox_TextNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    cb_txt_msg_value="1";
                }else {
                    cb_txt_msg_value="0";
                }

               // Toast.makeText(getActivity(), ""+isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        checkBox_EmailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    cb_email_value="1";
                }else {
                    cb_email_value="0";
                }

            }
        });

        checkBox_PushNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_push_noti_value="1";
                }else {
                    cb_push_noti_value="0";
                }


            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<CommonResponse>call=apiInterface.updateNotificationPreference(HomePageActivity.userId,cb_txt_msg_value,cb_email_value,cb_push_noti_value);
                call.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}