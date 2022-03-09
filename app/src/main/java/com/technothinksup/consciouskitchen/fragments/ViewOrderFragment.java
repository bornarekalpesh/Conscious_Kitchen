package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.OrderDetailResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewOrderFragment extends Fragment {

    private View view;

    private TextView tv_order_no,tv_order_date,tv_customer_Name,tv_email,tv_order_type,tv_delivery_address,tv_car_make,tv_car_model,tv_car_care,tv_pick_up_time,tv_order_basic_amt,tv_order_tax_amt,tv_order_total_amt,tv_used_coupon_amt,tv_used_coupon_code,tv_used_wallet_amt,tv_used_order_status;

    private LinearLayout linearLayout_delivery,linear_layout_curbside;

    private String order_id;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_view_order, container, false);

        init();

        Bundle bundle=getArguments();

        order_id=bundle.getString("order_id");

       // Toast.makeText(getContext(), ""+order_id, Toast.LENGTH_SHORT).show();

        getOrderDetail();

        imageBackListener();


        return view;
    }

    private void getOrderDetail() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<OrderDetailResponse>call=apiInterface.getOrderDetail(HomePageActivity.userId,order_id);

        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
              //  Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();

                String name= Common.getSavedUserData(getActivity(),"fName") +Common.getSavedUserData(getActivity(),"lName");

                tv_order_no.setText("#00"+response.body().getOrderDetails().get(0).getOrderNo());
                tv_order_date.setText(""+response.body().getOrderDetails().get(0).getOrderDate().substring(0,10));

                tv_customer_Name.setText(name);
                tv_email.setText(""+Common.getSavedUserData(getActivity(),"email"));
                if(response.body().getOrderDetails().get(0).getOrderType().equalsIgnoreCase("1")){
                    tv_order_type.setText("PickUp");
                }else if(response.body().getOrderDetails().get(0).getOrderType().equalsIgnoreCase("2")){
                    tv_order_type.setText("Delivery");

                    linearLayout_delivery.setVisibility(View.VISIBLE);

                    tv_delivery_address.setText(response.body().getOrderDetails().get(0).getDeliveryAddress());
                } else if(response.body().getOrderDetails().get(0).getOrderType().equalsIgnoreCase("3")){
                    tv_order_type.setText("Curbside");

                    linear_layout_curbside.setVisibility(View.VISIBLE);

                    tv_car_make.setText(response.body().getOrderDetails().get(0).getCarMake());
                    tv_car_model.setText(response.body().getOrderDetails().get(0).getCarModel());
                    tv_car_care.setText(response.body().getOrderDetails().get(0).getCarColor());



                }

                tv_pick_up_time.setText(response.body().getOrderDetails().get(0).getPickupDeliveryTime());

                tv_order_basic_amt.setText(response.body().getOrderDetails().get(0).getOrderBasicAmount());

                tv_order_tax_amt.setText(response.body().getOrderDetails().get(0).getOrderTaxAmount());

                tv_order_total_amt.setText(response.body().getOrderDetails().get(0).getOrderTotalAmount());

                tv_used_coupon_amt.setText(response.body().getOrderDetails().get(0).getUsedCouponAmount());

                tv_used_coupon_code.setText(response.body().getOrderDetails().get(0).getUsedCouponCode());

                tv_used_wallet_amt.setText(response.body().getOrderDetails().get(0).getUsedWalletAmount());

                tv_used_wallet_amt.setText(response.body().getOrderDetails().get(0).getUsedWalletAmount());

                tv_used_order_status.setText(response.body().getOrderDetails().get(0).getOrderStatus());

            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {

            }
        });
    }
    private void imageBackListener() {
        ImageView imageView_Back=view.findViewById(R.id.imageView_Back);

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }

    private void init() {
        tv_order_no=view.findViewById(R.id.tv_order_no);
        tv_order_date=view.findViewById(R.id.tv_order_date);
        tv_customer_Name=view.findViewById(R.id.tv_customer_Name);
        tv_email=view.findViewById(R.id.tv_email);
        tv_order_type=view.findViewById(R.id.tv_order_type);
        tv_delivery_address=view.findViewById(R.id.tv_delivery_address);
        tv_car_make=view.findViewById(R.id.tv_car_make);
        tv_car_model=view.findViewById(R.id.tv_car_model);
        tv_car_care=view.findViewById(R.id.tv_car_care);
        tv_pick_up_time=view.findViewById(R.id.tv_pick_up_time);
        tv_order_basic_amt=view.findViewById(R.id.tv_order_basic_amt);
        tv_order_tax_amt=view.findViewById(R.id.tv_order_tax_amt);
        tv_order_total_amt=view.findViewById(R.id.tv_order_total_amt);
        tv_used_coupon_amt=view.findViewById(R.id.tv_used_coupon_amt);
        tv_used_coupon_code=view.findViewById(R.id.tv_used_coupon_code);
        tv_used_wallet_amt=view.findViewById(R.id.tv_used_wallet_amt);
        tv_used_order_status=view.findViewById(R.id.tv_used_order_status);
        linearLayout_delivery=view.findViewById(R.id.linearLayout_delivery);
        linear_layout_curbside=view.findViewById(R.id.linear_layout_curbside);
    }
}