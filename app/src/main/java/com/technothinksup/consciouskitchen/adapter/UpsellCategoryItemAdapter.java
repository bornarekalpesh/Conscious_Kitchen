package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.Item;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpsellCategoryItemAdapter extends RecyclerView.Adapter<UpsellCategoryItemAdapter.HorizontalViewHolder> {

    private Context context;

    private List<Item> itemList;

    private String subCatMenuImagePath;

    public UpsellCategoryItemAdapter(Context context, List<Item> itemList, String subCatMenuImagePath) {
        this.context = context;
        this.itemList = itemList;
        this.subCatMenuImagePath = subCatMenuImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.upsell_category_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            String image_url = subCatMenuImagePath + itemList.get(position).getItemImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_upsell_cat_image);

            holder.tv_upsell_item_name.setText(itemList.get(position).getItemName());
            
            holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call = apiInterface.addToCart(HomePageActivity.userId, itemList.get(position).getItemId(), itemList.get(position).getItemId(), "", "", 0,
                            "", "", "", "", "", "", "",
                            0, 1, itemList.get(position).getTaxRateId(), Integer.parseInt(itemList.get(position).getTaxRatePer()));


                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {



                            if (response.body().getStatus() == 1) {

                                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });
                }
            });



          /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call=apiInterface.save_recent_list(HomePageActivity.userId,itemList.get(position).getItemId());


                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });

                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_id", itemList.get(position).getItemId());
                    bundle.putString("item_name", itemList.get(position).getItemName());
                    productDetailFragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(productDetailFragment, true);

                }
            });*/




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_upsell_cat_image;
        TextView tv_upsell_item_name,tv_add_to_cart;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_upsell_cat_image = itemView.findViewById(R.id.iv_upsell_cat_image);
            tv_upsell_item_name = itemView.findViewById(R.id.tv_upsell_item_name);
            tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);
        }
    }
}