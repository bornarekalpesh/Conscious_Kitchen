package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ContactResponse;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.HorizontalViewHolder> {

    private Context context;

    private List<ContactResponse> contactResponseList;

    public ContactListAdapter(Context context, List<ContactResponse> contactResponseList) {
        this.context = context;
        this.contactResponseList = contactResponseList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {
            holder.tv_contact_name.setText(contactResponseList.get(position).getCustomerName());

            holder.fab_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.sendRefSMS(HomePageActivity.userId, contactResponseList.get(position).getCustomerName(), contactResponseList.get(position).getCustomerNumber());
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return contactResponseList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_contact_name;

        private FloatingActionButton fab_send;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_contact_name = itemView.findViewById(R.id.tv_contact_name);
            fab_send = itemView.findViewById(R.id.fab_send);

        }
    }
}