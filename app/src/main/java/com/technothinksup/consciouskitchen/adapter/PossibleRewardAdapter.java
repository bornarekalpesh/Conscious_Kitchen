package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.ItemDetailResponse;
import com.technothinksup.consciouskitchen.model.PossibleReward;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PossibleRewardAdapter extends RecyclerView.Adapter<PossibleRewardAdapter.HorizontalViewHolder> {

    private Context context;

    private List<PossibleReward> possibleRewardList;

    private String item_id;

    public PossibleRewardAdapter(Context context, List<PossibleReward> possibleRewardList) {
        this.context = context;
        this.possibleRewardList = possibleRewardList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.possible_reward_list_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_reward_point.setText(possibleRewardList.get(position).getRewardMilestonePoint() + "Point");

            setItemDetailData(holder, position, possibleRewardList.get(position).getItemId(),possibleRewardList.get(position).getRewardMilestonePoint());






            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();


                  /*  MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
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

    private void setItemDetailData(HorizontalViewHolder holder, int position, String item_id, String rewardMilestonePoint) {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<ItemDetailResponse> call = apiInterface.getItemDetail(item_id);
        call.enqueue(new Callback<ItemDetailResponse>() {
            @Override
            public void onResponse(Call<ItemDetailResponse> call, Response<ItemDetailResponse> response) {

                if (response.body().getStatus() == 1) {
                   // Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();

                    String image_url = response.body().getItemImagePath() + response.body().getItemDetails().get(0).getItemImage();


                    Picasso.with(context)
                            .load(image_url)
                            .into(holder.iv_item_image);

                    holder.tv_item_name.setText(response.body().getItemDetails().get(0).getItemName());
                    holder.tv_item_desc.setText(response.body().getItemDetails().get(0).getItemDescr());

                    holder.textView_RewardAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(context, "click"+response.body().getItemDetails().get(0).getItemVariation().get(0).getItemVariationPrice(), Toast.LENGTH_SHORT).show();

                            if(HomePageActivity.reward_point>Double.parseDouble(rewardMilestonePoint)){
                                Toast.makeText(context, "eligble", Toast.LENGTH_SHORT).show();

                                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                                Call<CommonResponse> call = apiInterface.addToCart(HomePageActivity.userId, item_id, response.body().getItemDetails().get(0).getItemName(), "", "", Float.valueOf(response.body().getItemDetails().get(0).getItemVariation().get(0).getItemVariationPrice()),
                                        "", "", "", "", "", "", "",
                                        0, 1, response.body().getItemDetails().get(0).getTaxRateId(), Integer.parseInt(response.body().getItemDetails().get(0).getTaxRateId()));


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

                            }else {
                                Toast.makeText(context, "not eligble", Toast.LENGTH_SHORT).show();

                                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                                Call<CommonResponse> call = apiInterface.addToCart(HomePageActivity.userId, item_id, response.body().getItemDetails().get(0).getItemName(), "", "", Float.valueOf(response.body().getItemDetails().get(0).getItemVariation().get(0).getItemVariationPrice()),
                                        "", "", "", "", "", "", "",
                                        0, 1, response.body().getItemDetails().get(0).getTaxRateId(), Integer.parseInt(response.body().getItemDetails().get(0).getTaxRateId()));


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

                        }
                    });


                }


            }

            @Override
            public void onFailure(Call<ItemDetailResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return possibleRewardList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_image;
        TextView tv_item_name, tv_item_desc, textView_RewardScanToRedeem, textView_RewardAddToCart, tv_reward_point;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_item_image = itemView.findViewById(R.id.iv_item_image);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_desc = itemView.findViewById(R.id.tv_item_desc);
            textView_RewardScanToRedeem = itemView.findViewById(R.id.textView_RewardScanToRedeem);
            textView_RewardAddToCart = itemView.findViewById(R.id.textView_RewardAddToCart);
            tv_reward_point = itemView.findViewById(R.id.tv_reward_point);
        }
    }
}