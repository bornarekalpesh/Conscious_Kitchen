package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ReviewOrderFragment;
import com.technothinksup.consciouskitchen.model.CartItem;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.HorizontalViewHolder> {

    private Context context;

    private List<CartItem> cartItemList;

    private int counter = 0;

    private String imagePath;

    public CartAdapter(Context context, List<CartItem> cartItemList, String imagePath) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.imagePath = imagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item_list, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            String image_url = imagePath + cartItemList.get(position).getItemImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_cart_item_image);

            holder.tv_item_name.setText(cartItemList.get(position).getItemName());
           holder.tv_item_price.setText(HomePageActivity.currency+ cartItemList.get(position).getItemVariationPrice());
         //   holder.tv_item_price.setText(HomePageActivity.currency+ cartItemList.get(position).getCustomizeItemAmount());

            holder.tv_item_desc.setText(cartItemList.get(position).getItemDescr());

            holder.tv_qty.setText("" + cartItemList.get(position).getItemQty());

            counter = Integer.parseInt(cartItemList.get(position).getItemQty());


            holder.itemamt.setText(HomePageActivity.currency+ cartItemList.get(position).getItemTotalPrice());

            //  holder.textView_CouponsTerms.setText("+2 Extra Patties\\n-No Papper Jack Cheese\\n-No Pickles\\n+Extra Tomatoes.");



            if(cartItemList.get(position).getChoiceValueId().equals("13"))
                holder.textView_CouponsTerms.setText("-No " + cartItemList.get(position).getCustomizeItemName());
            else if(cartItemList.get(position).getChoiceValueId().equals("12"))
                holder.textView_CouponsTerms.setText("-Add " + cartItemList.get(position).getCustomizeItemName() + "("+HomePageActivity.currency+cartItemList.get(position).getCustomizeItemAmount()+")");
            else
                holder.textView_CouponsTerms.setText("- "+cartItemList.get(position).getChoiceValueName() + cartItemList.get(position).getCustomizeItemName()+ "("+HomePageActivity.currency+cartItemList.get(position).getCustomizeItemAmount()+")");


            holder.tv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.removeCartItem(HomePageActivity.userId, cartItemList.get(position).getCartId());

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                          //  Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                            ((HomePageActivity) context).loadFragment(new ReviewOrderFragment(), false);
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });

                }
            });

            holder.tv_plus_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    counter++;

                    holder.tv_qty.setText(""+counter);

                    int qty= Integer.parseInt(holder.tv_qty.getText().toString());

                    Log.e("qty",""+qty);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.updateQty(HomePageActivity.userId, cartItemList.get(position).getCartId(), Integer.parseInt(holder.tv_qty.getText().toString()), Float.valueOf(cartItemList.get(position).getItemVariationPrice()), Float.valueOf(cartItemList.get(position).getCustomizeItemAmount()), Float.valueOf(cartItemList.get(position).getTaxRatePer()));

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                         //   Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ((HomePageActivity) context).loadFragment(new ReviewOrderFragment(), true);
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });


                }
            });


            holder.tv_minus_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (counter <= 0) {
                        counter = 0;
                    } else {
                        counter--;
                    }

                    holder.tv_qty.setText("" + counter);


                    int qty= Integer.parseInt(holder.tv_qty.getText().toString());

                    Log.e("qty",""+qty);

                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.updateQty(HomePageActivity.userId, cartItemList.get(position).getCartId(), qty, Float.valueOf(cartItemList.get(position).getItemVariationPrice()), Float.valueOf(cartItemList.get(position).getCustomizeItemAmount()), Float.valueOf(cartItemList.get(position).getTaxRatePer()));

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                           // Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            ((HomePageActivity) context).loadFragment(new ReviewOrderFragment(), true);
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
        return cartItemList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cart_item_image;
        TextView tv_item_name, tv_item_price, tv_item_desc, tv_remove, tv_minus_qty, tv_plus_qty, tv_qty,textView_CouponsTerms,itemamt;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            iv_cart_item_image = itemView.findViewById(R.id.iv_cart_item_image);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            tv_item_desc = itemView.findViewById(R.id.tv_item_desc);
            tv_remove = itemView.findViewById(R.id.tv_remove);
            tv_minus_qty = itemView.findViewById(R.id.tv_minus_qty);
            tv_plus_qty = itemView.findViewById(R.id.tv_plus_qty);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            itemamt = itemView.findViewById(R.id.itemamt);

            textView_CouponsTerms= itemView.findViewById(R.id.textView_CouponsTerms);


        }
    }
}

