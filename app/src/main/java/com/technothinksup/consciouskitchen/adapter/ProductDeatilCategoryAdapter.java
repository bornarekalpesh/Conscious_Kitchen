package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CategoryList;
import com.technothinksup.consciouskitchen.model.MenuSubCategoryResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDeatilCategoryAdapter extends RecyclerView.Adapter<ProductDeatilCategoryAdapter.HorizontalViewHolder> {

    private Context context;

    private List<CategoryList> categoryLists;

    private String catMenuImagePath;

    private UpsellCategoryItemAdapter upsellCategoryItemAdapter;

    boolean arrowClickFlag=false;


    public ProductDeatilCategoryAdapter(Context context, List<CategoryList> categoryLists, String catMenuImagePath) {
        this.context = context;
        this.categoryLists = categoryLists;
        this.catMenuImagePath = catMenuImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_detail_cat_item, parent, false);
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

            holder.iv_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setSubCategoryMenuAdapter(holder, categoryLists.get(position).getUpsellCategoryId());

                    if (holder.linearLayout_Product1.getVisibility() == View.GONE) {
                        holder.linearLayout_Product1.setVisibility(View.VISIBLE);

                    } else {
                        holder.linearLayout_Product1.setVisibility(View.GONE);

                    }

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    /*MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food_cat_id", categoryLists.get(position).getFoodCategoryId());
                    bundle.putString("food_cat_name", categoryLists.get(position).getFoodCategoryName());
                    menuSubCategoryfragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(menuSubCategoryfragment, true);*/

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setSubCategoryMenuAdapter(HorizontalViewHolder holder, String upsell_category_id) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<MenuSubCategoryResponse> call = apiInterface.getMenuSubCategoryList("10");
        call.enqueue(new Callback<MenuSubCategoryResponse>() {
            @Override
            public void onResponse(Call<MenuSubCategoryResponse> call, Response<MenuSubCategoryResponse> response) {

                upsellCategoryItemAdapter = new UpsellCategoryItemAdapter(context, response.body().getItemList(), response.body().getItemImagePath());
                holder.rv_upsell_item.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                holder.rv_upsell_item.setAdapter(upsellCategoryItemAdapter);
                upsellCategoryItemAdapter.notifyDataSetChanged();
                holder.rv_upsell_item.setHasFixedSize(true);
                if(arrowClickFlag) {
                    arrowClickFlag=false;

                    holder.nsv.setBackground(null);

                }
                else
                {
                    arrowClickFlag=true;
                    holder.nsv.setBackground(context.getResources().getDrawable(R.drawable.bg_white_red));

                }

            }

            @Override
            public void onFailure(Call<MenuSubCategoryResponse> call, Throwable t) {

                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryLists.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cat_icon, iv_arrow;
        private TextView tv_category;

        private LinearLayout linearLayout_Product1;

        private RecyclerView rv_upsell_item;
        NestedScrollView nsv;


        public HorizontalViewHolder(View itemView) {
            super(itemView);
            nsv= itemView.findViewById(R.id.nestedScrollView);
            iv_cat_icon = itemView.findViewById(R.id.iv_cat_icon);
            iv_arrow = itemView.findViewById(R.id.iv_arrow);
            tv_category = itemView.findViewById(R.id.tv_category);
            rv_upsell_item = itemView.findViewById(R.id.rv_upsell_item);
            linearLayout_Product1 = itemView.findViewById(R.id.linearLayout_Product1);
        }
    }
}