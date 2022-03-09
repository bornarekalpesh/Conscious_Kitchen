package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.ViewOrderFragment;
import com.technothinksup.consciouskitchen.model.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.HorizontalViewHolder> {

    private final Context context;

    private final List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_list_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_order_no.setText("#00" + orderList.get(position).getOrderNo());


            String date= orderList.get(position).getOrderDate().substring(0,10);

            holder.tv_order_date.setText("" +date);

            holder.tv_order_amt.setText("" + orderList.get(position).getOrderTotalAmount());
            holder.tv_order_status.setText("" + orderList.get(position).getOrderStatus());

            if (orderList.get(position).getOrderType().equalsIgnoreCase("1")) {
                holder.tv_order_type.setText("Pick Up");
            } else if (orderList.get(position).getOrderType().equalsIgnoreCase("2")) {
                holder.tv_order_type.setText("Delivery");
            } else if (orderList.get(position).getOrderType().equalsIgnoreCase("3")) {
                holder.tv_order_type.setText("Curbside");

            }

            holder.tv_view_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewOrderFragment viewOrderFragment = new ViewOrderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("order_id", orderList.get(position).getOrderId());

                    viewOrderFragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(viewOrderFragment, true);
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_order_no;
        private final TextView tv_order_date;
        private final TextView tv_order_type;
        private final TextView tv_order_amt;
        private final TextView tv_order_status;
        private final TextView tv_view_order;



        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_order_date = itemView.findViewById(R.id.tv_order_date);
            tv_order_type = itemView.findViewById(R.id.tv_order_type);
            tv_order_amt = itemView.findViewById(R.id.tv_order_amt);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            tv_view_order = itemView.findViewById(R.id.tv_view_order);
        }
    }
}