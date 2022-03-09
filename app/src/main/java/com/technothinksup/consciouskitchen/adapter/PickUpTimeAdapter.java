package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ConfirmAndPayFragment;
import com.technothinksup.consciouskitchen.fragments.CurbsideConfirmAndPayFragment;
import com.technothinksup.consciouskitchen.fragments.DeliveryConfirmPayFragment;
import com.technothinksup.consciouskitchen.fragments.WalletFragment;
import com.technothinksup.consciouskitchen.model.PickupTime;

import java.util.List;

public class PickUpTimeAdapter extends RecyclerView.Adapter<PickUpTimeAdapter.HorizontalViewHolder> {

    private Context context;

    private List<PickupTime> pickupTimeList;

    int row_index = -1;


    public PickUpTimeAdapter(Context context, List<PickupTime> pickupTimeList) {
        this.context = context;
        this.pickupTimeList = pickupTimeList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pickuptimeitem, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_pickuptime.setText(pickupTimeList.get(position).getTime());


            holder.tv_pickuptime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    row_index = position;

                    notifyDataSetChanged();

                    ConfirmAndPayFragment.pickup_delivery_time = pickupTimeList.get(position).getTime();
                    DeliveryConfirmPayFragment.pickup_delivery_time = pickupTimeList.get(position).getTime();
                    CurbsideConfirmAndPayFragment.pickup_delivery_time = pickupTimeList.get(position).getTime();
                    WalletFragment.pickup_delivery_time = pickupTimeList.get(position).getTime();


                }
            });

            if (row_index == position) {

                //  Toast.makeText(context, "positiion", Toast.LENGTH_SHORT).show();

                holder.tv_pickuptime.setBackground(context.getResources().getDrawable(R.drawable.text_bg_red));
                holder.tv_pickuptime.setTextColor(context.getResources().getColor(R.color.colorWhite));

            } else {
                holder.tv_pickuptime.setBackground(context.getResources().getDrawable(R.drawable.text_bg_plan));
                holder.tv_pickuptime.setTextColor(context.getResources().getColor(R.color.black));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return pickupTimeList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView tv_pickuptime;

        public HorizontalViewHolder(View itemView) {
            super(itemView);


            tv_pickuptime = itemView.findViewById(R.id.tv_pickuptime);


        }
    }
}