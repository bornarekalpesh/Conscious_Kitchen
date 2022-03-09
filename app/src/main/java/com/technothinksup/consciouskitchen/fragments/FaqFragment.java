package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.FaqAdapter;
import com.technothinksup.consciouskitchen.adapter.MenuRecentAdapter;
import com.technothinksup.consciouskitchen.model.FaqResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FaqFragment extends Fragment {

    private View view;

    private RecyclerView rv_faq;

    private FaqAdapter faqAdapter;

    private ImageView imageView_Back;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_faq, container, false);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        init();
        listener();
        getData();


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

    private void getData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<FaqResponse>call=apiInterface.getFaqResponse();
        call.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
               // Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();

                faqAdapter = new FaqAdapter(getActivity(), response.body().getFaqCategoryList());
                rv_faq.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_faq.setAdapter(faqAdapter);
                faqAdapter.notifyDataSetChanged();
                rv_faq.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {

            }
        });

    }

    private void init() {
        rv_faq=view.findViewById(R.id.rv_faq);
        imageView_Back=view.findViewById(R.id.imageView_Back);
    }
}