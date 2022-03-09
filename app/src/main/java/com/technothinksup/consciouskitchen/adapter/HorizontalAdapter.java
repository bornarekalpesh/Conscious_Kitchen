package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.EventDetailFragment;
import com.technothinksup.consciouskitchen.model.EventList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private Context context;

    private List<EventList>eventLists;

    private String eventImagePath;

    public HorizontalAdapter(Context context, List<EventList> eventLists, String eventImagePath) {
        this.context = context;
        this.eventLists = eventLists;
        this.eventImagePath=eventImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout_recycler, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            String image_url = eventImagePath + eventLists.get(position).getEventImage();


            Picasso.with(context)
                    .load(image_url)
                    .into(holder.imageView_EventImages);

            holder.textView_EventAdapterTitle.setText(eventLists.get(position).getEventName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();

                    EventDetailFragment eventDetailFragment = new EventDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", eventLists.get(position).getEventId());
                    eventDetailFragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(eventDetailFragment, true);


                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return eventLists.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_EventImages;
        TextView textView_EventAdapterTitle;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView_EventImages = itemView.findViewById(R.id.imageView_EventImages);
            textView_EventAdapterTitle = itemView.findViewById(R.id.textView_EventAdapterTitle);
        }
    }
}