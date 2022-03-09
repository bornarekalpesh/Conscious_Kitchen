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
import com.technothinksup.consciouskitchen.fragments.ProductDetailFragment;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.Item;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuSubCategoryAdapter extends RecyclerView.Adapter<MenuSubCategoryAdapter.HorizontalViewHolder> {

    private Context context;

    private List<Item> itemList;

    private String subCatMenuImagePath;

    public MenuSubCategoryAdapter(Context context, List<Item> itemList, String subCatMenuImagePath) {
        this.context = context;
        this.itemList = itemList;
        this.subCatMenuImagePath = subCatMenuImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu_item_list, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            String image_url = subCatMenuImagePath + itemList.get(position).getItemImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_cat_icon);

            holder.tv_category.setText(itemList.get(position).getItemName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


               /*     ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse> call=apiInterface.save_recent_list(HomePageActivity.userId,itemList.get(position).getItemId());


                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });*/

                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_id", itemList.get(position).getItemId());
                    bundle.putString("item_name", itemList.get(position).getItemName());
                    productDetailFragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(productDetailFragment, true);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cat_icon;
        TextView tv_category;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_cat_icon = itemView.findViewById(R.id.iv_cat_icon);
            tv_category = itemView.findViewById(R.id.tv_category);
        }
    }
}