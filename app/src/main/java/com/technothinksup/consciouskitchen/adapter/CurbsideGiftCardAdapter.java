package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ConfirmAndPayFragment;
import com.technothinksup.consciouskitchen.fragments.DeliveryConfirmPayFragment;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.GiftCard;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CurbsideGiftCardAdapter extends RecyclerView.Adapter<CurbsideGiftCardAdapter.MyViewHolder> {

    private Context context;

    private List<GiftCard> giftCardList;

    private String imagePath;

    public static double gift_card_amount = 0, gift_card_used_amt = 0;

    public static int isCreditCard = 0;

    public static String used_gift_card_id = "", used_gift_card_code = "";

    public CurbsideGiftCardAdapter(Context context, List<GiftCard> giftCardList, String iamgeUrl) {
        this.context = context;
        this.giftCardList = giftCardList;
        this.imagePath = iamgeUrl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.gift_card_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        try {

            String image_url = imagePath + giftCardList.get(position).getGiftCardImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_image);

            holder.tv_gift_card_code.setText(giftCardList.get(position).getGiftCardCode());
            holder.tv_gift_card_name.setText(giftCardList.get(position).getGiftCardName());
            holder.tv_gift_card_amt.setText(giftCardList.get(position).getGiftCardAmt());
            holder.tv_gift_card_expiry_date.setText(giftCardList.get(position).getGiftCardExpDate());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gift_card_amount = Double.parseDouble(giftCardList.get(position).getGiftCardAmt());

                    used_gift_card_id = giftCardList.get(position).getCustGiftCardId();
                    used_gift_card_code = giftCardList.get(position).getGiftCardCode();

                    if (gift_card_amount > Double.parseDouble(ConfirmAndPayFragment.order_total_amount)) {
                        gift_card_used_amt = Double.parseDouble(ConfirmAndPayFragment.order_total_amount);
                        Toast.makeText(context, "gift grater", Toast.LENGTH_SHORT).show();

                        isCreditCard = 0;

                        Log.e("new amt", "" + gift_card_used_amt);

                    } else {
                        isCreditCard = 1;
                        gift_card_used_amt = Double.parseDouble(ConfirmAndPayFragment.order_total_amount) - gift_card_amount;
                    }


                }
            });

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
                    Call<CommonResponse> call = apiInterface.deleteGiftCard(HomePageActivity.userId, giftCardList.get(position).getCustGiftCardId());

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            //Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            DeliveryConfirmPayFragment deliveryConfirmPayFragment = new DeliveryConfirmPayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", "2");
                            deliveryConfirmPayFragment.setArguments(bundle);
                            ((HomePageActivity) context).loadFragment(deliveryConfirmPayFragment, true);

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
        return giftCardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView tv_gift_card_code, tv_gift_card_name, tv_gift_card_amt, tv_gift_card_expiry_date;

        private ImageView iv_delete, iv_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_gift_card_code = itemView.findViewById(R.id.tv_gift_card_code);
            tv_gift_card_name = itemView.findViewById(R.id.tv_gift_card_name);
            tv_gift_card_amt = itemView.findViewById(R.id.tv_gift_card_amt);
            tv_gift_card_expiry_date = itemView.findViewById(R.id.tv_gift_card_expiry_date);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_image = itemView.findViewById(R.id.iv_image);

        }
    }
}
