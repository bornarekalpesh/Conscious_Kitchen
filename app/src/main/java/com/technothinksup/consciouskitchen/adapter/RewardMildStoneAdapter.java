package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.RewardList;

import java.util.List;

public class RewardMildStoneAdapter extends RecyclerView.Adapter<RewardMildStoneAdapter.HorizontalViewHolder> {

    private Context context;

    private List<RewardList>rewardLists;


    public RewardMildStoneAdapter(Context context, List<RewardList> rewardLists) {
        this.context = context;
        this.rewardLists = rewardLists;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reward_mildstone_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_point.setText(rewardLists.get(position).getRewardMilestonePoint());

            holder.tv_reward_name.setText(rewardLists.get(position).getRewardName());



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    /*MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food_cat_id", categoryLists.get(position).getFoodCategoryId());
                    bundle.putString("food_cat_name", categoryLists.get(position).getFoodCategoryName());
                    menuSubCategoryfragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(menuSubCategoryfragment, true);*/

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return rewardLists.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cat_icon;
        TextView tv_point,tv_reward_name;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_point=itemView.findViewById(R.id.tv_point);
            tv_reward_name=itemView.findViewById(R.id.tv_reward_name);



        }
    }
}