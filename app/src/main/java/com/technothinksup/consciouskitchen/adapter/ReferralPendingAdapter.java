package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.PendingReferral;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralPendingAdapter extends RecyclerView.Adapter<ReferralPendingAdapter.HorizontalViewHolder> {

    private Context context;

    private List<PendingReferral> pendingReferralList;

    public ReferralPendingAdapter(Context context, List<PendingReferral> pendingReferralList) {
        this.context = context;
        this.pendingReferralList = pendingReferralList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.referal_pending_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_contact_name.setText(pendingReferralList.get(position).getRefPersonName());

       /* holder.fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<CommonResponse>call=apiInterface.sendRefSMS(HomePageActivity.userId,contactResponseList.get(position).getCustomerName(),contactResponseList.get(position).getCustomerNumber());
                call.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                });

            }
        });*/

            holder.cardView_SendReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.sendRefSMS(HomePageActivity.userId, pendingReferralList.get(position).getRefPersonCustomerName(), pendingReferralList.get(position).getRefPersonMobile());
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                          //  Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
        return pendingReferralList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_contact_name;

        private CardView cardView_SendReminder;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_contact_name = itemView.findViewById(R.id.tv_contact_name);
            cardView_SendReminder = itemView.findViewById(R.id.cardView_SendReminder);


        }
    }
}