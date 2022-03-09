package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.ChoiceValueAdapter;
import com.technothinksup.consciouskitchen.adapter.CustomisedMenuAdapter;
import com.technothinksup.consciouskitchen.adapter.ProductDeatilCategoryAdapter;
import com.technothinksup.consciouskitchen.model.CategoryResponse;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.CustomizedMenuResponse;
import com.technothinksup.consciouskitchen.model.ItemDetail;
import com.technothinksup.consciouskitchen.model.ItemDetailResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.adapter.CustomisedMenuAdapter.customize_item_id;
import static com.technothinksup.consciouskitchen.adapter.CustomisedMenuAdapter.customize_item_name;

public class ProductDetailFragment extends Fragment {


    private View view;
    private ImageView imageVIew_product, ImageView_Customise, imageView_ProductOneArrow, imageView_ProductTwoArrow,
            imageView_CustomizeExtraAddOne, imageView_CustomiseProductOne, imageView_CustomiseProductTwo, imageView_CustomiseProductThree, imageView_BackProductDetails, imageView_RewardProductDetails, imageView_CartProductDetails;

    private String item_id="", item_name="";

    private TextView textView_ProductTitle, textView_ProductDetail, textView_Customize, textView_ProductDetailAddToCart,textView_CustomizeRate;

    private LinearLayout linearLayout_CustomiseProduct, linearLayout_Product1, linearLayout_Product2;

    private RelativeLayout relativeLayout_CustomiseProductOne, relativeLayout_CustomiseProductTwo, relativeLayout_CustomiseProductThree, relativeLayout_CustomizeExtraTwo, relativeLayout_CustomizeExtraThree, relativeLayout_CustomizeExtraAddOne;

    private CardView cardView_ProductDetailAddToCart;

    private RecyclerView rv_category, rv_customised_item_list;

    private ProductDeatilCategoryAdapter productDeatilCategoryAdapter;

    private CustomisedMenuAdapter customisedMenuAdapter;

    private List<ItemDetail> itemDetailList = new ArrayList<>();

    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, container, false);


        init();

        try {
            Bundle bundle = getArguments();
            item_id = bundle.getString("item_id");
            item_name = bundle.getString("item_name");
            textView_ProductTitle.setText("" + item_name);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // setData();

        setItemDetailData();

        setCustomiseData();


        setCategoryAdapter();
        final TextView textView_ProductDetailTitle = (TextView) view.findViewById(R.id.textView_ProductDetailTitle);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        imageView_BackProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartProductDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        imageView_CustomiseProductOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout_CustomizeExtraAddOne.setVisibility(View.VISIBLE);
            }
        });

        imageView_CustomizeExtraAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout_CustomizeExtraAddOne.setVisibility(View.GONE);
            }
        });


//        imageView_CustomiseProductTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relativeLayout_CustomizeExtraAddTwo.setVisibility(View.VISIBLE);
//            }
//        });
//
//        imageView_CustomizeExtraAddTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relativeLayout_CustomizeExtraAddTwo.setVisibility(View.GONE);
//            }
//        });
//
//        imageView_CustomiseProductThree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relativeLayout_CustomizeExtraAddThree.setVisibility(View.VISIBLE);
//            }
//        });
//
//        imageView_CustomizeExtraAddThree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                relativeLayout_CustomizeExtraAddThree.setVisibility(View.GONE);
//            }
//        });


//        relativeLayout_CustomiseProductOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (relativeLayout_CustomizeExtraOne.getVisibility() == View.GONE)
//                {
//                    relativeLayout_CustomizeExtraOne.setVisibility(View.VISIBLE);
//
//                }
//                else
//                {
//                    relativeLayout_CustomizeExtraOne.setVisibility(View.GONE);
//
//                }
//            }
//        });

        relativeLayout_CustomiseProductTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeLayout_CustomizeExtraTwo.getVisibility() == View.GONE) {
                    relativeLayout_CustomizeExtraTwo.setVisibility(View.VISIBLE);

                } else {
                    relativeLayout_CustomizeExtraTwo.setVisibility(View.GONE);

                }
            }
        });

        relativeLayout_CustomiseProductThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeLayout_CustomizeExtraThree.getVisibility() == View.GONE) {
                    relativeLayout_CustomizeExtraThree.setVisibility(View.VISIBLE);

                } else {
                    relativeLayout_CustomizeExtraThree.setVisibility(View.GONE);

                }
            }
        });


        imageView_ProductOneArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout_Product1.getVisibility() == View.GONE) {
                    linearLayout_Product1.setVisibility(View.VISIBLE);

                } else {
                    linearLayout_Product1.setVisibility(View.GONE);

                }
            }
        });

        imageView_ProductTwoArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout_Product2.getVisibility() == View.GONE) {
                    linearLayout_Product2.setVisibility(View.VISIBLE);

                } else {
                    linearLayout_Product2.setVisibility(View.GONE);

                }
            }
        });


        textView_Customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout_CustomiseProduct.getVisibility() == View.GONE) {
                    linearLayout_CustomiseProduct.setVisibility(View.VISIBLE);
                    textView_ProductDetailTitle.setText("Customize Order");
                    textView_ProductDetailAddToCart.setText("Add To Cart");
                } else {
                    linearLayout_CustomiseProduct.setVisibility(View.GONE);
                    textView_ProductDetailTitle.setText("The Gobble");
                    textView_ProductDetailAddToCart.setText("Continue To Pay");
                }
            }
        });

        cardView_ProductDetailAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView_ProductDetailAddToCart.getText().toString().matches("Add To Cart")) {

                    Toast.makeText(getActivity(),"choice_set_id == "+ ChoiceValueAdapter.choice_value_id,Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"customize_item_name == "+ ChoiceValueAdapter.choice_value_name,Toast.LENGTH_LONG).show();

                    try {
                        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                        Call<CommonResponse> call = apiInterface.addToCart(HomePageActivity.userId, item_id, item_name, itemDetailList.get(0).getItemVariation().get(0).getItemVariationId(), itemDetailList.get(0).getItemVariation().get(0).getItemVariationName(), Float.valueOf(itemDetailList.get(0).getItemVariation().get(0).getItemVariationPrice()),
                                CustomisedMenuAdapter.choice_set_id, CustomisedMenuAdapter.choice_set_name, CustomisedMenuAdapter.choice_set_kitchen_name, ChoiceValueAdapter.choice_value_id, ChoiceValueAdapter.choice_value_name, customize_item_id, customize_item_name,
                                (int) CustomisedMenuAdapter.customize_item_amount, 1, itemDetailList.get(0).getTaxRateId(), 1);


                        call.enqueue(new Callback<CommonResponse>() {
                            @Override
                            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                                //Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();


                                if (response.body().getStatus() == 1) {

                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    loadReviewOrderFragment();
                                }
                            }

                            @Override
                            public void onFailure(Call<CommonResponse> call, Throwable t) {

                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }







                } else {
                    loadPaymentMethodFragment();
                }


                // loadReviewOrderFragment();


            }
        });

        ImageView_Customise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout_CustomiseProduct.getVisibility() == View.GONE) {
                    linearLayout_CustomiseProduct.setVisibility(View.VISIBLE);
                    textView_ProductDetailTitle.setText("Customize Order");
                    textView_ProductDetailAddToCart.setText("Add To Cart");
                } else {
                    linearLayout_CustomiseProduct.setVisibility(View.GONE);
                    textView_ProductDetailTitle.setText("The Gobble");
                    textView_ProductDetailAddToCart.setText("Continue To Pay");
                }
            }
        });

        return view;
    }

    private void setItemDetailData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<ItemDetailResponse> call = apiInterface.getItemDetail(item_id);
        call.enqueue(new Callback<ItemDetailResponse>() {
            @Override
            public void onResponse(Call<ItemDetailResponse> call, Response<ItemDetailResponse> response) {

                if (response.body().getStatus() == 1) {

                    String image_url = response.body().getItemImagePath() + response.body().getItemDetails().get(0).getItemImage();


                    itemDetailList = response.body().getItemDetails();


                    Picasso.with(getActivity())
                            .load(image_url)
                            .into(imageVIew_product);

                    textView_ProductTitle.setText(response.body().getItemDetails().get(0).getItemName());
                    textView_ProductDetail.setText(response.body().getItemDetails().get(0).getItemDescr());

                    textView_CustomizeRate.setText("$"+itemDetailList.get(0).getItemVariation().get(0).getItemVariationPrice());

                    if (response.body().getIs_item_customize().equalsIgnoreCase("1")) {

                        textView_Customize.setVisibility(View.VISIBLE);
                        ImageView_Customise.setVisibility(View.VISIBLE);


                    }

                }


            }

            @Override
            public void onFailure(Call<ItemDetailResponse> call, Throwable t) {

            }
        });
    }

    private void setCustomiseData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CustomizedMenuResponse> call = apiInterface.getCustomizedMenuList(item_id);

        call.enqueue(new Callback<CustomizedMenuResponse>() {
            @Override
            public void onResponse(Call<CustomizedMenuResponse> call, Response<CustomizedMenuResponse> response) {


                customisedMenuAdapter = new CustomisedMenuAdapter(getActivity(), response.body().getCustomizedMenuItemList(), response.body().getImagePath());
                rv_customised_item_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_customised_item_list.setAdapter(customisedMenuAdapter);
                customisedMenuAdapter.notifyDataSetChanged();
                rv_customised_item_list.setHasFixedSize(true);


            }

            @Override
            public void onFailure(Call<CustomizedMenuResponse> call, Throwable t) {

            }
        });

    }

    private void init() {

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        imageVIew_product = view.findViewById(R.id.imageVIew_product);
        textView_ProductTitle = view.findViewById(R.id.textView_ProductTitle);
        textView_ProductDetail = view.findViewById(R.id.textView_ProductDetail);
        textView_CustomizeRate = view.findViewById(R.id.textView_CustomizeRate);


        textView_Customize = view.findViewById(R.id.textView_Customize);
        textView_ProductDetailAddToCart = view.findViewById(R.id.textView_ProductDetailAddToCart);
        ImageView_Customise = view.findViewById(R.id.ImageView_Customise);
        imageView_ProductOneArrow = view.findViewById(R.id.imageView_ProductOneArrow);
        imageView_ProductTwoArrow = view.findViewById(R.id.imageView_ProductTwoArrow);

        linearLayout_CustomiseProduct = view.findViewById(R.id.linearLayout_CustomiseProduct);
        linearLayout_Product1 = view.findViewById(R.id.linearLayout_Product1);
        linearLayout_Product2 = view.findViewById(R.id.linearLayout_Product2);

        relativeLayout_CustomiseProductOne = view.findViewById(R.id.relativeLayout_CustomiseProductOne);
        relativeLayout_CustomiseProductTwo = view.findViewById(R.id.relativeLayout_CustomiseProductTwo);
        relativeLayout_CustomiseProductThree = view.findViewById(R.id.relativeLayout_CustomiseProductThree);

        relativeLayout_CustomizeExtraTwo = view.findViewById(R.id.relativeLayout_CustomizeExtraTwo);
        relativeLayout_CustomizeExtraThree = view.findViewById(R.id.relativeLayout_CustomizeExtraThree);
        cardView_ProductDetailAddToCart = view.findViewById(R.id.cardView_ProductDetailAddToCart);

        relativeLayout_CustomizeExtraAddOne = view.findViewById(R.id.relativeLayout_CustomizeExtraAddOne);

        imageView_CustomizeExtraAddOne = view.findViewById(R.id.imageView_CustomizeExtraAddOne);

        imageView_CustomiseProductOne = view.findViewById(R.id.imageView_CustomiseProductOne);
        imageView_CustomiseProductTwo = view.findViewById(R.id.imageView_CustomiseProductTwo);
        imageView_CustomiseProductThree = view.findViewById(R.id.imageView_CustomiseProductThree);

        imageView_BackProductDetails = view.findViewById(R.id.imageView_BackProductDetails);
        imageView_RewardProductDetails = view.findViewById(R.id.imageView_RewardProductDetails);
        imageView_CartProductDetails = view.findViewById(R.id.imageView_CartProductDetails);


        rv_category = view.findViewById(R.id.rv_category);
        rv_customised_item_list = view.findViewById(R.id.rv_customised_item_list);

    }


    private void setCategoryAdapter() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        Call<CategoryResponse> call = apiInterface.getCategoryList();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

             //   Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();

                if (response.body().getStatus() == 1) {

                    productDeatilCategoryAdapter = new ProductDeatilCategoryAdapter(getActivity(), response.body().getCategoryList(), response.body().getCategoryImagePath());
                    rv_category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    rv_category.setAdapter(productDeatilCategoryAdapter);
                    productDeatilCategoryAdapter.notifyDataSetChanged();
                    rv_category.setHasFixedSize(true);


                }
                else
                    {

                }


            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void loadReviewOrderFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadPaymentMethodFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, PaymentMethodFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardProcessFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

}
