package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.BranchList;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.MyViewHolder> {

  private   Context context;

  private List<BranchList>branchLists;

    public BranchAdapter(Context context, List<BranchList> branchLists) {
        this.context = context;
        this.branchLists = branchLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.branch_list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        try {

            holder.tv_branch_name.setText(branchLists.get(position).getBranchName());
            holder.tv_branch_address.setText(branchLists.get(position).getBranchAddress());
            holder.tv_branch_email.setText(branchLists.get(position).getBranchEmail());
            holder.tv_branch_number.setText(branchLists.get(position).getBranchMobile());


            holder.tv_set_as_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse>call=apiInterface.setAsDefaultBranch(HomePageActivity.userId,branchLists.get(position).getBranchId());

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return branchLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_branch_name,tv_branch_address,tv_branch_number,tv_branch_email,tv_set_as_default;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_branch_name = itemView.findViewById(R.id.tv_branch_name);
            tv_branch_address = itemView.findViewById(R.id.tv_branch_address);
            tv_branch_number = itemView.findViewById(R.id.tv_branch_number);
            tv_branch_email = itemView.findViewById(R.id.tv_branch_email);
            tv_set_as_default = itemView.findViewById(R.id.tv_set_as_default);


        }
    }
}
