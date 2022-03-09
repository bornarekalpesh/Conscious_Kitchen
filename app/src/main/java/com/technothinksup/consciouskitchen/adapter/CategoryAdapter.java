package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.MenuSubCategoryfragment;
import com.technothinksup.consciouskitchen.model.CategoryList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HorizontalViewHolder> {

    private Context context;

    private List<CategoryList>categoryLists;

    private String catMenuImagePath;


    public CategoryAdapter(Context context, List<CategoryList> categoryLists, String catMenuImagePath) {
        this.context = context;
        this.categoryLists = categoryLists;
        this.catMenuImagePath = catMenuImagePath;
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

            String image_url = catMenuImagePath + categoryLists.get(position).getFoodCategoryImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.iv_cat_icon);

            holder.tv_category.setText(categoryLists.get(position).getFoodCategoryName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();


                    MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food_cat_id", categoryLists.get(position).getFoodCategoryId());
                    bundle.putString("food_cat_name", categoryLists.get(position).getFoodCategoryName());
                    menuSubCategoryfragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(menuSubCategoryfragment, true);

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
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