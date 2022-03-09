package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.Faq;

import java.util.List;

public class FaqQueAnsAdapter extends RecyclerView.Adapter<FaqQueAnsAdapter.HorizontalViewHolder> {

    private Context context;
    private List<Faq> faqList;

    public FaqQueAnsAdapter(Context context, List<Faq> faqList) {
        this.context = context;
        this.faqList = faqList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.faq_que_answer, parent, false);
        return new HorizontalViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            holder.tv_faq_que.setText(Html.fromHtml(faqList.get(position).getFaqQuestion(), Html.FROM_HTML_MODE_COMPACT));
            holder.tv_faq_ans.setText(Html.fromHtml(faqList.get(position).getFaqAnswer(), Html.FROM_HTML_MODE_COMPACT));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_faq_que, tv_faq_ans;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_faq_que = itemView.findViewById(R.id.tv_faq_que);
            tv_faq_ans = itemView.findViewById(R.id.tv_faq_ans);

        }
    }
}