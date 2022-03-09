package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.TestimonialList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TesimonialAdapter extends RecyclerView.Adapter<TesimonialAdapter.HorizontalViewHolder> {

    private Context context;

    private List<TestimonialList>testimonialLists;

    private String testimonialImagePAth;

    public TesimonialAdapter(Context context, List<TestimonialList> testimonialLists, String testimonialImagePAth) {
        this.context = context;
        this.testimonialLists = testimonialLists;
        this.testimonialImagePAth = testimonialImagePAth;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.testimonial_list_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, int position) {

        try {


            String image_url = testimonialImagePAth + testimonialLists.get(position).getTestimonialImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.imageView_User);


            holder.textView_UserName.setText(testimonialLists.get(position).getTestimonialCustName());

            holder.textView_UserAddress.setText(testimonialLists.get(position).getTestimonialCustAddress());

       //     holder.tv_testmonial_desc.setText(testimonialLists.get(position).getTestimonialDetails());

           holder. tv_testmonial_desc.setText(""+ HtmlCompat.fromHtml(testimonialLists.get(0).getTestimonialDetails(), 0));



        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return testimonialLists.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
       private ImageView imageView_User;

       private TextView textView_UserName,textView_UserAddress,tv_testmonial_desc;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView_User = itemView.findViewById(R.id.imageView_User);
            textView_UserName = itemView.findViewById(R.id.textView_UserName);
            textView_UserAddress = itemView.findViewById(R.id.textView_UserAddress);
            tv_testmonial_desc = itemView.findViewById(R.id.tv_testmonial_desc);
        }
    }
}