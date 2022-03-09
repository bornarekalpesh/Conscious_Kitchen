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
import com.technothinksup.consciouskitchen.model.Reward;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.HorizontalViewHolder> {

    private Context context;

    private List<Reward> rewardLists;

    private String categoryBaseImagePath, itemBaseImagePath;

    public static double reward_point_used = 0;


    public RewardAdapter(Context context, List<Reward> rewardLists, String categoryBaseImagePath, String itemBaseImagePath) {
        this.context = context;
        this.rewardLists = rewardLists;
        this.categoryBaseImagePath = categoryBaseImagePath;
        this.itemBaseImagePath = itemBaseImagePath;
        Log.e("main_reward_point", "" + HomePageActivity.reward_point);
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reward_list_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, int position) {

        try {

            if (rewardLists.get(position).getRewardRedeemType().equalsIgnoreCase("Menu Category")) {

                holder.tv_item_name.setText(rewardLists.get(position).getItemDetails().get(0).getFoodCategoryName());
                holder.tv_item_desc.setText(rewardLists.get(position).getItemDetails().get(0).getFoodCategoryDescr());
                holder.tv_reward_point.setText(HomePageActivity.currency + rewardLists.get(0).getRewardMilestonePoint());

                String categoryPath = categoryBaseImagePath + rewardLists.get(position).getItemDetails().get(0).getFoodCategoryImage();


                Picasso.with(context)
                        .load(categoryPath)
                        .into(holder.iv_item_image);

            } else if (rewardLists.get(position).getRewardRedeemType().equalsIgnoreCase("Menu Item")) {

                holder.tv_item_name.setText(rewardLists.get(position).getItemDetails().get(0).getItemName());
                holder.tv_item_desc.setText(rewardLists.get(position).getItemDetails().get(0).getItemDescr());
                holder.tv_reward_point.setText(HomePageActivity.currency + rewardLists.get(position).getRewardMilestonePoint());

                String itemPath = itemBaseImagePath + rewardLists.get(position).getItemDetails().get(0).getItemImage();

                Picasso.with(context)
                        .load(itemPath)
                        .into(holder.iv_item_image);

            }


            holder.textView_RewardAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (HomePageActivity.reward_point < Integer.parseInt(rewardLists.get(position).getRewardMilestonePoint())) {

                        Toast.makeText(context, "Sorry, You have Insufiicient Reward Point", Toast.LENGTH_SHORT).show();

                    } else {

                        reward_point_used = Double.parseDouble(rewardLists.get(position).getRewardMilestonePoint());

                        double amt = HomePageActivity.reward_point - reward_point_used;


                        double amt_used = Double.parseDouble(rewardLists.get(position).getRewardMilestonePoint());


                        HomePageActivity.reward_point = amt;

                        Log.e("current_reward_point", "" + amt);


                        Toast.makeText(context, "" + reward_point_used, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return rewardLists.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_item_image;

        private TextView tv_item_name, tv_item_desc, tv_reward_point, textView_RewardAdd;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            iv_item_image = itemView.findViewById(R.id.iv_item_image);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_desc = itemView.findViewById(R.id.tv_item_desc);
            tv_reward_point = itemView.findViewById(R.id.tv_reward_point);
            textView_RewardAdd = itemView.findViewById(R.id.textView_RewardAdd);


        }
    }
}