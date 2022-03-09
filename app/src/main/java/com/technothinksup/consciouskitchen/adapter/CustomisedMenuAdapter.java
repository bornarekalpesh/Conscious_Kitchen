package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.CustomizedMenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CustomisedMenuAdapter extends RecyclerView.Adapter<CustomisedMenuAdapter.HorizontalViewHolder> {

    private Context context;
    boolean flag=false;
    public static List<CustomizedMenuItem> customizedMenuItemList;
    private String customisedMenuImagePath;
    private ChoiceValueAdapter choiceValueAdapter;
    public static  String customize_item_id="",customize_item_name="",choice_set_id="",choice_set_name="",choice_set_kitchen_name="";
    public static  float customize_item_amount=0;
    public static  int customize_status=0;


    public CustomisedMenuAdapter(Context context, List<CustomizedMenuItem> customizedMenuItemList, String customisedMenuImagePath) {
        this.context = context;
        this.customizedMenuItemList = customizedMenuItemList;
        this.customisedMenuImagePath = customisedMenuImagePath;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.customise_menu_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalViewHolder holder, final int position) {

        try {

            String image_url = customisedMenuImagePath + customizedMenuItemList.get(position).getCustomizeItemImage();
            Picasso.with(context)
                    .load(image_url)
                    .into(holder.imageView_CustomizeProductItemOne);
            holder.textView_CustomiseItemNameOne.setText(customizedMenuItemList.get(position).getCustomizeItemName());
            holder.tv_customize_item_descr.setText(customizedMenuItemList.get(position).getCustomizeItemDescr());


/*
holder.relativeLayout_CustomiseProductOne.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(flag){
            flag=false;
        holder.relativeLayout_CustomizeAddOne.setVisibility(View.GONE);
        }
        else
        {
            flag=true;
            holder.relativeLayout_CustomizeAddOne.setVisibility(View.VISIBLE);
        }
    }
});

holder.rladd1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.imageView_CustomiseSelectProduct.setVisibility(View.VISIBLE);
        holder.relativeLayout_CustomizeAddOne.setVisibility(View.GONE);

    }
});

holder.rlremove1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.imageView_CustomiseSelectProduct.setVisibility(View.GONE);
        holder.relativeLayout_CustomizeAddOne.setVisibility(View.GONE);


    }
});
*/


            holder.relativeLayout_CustomiseProductOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   /* if (customizedMenuItemList.get(position).getChoiceSetName().equalsIgnoreCase("Add")) {
                        //  holder.relativeLayout_CustomizeAddOne.setVisibility(View.VISIBLE);
                    } else {
                        // holder.relativeLayout_CustomizeExtra.setVisibility(View.VISIBLE);
                    }
*/
                    if(flag){
                        flag=false;
                        holder.relativeLayout_choiceList.setVisibility(View.GONE);
                       // holder.relativeLayout_CustomizeAddOne.setVisibility(View.GONE);
                    }
                    else
                    {
                        flag=true;
                        holder.relativeLayout_choiceList.setVisibility(View.VISIBLE);
                        // holder.relativeLayout_CustomizeAddOne.setVisibility(View.VISIBLE);

                        customize_status=1;
                        customize_item_id=customizedMenuItemList.get(position).getCustomizeItemId();
                        Toast.makeText(context,"choice_set_id == "+customize_item_id,Toast.LENGTH_LONG).show();
                        customize_item_name=customizedMenuItemList.get(position).getCustomizeItemName();
                        Toast.makeText(context,"customize_item_name == "+customize_item_name,Toast.LENGTH_LONG).show();
                        customize_item_amount= Float.valueOf(customizedMenuItemList.get(position).getCustomizeItemPrice());
                        setChoiceAdapter(position, holder);


                        if (customizedMenuItemList.get(position).getChoiceSetName().equalsIgnoreCase("Add")) {
                            //  holder.relativeLayout_CustomizeAddOne.setVisibility(View.VISIBLE);
                            customize_item_id=customizedMenuItemList.get(position).getCustomizeItemId();
                            Toast.makeText(context,"choice_set_id == "+customize_item_id,Toast.LENGTH_LONG).show();
                            customize_item_name=customizedMenuItemList.get(position).getCustomizeItemName();
                            Toast.makeText(context,"customize_item_name == "+customize_item_name,Toast.LENGTH_LONG).show();
                            customize_item_amount= Float.valueOf(customizedMenuItemList.get(position).getCustomizeItemPrice());
                            choice_set_id=customizedMenuItemList.get(position).getChoiceSetId();
                            choice_set_name=customizedMenuItemList.get(position).getChoiceSetName();
                            choice_set_kitchen_name=customizedMenuItemList.get(position).getChoiceSetKitchenName();

                        } else if(customizedMenuItemList.get(position).getChoiceSetName().equalsIgnoreCase("Remove")) {
                            customize_item_id="";
                            Toast.makeText(context,"choice_set_id == "+customize_item_id,Toast.LENGTH_LONG).show();
                            customize_item_name="";
                            Toast.makeText(context,"customize_item_name == "+customize_item_name,Toast.LENGTH_LONG).show();
                            customize_item_amount= Float.valueOf(customizedMenuItemList.get(position).getCustomizeItemPrice());
                            choice_set_id=customizedMenuItemList.get(position).getChoiceSetId();
                            choice_set_name=customizedMenuItemList.get(position).getChoiceSetName();
                            choice_set_kitchen_name=customizedMenuItemList.get(position).getChoiceSetKitchenName();
                            // holder.relativeLayout_CustomizeExtra.setVisibility(View.VISIBLE);
                        }
                     }


                }
            });
            /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();



                    MenuSubCategoryfragment menuSubCategoryfragment = new MenuSubCategoryfragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("food_cat_id", categoryLists.get(position).getFoodCategoryId());
                    bundle.putString("food_cat_name", categoryLists.get(position).getFoodCategoryName());
                    menuSubCategoryfragment.setArguments(bundle);
                    ((HomePageActivity) context).loadFragment(menuSubCategoryfragment, true);

                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.relativeLayout_CustomizeAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relativeLayout_CustomizeAddOne.setVisibility(View.GONE);
            }
        });
    }

    private void setChoiceAdapter(int position, HorizontalViewHolder holder) {

        choiceValueAdapter = new ChoiceValueAdapter(context, customizedMenuItemList.get(position).getChoiceValueList());
        holder.rv_choice_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_choice_list.setAdapter(choiceValueAdapter);

        choiceValueAdapter.notifyDataSetChanged();

        holder.rv_choice_list.setHasFixedSize(true);
    }


    @Override
    public int getItemCount() {
        return customizedMenuItemList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_CustomizeProductItemOne, imageView_CustomiseSelectProduct,ivaddchk;
        RelativeLayout relativeLayout_CustomiseProductOne;
        TextView textView_CustomiseItemNameOne, tv_customize_item_descr;
        RelativeLayout rladd1,rlremove1,relativeLayout_CustomizeAddOne, relativeLayout_CustomizeExtra, relativeLayout_choiceList;
        RecyclerView rv_choice_list;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView_CustomizeProductItemOne = itemView.findViewById(R.id.imageView_CustomizeProductItemOne);
            textView_CustomiseItemNameOne = itemView.findViewById(R.id.textView_CustomiseItemNameOne);
            tv_customize_item_descr = itemView.findViewById(R.id.tv_customize_item_descr);
            imageView_CustomiseSelectProduct = itemView.findViewById(R.id.imageView_CustomiseSelectProduct);
            ivaddchk = itemView.findViewById(R.id.addChk);

           relativeLayout_CustomizeAddOne = itemView.findViewById(R.id.relativeLayout_CustomizeAddOne);
            relativeLayout_CustomiseProductOne = itemView.findViewById(R.id.relativeLayout_CustomiseProductOne);

            relativeLayout_CustomizeExtra = itemView.findViewById(R.id.relativeLayout_CustomizeExtra);
            rladd1= itemView.findViewById(R.id.add1);
            rlremove1= itemView.findViewById(R.id.remove1);


            relativeLayout_choiceList = itemView.findViewById(R.id.relativeLayout_choiceList);
           rv_choice_list = itemView.findViewById(R.id.rv_choice_list);
        }
    }
}