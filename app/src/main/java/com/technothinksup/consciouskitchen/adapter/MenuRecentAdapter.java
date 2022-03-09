package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ProductDetailFragment;
import com.technothinksup.consciouskitchen.model.Item;

import java.util.List;

public class MenuRecentAdapter extends RecyclerView.Adapter<MenuRecentAdapter.HorizontalViewHolder> {

    private Context context;

    private List<Item> itemDetailList;

    private String recentItemImagePath;

    public MenuRecentAdapter(Context context, List<Item> itemDetailList, String recentItemImagePath) {
        this.context = context;
        this.itemDetailList = itemDetailList;
        this.recentItemImagePath = recentItemImagePath;
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

            String image_url = recentItemImagePath + itemDetailList.get(position).getItemImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_cat_icon);

            holder.tv_category.setText(itemDetailList.get(position).getItemName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_id", itemDetailList.get(position).getItemId());
                    bundle.putString("item_name", itemDetailList.get(position).getItemName());
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
        return itemDetailList.size();
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