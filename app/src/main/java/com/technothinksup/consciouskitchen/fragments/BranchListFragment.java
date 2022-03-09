package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.BranchAdapter;
import com.technothinksup.consciouskitchen.model.BranchResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BranchListFragment extends Fragment {

    private View view;

    private RecyclerView rv_branch;

    private BranchAdapter branchAdapter;

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
        view= inflater.inflate(R.layout.fragment_branch_list, container, false);

        init();

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        getBranchList();

        listener();

        return view;
    }

    private void listener() {
        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void getBranchList() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<BranchResponse>call=apiInterface.getBranchResponse();

        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                branchAdapter  = new BranchAdapter(getActivity(), response.body().getBranchList());
                rv_branch.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv_branch.setAdapter(branchAdapter);
                branchAdapter.notifyDataSetChanged();
                rv_branch.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {

            }
        });
    }

    private void init() {
        rv_branch = view.findViewById(R.id.rv_branch);
        imageView_Back = view.findViewById(R.id.imageView_Back);

    }
}