package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Common.showProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class AddCurbsideInstructionFragment extends Fragment {

    private View view;
    private FormEditText editText_CurbsideColor, editText_CurbsideModel, editText_CurbsideMake, editText_Curbsidedesc;
    private Switch switch_CurbsideSaveDescription;
    private CardView cardView_CurbsideContinue;
    private ImageView imageView_Back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_curbside_instruction, container, false);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();
        listener();
        return view;
    }

    private void init() {
        editText_CurbsideColor = view.findViewById(R.id.editText_CurbsideColor);
        editText_CurbsideModel = view.findViewById(R.id.editText_CurbsideModel);
        editText_CurbsideMake = view.findViewById(R.id.editText_CurbsideMake);
        editText_Curbsidedesc = view.findViewById(R.id.editText_Curbsidedesc);
        switch_CurbsideSaveDescription = view.findViewById(R.id.switch_CurbsideSaveDescription);
        cardView_CurbsideContinue = view.findViewById(R.id.cardView_CurbsideContinue);
        imageView_Back = view.findViewById(R.id.imageView_Back);
        if (switch_CurbsideSaveDescription.isChecked()) {
            editText_Curbsidedesc.setVisibility(View.VISIBLE);
        } else {
            editText_Curbsidedesc.setVisibility(View.GONE);
        }
    }

    private void listener() {
        switch_CurbsideSaveDescription.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switch_CurbsideSaveDescription.isChecked()) {
                editText_Curbsidedesc.setVisibility(View.VISIBLE);
            } else {
                editText_Curbsidedesc.setVisibility(View.GONE);
            }
        });

        cardView_CurbsideContinue.setOnClickListener(v -> {
            if (editText_CurbsideColor.testValidity() && editText_CurbsideModel.testValidity() && editText_CurbsideMake.testValidity()) {
                if (isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
                   // showProgressBar(getActivity());
                    addCurbSideInstruction();
                } else NetworkError(getActivity());
            }
        });
        imageView_Back.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void addCurbSideInstruction() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.addCurbsideInstruction(HomePageActivity.userId, editText_CurbsideMake.getText().toString(), editText_CurbsideModel.getText().toString(), editText_CurbsideColor.getText().toString(), editText_Curbsidedesc.getText().toString());
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (getFragmentManager() != null) {
                                getFragmentManager().popBackStack();
                            } else {
                                ((HomePageActivity) getActivity()).loadFragment(new CurbsideInstructionListFragment(), true);
                            }
                        }
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
            public void onFailure(Call<CommonResponse> call, Throwable e) {
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
        Objects.requireNonNull(getActivity()).runOnUiThread(() ->
        {
            removeProgressBar(getActivity());
            onErrorSnack(getActivity(), message);
        });
    }
}