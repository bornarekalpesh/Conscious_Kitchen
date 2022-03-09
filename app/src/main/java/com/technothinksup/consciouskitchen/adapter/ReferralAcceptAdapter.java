package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.AcceptedReferral;

import java.util.List;

public class ReferralAcceptAdapter extends RecyclerView.Adapter<ReferralAcceptAdapter.HorizontalViewHolder> {

    private Context context;

    private List<AcceptedReferral> acceptedReferralList;

    public ReferralAcceptAdapter(Context context, List<AcceptedReferral> acceptedReferralList) {
        this.context = context;
        this.acceptedReferralList = acceptedReferralList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.referal_accepted_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {
            holder.tv_contact_name.setText(acceptedReferralList.get(position).getRefPersonName());
        }catch (Exception e){
            e.printStackTrace();
        }






    }

    @Override
    public int getItemCount() {
        return acceptedReferralList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_contact_name;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_contact_name = itemView.findViewById(R.id.tv_contact_name);


        }
    }
}