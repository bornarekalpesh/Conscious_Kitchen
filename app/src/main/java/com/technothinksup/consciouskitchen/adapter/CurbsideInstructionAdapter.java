package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.CurbsideConfirmAndPayFragment;
import com.technothinksup.consciouskitchen.model.CurbsideInstruction;

import java.util.List;

public class CurbsideInstructionAdapter extends RecyclerView.Adapter<CurbsideInstructionAdapter.MyViewHolder> {

    Context context;
    List<CurbsideInstruction> curbsideInstructionList;

    public CurbsideInstructionAdapter(Context context, List<CurbsideInstruction> curbsideInstructionList) {
        this.context = context;
        this.curbsideInstructionList = curbsideInstructionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.curbside_instruction_item, parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            holder.tv_car_color.setText(curbsideInstructionList.get(position).getCarColor());
            holder.tv_car_model.setText(curbsideInstructionList.get(position).getCarModel());
            holder.tv_car_make.setText(curbsideInstructionList.get(position).getCarMake());
            holder.tv_curbside_instruction.setText(curbsideInstructionList.get(position).getCurbsideInstruction());

            holder.itemView.setOnClickListener(v -> {
                CurbsideConfirmAndPayFragment.car_color = curbsideInstructionList.get(position).getCarColor();
                CurbsideConfirmAndPayFragment.car_make = curbsideInstructionList.get(position).getCarMake();
                CurbsideConfirmAndPayFragment.car_model = curbsideInstructionList.get(position).getCarModel();
                CurbsideConfirmAndPayFragment.instruction_status = "1";
                ((HomePageActivity) context).loadFragment(new CurbsideConfirmAndPayFragment(), true);
               /* CurbsideConfirmAndPayFragment curbsideConfirmAndPayFragment = new CurbsideConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("car_color", curbsideInstructionList.get(position).getCarColor());
                bundle.putString("car_make", curbsideInstructionList.get(position).getCarMake());
                bundle.putString("car_model", curbsideInstructionList.get(position).getCarModel());
                curbsideConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) context).loadFragment(curbsideConfirmAndPayFragment, true);*/
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return curbsideInstructionList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_car_make, tv_car_model, tv_car_color, tv_curbside_instruction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_car_make = itemView.findViewById(R.id.tv_car_make);
            tv_car_model = itemView.findViewById(R.id.tv_car_model);
            tv_car_color = itemView.findViewById(R.id.tv_car_color);
            tv_curbside_instruction = itemView.findViewById(R.id.tv_curbside_instruction);
        }
    }
}
