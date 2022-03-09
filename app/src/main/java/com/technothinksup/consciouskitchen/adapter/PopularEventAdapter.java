package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.EventList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularEventAdapter extends RecyclerView.Adapter<PopularEventAdapter.HorizontalViewHolder> {

    private Context context;

    private List<EventList>eventLists;

    private String eventImagePath;

    public PopularEventAdapter(Context context, List<EventList> eventLists, String eventImagePath) {
        this.context = context;
        this.eventLists = eventLists;
        this.eventImagePath=eventImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.popular_item_layout_recycler, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, int position) {

        try {
            String image_url = eventImagePath + eventLists.get(position).getEventImage();
            Picasso.with(context)
                    .load(image_url)
                    .into(holder.imageView_EventImages);

            holder.textView_EventAdapterDate.setText(eventLists.get(position).getEventDate());
            holder.textView_EventAdapterTitle.setText(eventLists.get(position).getEventName());
            holder.tv_event_reward.setText("Get"+eventLists.get(position).getEventRewardPoint()+"X  Reward Points");
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
        TextView textView_EventAdapterTitle,textView_EventAdapterDate,tv_event_reward;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView_EventImages = itemView.findViewById(R.id.imageView_EventImages);
            textView_EventAdapterTitle = itemView.findViewById(R.id.textView_EventAdapterTitle);
            textView_EventAdapterDate = itemView.findViewById(R.id.textView_EventAdapterDate);
            tv_event_reward = itemView.findViewById(R.id.tv_event_reward);
        }
    }
}