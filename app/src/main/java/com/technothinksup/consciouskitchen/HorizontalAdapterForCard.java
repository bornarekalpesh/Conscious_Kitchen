package com.technothinksup.consciouskitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalAdapterForCard extends RecyclerView.Adapter<HorizontalAdapterForCard.HorizontalViewHolder> {

    private int[] items;
    Context context;


    public HorizontalAdapterForCard(int[] items) {
        this.items = items;
    }

    @Override
    public HorizontalAdapterForCard.HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_layout_recycler, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalAdapterForCard.HorizontalViewHolder holder, int position) {
        holder.imageView_CardImages.setImageResource(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_CardImages;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView_CardImages = itemView.findViewById(R.id.imageView_CardImages);
        }
    }
}