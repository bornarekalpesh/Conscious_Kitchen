package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.fragments.MenuCategoryShowFragment;
import com.technothinksup.consciouskitchen.model.CustAddress;

import java.util.List;

public class CustomerAddressAdapter extends RecyclerView.Adapter<CustomerAddressAdapter.HorizontalViewHolder> {

    private Context context;

    private List<CustAddress> custAddressList;

    private String address;

    public CustomerAddressAdapter(Context context, List<CustAddress> custAddressList) {
        this.context = context;
        this.custAddressList = custAddressList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.customer_address_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {
        try {
             address = custAddressList.get(position).getCustAddress()+ " " + custAddressList.get(position).getCustName() + "\n" +
                    custAddressList.get(position).getDeliveryInstruction()+ "  " + custAddressList.get(position).getCustZipcode();
            holder.tv_address.setText("" + address);
            holder.tv_select_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    ((HomePageActivity)context).loadFragment(new MenuCategoryShowFragment(),true);
                    Common.saveUserData(context,"selectedAddress",custAddressList.get(position).getCustAddress()+custAddressList.get(position).getCustZipcode());
                    Common.saveUserData(context,"selectedCustName",custAddressList.get(position).getCustName());
                    Common.saveUserData(context,"selectedDeliveryInstruction",custAddressList.get(position).getDeliveryInstruction());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return custAddressList.size();
    }

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView tv_address, tv_select_address;

        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_address = itemView.findViewById(R.id.tv_address);
            tv_select_address = itemView.findViewById(R.id.tv_select_address);
        }
    }
}