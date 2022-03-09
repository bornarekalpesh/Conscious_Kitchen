package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.FaqCategory;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.HorizontalViewHolder> {

    private Context context;
    private List<FaqCategory> faqCategoryList;

    boolean layoutStatus = false;

    private FaqQueAnsAdapter faqQueAnsAdapter;


    public FaqAdapter(Context context, List<FaqCategory> faqCategoryList) {
        this.context = context;
        this.faqCategoryList = faqCategoryList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.faq_category_list_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_faq_category.setText(faqCategoryList.get(position).getFaqCategoryName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutStatus) {

                        holder.linear_layout_submenu_layout.setVisibility(View.GONE);
                        layoutStatus = false;

                    } else {

                        holder.linear_layout_submenu_layout.setVisibility(View.VISIBLE);
                        layoutStatus = true;
                    }
                }
            });


            faqQueAnsAdapter = new FaqQueAnsAdapter(context, faqCategoryList.get(position).getFaqList());
            holder.rv_raq_que_ans.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.rv_raq_que_ans.setAdapter(faqQueAnsAdapter);
            faqQueAnsAdapter.notifyDataSetChanged();
            holder.rv_raq_que_ans.setHasFixedSize(true);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getData() {


    }

    @Override
    public int getItemCount() {
        return faqCategoryList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cat_icon;
        TextView tv_faq_category;
        LinearLayout linear_layout_submenu_layout;
        RecyclerView rv_raq_que_ans;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            iv_cat_icon = itemView.findViewById(R.id.iv_cat_icon);
            tv_faq_category = itemView.findViewById(R.id.tv_faq_category);
            linear_layout_submenu_layout = itemView.findViewById(R.id.linear_layout_submenu_layout);
            rv_raq_que_ans = itemView.findViewById(R.id.rv_raq_que_ans);
        }
    }
}