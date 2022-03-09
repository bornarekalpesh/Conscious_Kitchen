package com.technothinksup.consciouskitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.fragments.AddTasteFragment;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.Taste;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasteAdapter extends RecyclerView.Adapter<TasteAdapter.MyViewHolder> {

   private Context context;

   private List<Taste>tasteList;

    public TasteAdapter(Context context, List<Taste> tasteList) {
        this.context = context;
        this.tasteList = tasteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.taste_list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        try {


            holder.tv_taste.setText(tasteList.get(position).getTasteName());

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse>call=apiInterface.deleteTaste(HomePageActivity.userId,tasteList.get(position).getTasteId());

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            if (response.body().getStatus()==1){
                                Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                ((HomePageActivity)context).loadFragment(new AddTasteFragment(),true);
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });
                }
            });

            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AddTasteFragment.et_add_taste.setText(tasteList.get(position).getTasteName());
                    AddTasteFragment.button_add_taste.setText("Update");



                }
            });

            AddTasteFragment.button_add_taste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(AddTasteFragment.button_add_taste.getText().toString().equalsIgnoreCase("Update")){
                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                        Call<CommonResponse>call=apiInterface.updateTaste(HomePageActivity.userId,tasteList.get(position).getTasteId(), AddTasteFragment.et_add_taste.getText().toString());

                        call.enqueue(new Callback<CommonResponse>() {
                            @Override
                            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                                if (response.body().getStatus()==1){
                                    Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    ((HomePageActivity)context).loadFragment(new AddTasteFragment(),true);
                                }





                            }

                            @Override
                            public void onFailure(Call<CommonResponse> call, Throwable t) {

                            }
                        });
                    }else {
                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                        Call<CommonResponse>call=apiInterface.addTaste(HomePageActivity.userId,AddTasteFragment.et_add_taste.getText().toString());

                        call.enqueue(new Callback<CommonResponse>() {
                            @Override
                            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                                if(response.body().getStatus()==1){
                                    Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    ((HomePageActivity)context).loadFragment(new AddTasteFragment(),true);
                                }



                            }

                            @Override
                            public void onFailure(Call<CommonResponse> call, Throwable t) {

                            }
                        });
                    }

                }
            });







        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return tasteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


      private   TextView tv_taste;

      private ImageView iv_delete,iv_edit;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_taste = itemView.findViewById(R.id.tv_taste);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_edit=itemView.findViewById(R.id.iv_edit);


        }
    }
}
