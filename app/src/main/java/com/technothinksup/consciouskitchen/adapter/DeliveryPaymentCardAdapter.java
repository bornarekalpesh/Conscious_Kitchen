package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.DeliveryConfirmPayFragment;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.PaymentCard;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPaymentCardAdapter extends RecyclerView.Adapter<DeliveryPaymentCardAdapter.HorizontalViewHolder> {

    private Context context;

    private List<PaymentCard>paymentCardList;

    public static String card_number = "", card_exp_month, card_exp_year, card_cvv;

    public DeliveryPaymentCardAdapter(Context context, List<PaymentCard> paymentCardList) {
        this.context = context;
        this.paymentCardList = paymentCardList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.paymentcarditem, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            Picasso.with(context)
                    .load(paymentCardList.get(position).getCard_image())
                    .into(holder.iv_card_back_image);

            holder.iv_card_back_image.setBackground(context.getResources().getDrawable(R.drawable.card_bg_lay));
            holder.et_cardNo.setText(paymentCardList.get(position).getCardNumber());

            holder.et_name.setText(paymentCardList.get(position).getNameOnCard());

            holder.et_month.setText(paymentCardList.get(position).getCardExpireMonth());
            holder.et_yy.setText(paymentCardList.get(position).getCardExpireYear());
            holder.et_cvv.setText(paymentCardList.get(position).getCardSecurityCode());

            holder.iv_delete_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse>call=apiInterface.deleteCreditCard(HomePageActivity.userId,paymentCardList.get(position).getPaymentCardId());
                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", "3");
                            deliveryConfirmPayFragment.setArguments(bundle);
                            ((HomePageActivity) context).loadFragment(deliveryConfirmPayFragment, true);
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "card selected", Toast.LENGTH_SHORT).show();
                    card_number = paymentCardList.get(position).getCardNumber();
                    card_exp_month = paymentCardList.get(position).getCardExpireMonth();
                    card_exp_year = paymentCardList.get(position).getCardExpireYear();
                    card_cvv = paymentCardList.get(position).getCardSecurityCode();
                }
            });





        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return paymentCardList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView et_cardNo,et_month,et_yy,et_name,et_cvv;

        private ImageView iv_card_back_image,iv_delete_card;



        public HorizontalViewHolder(View itemView) {
            super(itemView);


            et_cardNo=itemView.findViewById(R.id.et_cardNo);
            et_month=itemView.findViewById(R.id.et_month);
            et_yy=itemView.findViewById(R.id.et_yy);
            et_name=itemView.findViewById(R.id.et_name);
            et_cvv=itemView.findViewById(R.id.et_cvv);
            iv_card_back_image=itemView.findViewById(R.id.iv_card_back_image);
            iv_delete_card=itemView.findViewById(R.id.iv_delete_card);


        }
    }
}