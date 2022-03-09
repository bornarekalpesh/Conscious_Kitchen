package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.ChoiceValue;

import java.util.List;

public class ChoiceValueAdapter extends RecyclerView.Adapter<ChoiceValueAdapter.HorizontalViewHolder> {

    private Context context;

    private List<ChoiceValue> choiceValueList;

    private View view;

    public static String choice_value_id="", choice_value_name="";


    public ChoiceValueAdapter(Context context, List<ChoiceValue> choiceValueList) {
        this.context = context;
        this.choiceValueList = choiceValueList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.choice_value_item_list, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {
            if (choiceValueList.get(position).getChoiceValueName().equalsIgnoreCase("Add")) {

                holder.tv_choice_value_name.setText(choiceValueList.get(position).getChoiceValueName() + "\n" + "+");
                holder.tv_tap_to_add.setVisibility(View.VISIBLE);
                holder.relativeLayout_back.setBackground(context.getResources().getDrawable(R.drawable.add_bg_yellow));


            } else if (choiceValueList.get(position).getChoiceValueName().equalsIgnoreCase("Remove")) {

                holder.tv_choice_value_name.setText(choiceValueList.get(position).getChoiceValueName() + "\n" + "-");
                holder.tv_tap_to_add.setVisibility(View.VISIBLE);
                holder.relativeLayout_back.setBackground(context.getResources().getDrawable(R.drawable.button_bg_yellow));

            } else if (choiceValueList.get(position).getChoiceValueName().equalsIgnoreCase("Extra")) {

                holder.relativeLayout_back.setBackground(context.getResources().getDrawable(R.drawable.add_bg_yellow));
                holder.tv_choice_value_name.setText(choiceValueList.get(position).getChoiceValueName());
            } else if (choiceValueList.get(position).getChoiceValueName().equalsIgnoreCase("Regular")) {

                holder.relativeLayout_back.setBackground(context.getResources().getDrawable(R.drawable.button_bg_yellow));
                holder.tv_choice_value_name.setText(choiceValueList.get(position).getChoiceValueName());

            } else if (choiceValueList.get(position).getChoiceValueName().equalsIgnoreCase("Light")) {

                holder.relativeLayout_back.setBackground(context.getResources().getDrawable(R.drawable.none_xml));
                holder.tv_choice_value_name.setText(choiceValueList.get(position).getChoiceValueName());

            }


            holder.relativeLayout_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //Toast.makeText(context, "click=="+choiceValueList.get(position).getChoiceValueId()+":"+choiceValueList.get(position).getChoiceValueName(), Toast.LENGTH_SHORT).show();
                    choice_value_id=choiceValueList.get(position).getChoiceValueId();
                    choice_value_name=choiceValueList.get(position).getChoiceValueName();

                }
            });









           /* holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();



                   *//* MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food_cat_id", categoryLists.get(position).getFoodCategoryId());
                    bundle.putString("food_cat_name", categoryLists.get(position).getFoodCategoryName());
                    menuSubCategoryfragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(menuSubCategoryfragment, true);*//*

                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return choiceValueList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView tv_choice_value_name, tv_tap_to_add;

        private RelativeLayout relativeLayout_back, relativeLayout_CustomizeAddOne;

        private RelativeLayout relativeLayout_choice_item;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            tv_choice_value_name = itemView.findViewById(R.id.tv_choice_value_name);
            tv_tap_to_add = itemView.findViewById(R.id.tv_tap_to_add);
            relativeLayout_back = itemView.findViewById(R.id.relativeLayout_back);
            relativeLayout_CustomizeAddOne = itemView.findViewById(R.id.relativeLayout_CustomizeAddOne);
            relativeLayout_choice_item = itemView.findViewById(R.id.relativeLayout_choice_item);

        }
    }
}