package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.CurbsideInstructionAdapter;
import com.technothinksup.consciouskitchen.model.CurbsideInstructionResponse;
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

public class CurbsideInstructionListFragment extends Fragment {

    private View view;
    private RecyclerView rv_instruction;
    private CurbsideInstructionAdapter curbsideInstructionAdapter;
    private TextView tv_add_curbside_instruction;
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
        view = inflater.inflate(R.layout.fragment_curbside_instruction_list, container, false);
        if (isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
            //showProgressBar(getActivity());
            getData();
        } else NetworkError(getActivity());
        init();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        return view;
    }

    private void init() {
        rv_instruction = view.findViewById(R.id.rv_instruction);
        imageView_Back = view.findViewById(R.id.imageView_Back);
        tv_add_curbside_instruction = view.findViewById(R.id.tv_add_curbside_instruction);
    }

    private void listener() {
        tv_add_curbside_instruction.setOnClickListener(v -> ((HomePageActivity) getActivity()).loadFragment(new AddCurbsideInstructionFragment(), true));
        imageView_Back.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void getData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<CurbsideInstructionResponse> call = apiInterface.getCurbSideInstruction(HomePageActivity.userId);
        call.enqueue(new Callback<CurbsideInstructionResponse>() {
            @Override
            public void onResponse(Call<CurbsideInstructionResponse> call, Response<CurbsideInstructionResponse> response) {
                if (response.isSuccessful()) {
                   // removeProgressBar(getActivity());
                    if (response.body() != null) {
                        curbsideInstructionAdapter = new CurbsideInstructionAdapter(getActivity(), response.body().getCurbsideInstructionList());
                        rv_instruction.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv_instruction.setAdapter(curbsideInstructionAdapter);
                        curbsideInstructionAdapter.notifyDataSetChanged();
                        rv_instruction.setHasFixedSize(true);
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
            public void onFailure(Call<CurbsideInstructionResponse> call, Throwable e) {
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
          //  removeProgressBar(getActivity());
            onErrorSnack(getActivity(), message);
        });
    }
}