package com.technothinksup.consciouskitchen.retrofit;


import com.technothinksup.consciouskitchen.model.AddGiftCardResponse;
import com.technothinksup.consciouskitchen.model.AddTestmonialResponse;
import com.technothinksup.consciouskitchen.model.AppImageResponse;
import com.technothinksup.consciouskitchen.model.BranchResponse;
import com.technothinksup.consciouskitchen.model.CartResponse;
import com.technothinksup.consciouskitchen.model.CategoryResponse;
import com.technothinksup.consciouskitchen.model.ChangePasswordResponse;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.CouponCodeResponse;
import com.technothinksup.consciouskitchen.model.CurbsideInstructionResponse;
import com.technothinksup.consciouskitchen.model.CustomerAddressResponse;
import com.technothinksup.consciouskitchen.model.CustomerLoginResponse;
import com.technothinksup.consciouskitchen.model.CustomerSignUpResponse;
import com.technothinksup.consciouskitchen.model.CustomizedMenuResponse;
import com.technothinksup.consciouskitchen.model.DefaultBranchResponse;
import com.technothinksup.consciouskitchen.model.DeliveryInstructionResponse;
import com.technothinksup.consciouskitchen.model.DeviceResponse;
import com.technothinksup.consciouskitchen.model.EventDetailResponse;
import com.technothinksup.consciouskitchen.model.EventResponse;
import com.technothinksup.consciouskitchen.model.FaqResponse;
import com.technothinksup.consciouskitchen.model.FavortiteResponse;
import com.technothinksup.consciouskitchen.model.FeaturedResponse;
import com.technothinksup.consciouskitchen.model.ForgotPasswordResponse;
import com.technothinksup.consciouskitchen.model.GetCustomerBalance;
import com.technothinksup.consciouskitchen.model.GetCustomerProfileResponse;
import com.technothinksup.consciouskitchen.model.GetPaymentCardResponse;
import com.technothinksup.consciouskitchen.model.GetPossibleRewardResponse;
import com.technothinksup.consciouskitchen.model.GetRefResponseList;
import com.technothinksup.consciouskitchen.model.GetRewardList;
import com.technothinksup.consciouskitchen.model.GiftCardResponse;
import com.technothinksup.consciouskitchen.model.ItemDetailResponse;
import com.technothinksup.consciouskitchen.model.MenuSubCategoryResponse;
import com.technothinksup.consciouskitchen.model.NearestBranchResponse;
import com.technothinksup.consciouskitchen.model.OrderDetailResponse;
import com.technothinksup.consciouskitchen.model.OrderResponse;
import com.technothinksup.consciouskitchen.model.PageContentResponse;
import com.technothinksup.consciouskitchen.model.PaymentCardResponse;
import com.technothinksup.consciouskitchen.model.PaymentResponse;
import com.technothinksup.consciouskitchen.model.PickUpInstructionResponse;
import com.technothinksup.consciouskitchen.model.PickupTimeResponse;
import com.technothinksup.consciouskitchen.model.PlaceOrderResponse;
import com.technothinksup.consciouskitchen.model.RecentItemResponse;
import com.technothinksup.consciouskitchen.model.RewardResponse;
import com.technothinksup.consciouskitchen.model.TasteResponse;
import com.technothinksup.consciouskitchen.model.TestimonialResponse;
import com.technothinksup.consciouskitchen.model.UpdateProfileResponse;
import com.technothinksup.consciouskitchen.model.WalletBalanceResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @Multipart
    @POST("User_API/customer_signup_save")
    Call<CustomerSignUpResponse> saveCustomer(@Part("customer_name") RequestBody customer_name,
                                              @Part("customer_lname") RequestBody customer_lname,
                                              @Part("customer_dob") RequestBody customer_dob,
                                              @Part("customer_country_code") RequestBody customer_country_code,
                                              @Part("customer_mobile") RequestBody customer_mobile,
                                              @Part("customer_email") RequestBody customer_email,
                                              @Part("customer_password") RequestBody customer_password,
                                              @Part("customer_text_notify") RequestBody customer_text_notify,
                                              @Part("customer_email_notify") RequestBody customer_email_notify,
                                              @Part("customer_push_notify") RequestBody customer_push_notify,
                                              @Part("device_id") RequestBody device_id,
                                              @Part("referral_code") RequestBody referral_code);




    @Multipart
    @POST("User_API/customer_login")
    Call<CustomerLoginResponse> loginCustomer(@Part("email") RequestBody email,
                                              @Part("password") RequestBody password,
                                              @Part("device_id") RequestBody device_id);

    @FormUrlEncoded
    @POST("User_API/forgot_password")
    Call<ForgotPasswordResponse> forgotPassGetOtp(@Field("mobile") String mobile,
                                                  @Field("type") String type);

    @Multipart
    @POST("User_API/change_password")
    Call<ChangePasswordResponse> changePassword(@Part("customer_id") RequestBody customer_id,
                                                @Part("new_password") RequestBody new_password);

    @GET("Master_API/event_list")
    Call<EventResponse> getEventList();


    @GET("Master_API/testimonial_list")
    Call<TestimonialResponse> getTestmonialList();


    @Multipart
    @POST("Master_API/branch_list")
    Call<BranchResponse> getBranchList(@Part("latitude") RequestBody latitude,
                                       @Part("longitude") RequestBody longitude);


    @GET("Master_API/reward_list")
    Call<RewardResponse> getRewardPointList();


    @Multipart
    @POST("Master_API/event_details")
    Call<EventDetailResponse> getEventDetail(@Part("event_id") RequestBody event_id);


    @GET("Master_API/category_list")
    Call<CategoryResponse> getCategoryList();


    @FormUrlEncoded
    @POST("Master_API/menu_item_list")
    Call<MenuSubCategoryResponse> getMenuSubCategoryList(@Field("food_category_id") String food_category_id);


    @FormUrlEncoded
    @POST("Master_API/customized_menu_item_list")
    Call<CustomizedMenuResponse> getCustomizedMenuList(@Field("item_id") String item_id);


    @FormUrlEncoded
    @POST("Master_API/item_details")
    Call<ItemDetailResponse> getItemDetail(@Field("item_id") String item_id);

    @FormUrlEncoded
    @POST("Master_API/recent_featured_favourite_list")
    Call<FeaturedResponse> getFeatureItemList(@Field("item_list_type") String item_list_type);


    @FormUrlEncoded
    @POST("Master_API/recent_featured_favourite_list")
    Call<RecentItemResponse> getRecentItemList(@Field("item_list_type") String item_list_type);

    @FormUrlEncoded
    @POST("Order_API/save_recent_item")
    Call<CommonResponse> save_recent_list(@Field("customer_id") String customer_id,
                                          @Field("item_id") String item_id);


    @FormUrlEncoded
    @POST("Master_API/recent_featured_favourite_list")
    Call<FavortiteResponse> getFavouriteItemList(@Field("item_list_type") String item_list_type);

    @FormUrlEncoded
    @POST("User_API/add_customer_address")
    Call<CommonResponse> addCustomerAddress(@Field("customer_id") String customer_id,
                                            @Field("cust_name") String cust_name,
                                            @Field("cust_address") String cust_address,
                                            @Field("cust_zipcode") String cust_zipcode,
                                            @Field("delivery_instruction") String delivery_instruction);


    @FormUrlEncoded
    @POST("User_API/customer_address_list")
    Call<CustomerAddressResponse> getCustomerAddressList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("Order_API/add_to_cart")
    Call<CommonResponse> addToCart(@Field("customer_id") String customer_id,
                                   @Field("item_id") String item_id,
                                   @Field("item_name") String item_name,
                                   @Field("item_variation_id") String item_variation_id,
                                   @Field("item_variation_name") String item_variation_name,
                                   @Field("item_variation_price") float item_variation_price,
                                   @Field("choice_set_id") String choice_set_id,
                                   @Field("choice_set_name") String choice_set_name,
                                   @Field("choice_set_kitchen_name") String choice_set_kitchen_name,
                                   @Field("choice_value_id") String choice_value_id,
                                   @Field("choice_value_name") String choice_value_name,
                                   @Field("customize_item_id") String customize_item_id,
                                   @Field("customize_item_name") String customize_item_name,
                                   @Field("customize_item_amount") int customize_item_amount,
                                   @Field("item_qty") int item_qty,
                                   @Field("tax_rate_id") String tax_rate_id,
                                   @Field("tax_rate_per") int tax_rate_per);


    @FormUrlEncoded
    @POST("Order_API/cart_item_list")
    Call<CartResponse> getCartList(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("Order_API/remove_cart_item")
    Call<CommonResponse> removeCartItem(@Field("customer_id") String customer_id,
                                        @Field("cart_id") String cart_id);


    @FormUrlEncoded
    @POST("Order_API/update_cart_item_qty")
    Call<CommonResponse> updateQty(@Field("customer_id") String customer_id,
                                   @Field("cart_id") String cart_id,
                                   @Field("item_qty") int item_qty,
                                   @Field("item_variation_price") float item_variation_price,
                                   @Field("customize_item_amount") float customize_item_amount,
                                   @Field("tax_rate_per") float tax_rate_per);


    @FormUrlEncoded
    @POST("Order_API/save_payment_card_details")
    Call<PaymentCardResponse> savePaymentCard(@Field("customer_id") String customer_id,
                                              @Field("card_number") String card_number,
                                              @Field("card_expire_month") String card_expire_month,
                                              @Field("card_expire_year") String card_expire_year,
                                              @Field("card_security_code") String card_security_code,
                                              @Field("name_on_card") String name_on_card);


    @FormUrlEncoded
    @POST("Order_API/payment_card_list")
    Call<GetPaymentCardResponse> getPaymentCardList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("Master_API/pickup_time_list")
    Call<PickupTimeResponse> getPickUpTime(@Field("branch_id") String branch_id);


    @FormUrlEncoded
    @POST("Order_API/place_order")
    Call<PlaceOrderResponse> placeOrder(@Field("branch_id") String branch_id,
                                        @Field("order_type") String order_type,
                                        @Field("customer_id") String customer_id,
                                        @Field("delivery_address") String delivery_address,
                                        @Field("car_make") String car_make,
                                        @Field("car_model") String car_model,
                                        @Field("car_color") String car_color,
                                        @Field("pickup_delivery_time") String pickup_delivery_time,
                                        @Field("order_instruction") String order_instruction,
                                        @Field("order_basic_amount") String order_basic_amount,
                                        @Field("order_tax_amount") String order_tax_amount,
                                        @Field("order_shipping_amount") String order_shipping_amount,
                                        @Field("order_total_amount") String order_total_amount,
                                        @Field("payment_id") String payment_id,
                                        @Field("used_coupon_amount") String used_coupon_amount,
                                        @Field("used_coupon_code") String used_coupon_code,
                                        @Field("used_coupon_id") String used_coupon_id,
                                        @Field("used_gift_card_amount") String used_gift_card_amount,
                                        @Field("used_gift_card_code") String used_gift_card_code,
                                        @Field("used_gift_card_id") String used_gift_card_id,
                                        @Field("used_wallet_amount") String used_wallet_amount,
                                        @Field("redeem_point_amount") String redeem_point_amount,
                                        @Field("reward_use_point") String reward_use_point,
                                        @Field("online_payment_amount") String online_payment_amount,
                                        @Field("payment_card_no") String payment_card_no);


    @Multipart
    @POST("User_API/update_profile")
    Call<UpdateProfileResponse> updateCustomerProfile(
            @Part("customer_id") RequestBody customer_id,
            @Part("customer_name") RequestBody customer_name,
            @Part("customer_lname") RequestBody customer_lname,
            @Part("customer_dob") RequestBody customer_dob,
            @Part("customer_mobile") RequestBody customer_mobile,
            @Part("customer_email") RequestBody customer_email,
            @Part("customer_password") RequestBody customer_password,
            @Part("customer_text_notify") RequestBody customer_text_notify,
            @Part("customer_email_notify") RequestBody customer_email_notify,
            @Part("customer_push_notify") RequestBody customer_push_notify,
            @Part("device_id") RequestBody device_id,
            @Part MultipartBody.Part payment_img);

    @FormUrlEncoded
    @POST("User_API/add_pickup_instruction")
    Call<CommonResponse> addPickUpInstruction(@Field("customer_id") String customer_id,
                                              @Field("pickup_instr") String pickup_instr);

    @FormUrlEncoded
    @POST("User_API/get_pickup_instruction")
    Call<PickUpInstructionResponse> getPickUpInstruction(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/add_delivery_instruction")
    Call<CommonResponse> addDeliveryInstruction(@Field("customer_id") String customer_id,
                                                @Field("customer_delivery_ins") String pickup_instr);

    @FormUrlEncoded
    @POST("User_API/get_delivery_instruction")
    Call<DeliveryInstructionResponse> getDeliveryInstruction(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("Order_API/save_curbside_instruction")
    Call<CommonResponse> addCurbsideInstruction(@Field("customer_id") String customer_id,
                                                @Field("car_make") String car_make,
                                                @Field("car_model") String car_model,
                                                @Field("car_color") String car_color,
                                                @Field("curbside_instruction") String curbside_instruction);


    @FormUrlEncoded
    @POST("Order_API/curbside_instruction_list")
    Call<CurbsideInstructionResponse> getCurbSideInstruction(@Field("customer_id") String customer_id);


    @GET("Master_API/branch_list2")
    Call<BranchResponse> getBranchResponse();

    @FormUrlEncoded
    @POST("User_API/set_default_branch")
    Call<CommonResponse> setAsDefaultBranch(@Field("customer_id") String customer_id,
                                            @Field("branch_id") String branch_id);

    @FormUrlEncoded
    @POST("User_API/add_taste")
    Call<CommonResponse> addTaste(@Field("customer_id") String customer_id,
                                  @Field("taste_name") String taste_name);

    @FormUrlEncoded
    @POST("User_API/taste_list")
    Call<TasteResponse> getTasteList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/update_taste")
    Call<CommonResponse> updateTaste(@Field("customer_id") String customer_id,
                                     @Field("taste_id") String taste_id,
                                     @Field("taste_name") String taste_name);

    @FormUrlEncoded
    @POST("User_API/delete_taste")
    Call<CommonResponse> deleteTaste(@Field("customer_id") String customer_id,
                                     @Field("taste_id") String taste_id);


    @FormUrlEncoded
    @POST("User_API/add_device")
    Call<CommonResponse> addDevice(@Field("customer_id") String customer_id,
                                   @Field("device_name") String device_name);


    @FormUrlEncoded
    @POST("User_API/update_device")
    Call<CommonResponse> updateDevice(@Field("customer_id") String customer_id,
                                      @Field("device_id") String taste_id,
                                      @Field("device_name") String taste_name);


    @FormUrlEncoded
    @POST("User_API/device_list")
    Call<DeviceResponse> getDeviceList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/delete_device")
    Call<CommonResponse> deleteDevice(@Field("customer_id") String customer_id,
                                      @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST("Order_API/add_amount_to_wallet")
    Call<CommonResponse> addMoneyToWallet(@Field("customer_id") String customer_id,
                                          @Field("payment_id") String payment_id,
                                          @Field("wallet_add_amount") String wallet_add_amount);

    @FormUrlEncoded
    @POST("User_API/get_customer_balance")
    Call<WalletBalanceResponse> getWalletBalance(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/add_gift_card")
    Call<AddGiftCardResponse> addGiftCard(@Field("customer_id") String customer_id,
                                          @Field("gift_card_code") String gift_card_code);

    @FormUrlEncoded
    @POST("User_API/delete_cust_gift_card")
    Call<CommonResponse> deleteGiftCard(@Field("customer_id") String customer_id,
                                        @Field("cust_gift_card_id") String cust_gift_card_id);

    @FormUrlEncoded
    @POST("User_API/gift_card_list")
    Call<GiftCardResponse> getGiftCardList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/update_profile_notification")
    Call<CommonResponse> updateNotificationPreference(@Field("customer_id") String customer_id,
                                                      @Field("customer_text_notify") String customer_text_notify,
                                                      @Field("customer_email_notify") String customer_email_notify,
                                                      @Field("customer_push_notify") String customer_push_notify);

    @FormUrlEncoded
    @POST("User_API/get_default_branch")
    Call<DefaultBranchResponse> getDefaultBranch(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("Order_API/delete_payment_card")
    Call<CommonResponse> deleteCreditCard(@Field("customer_id") String customer_id,
                                          @Field("payment_card_id") String payment_card_id);


    @FormUrlEncoded
    @POST("Order_API/apply_coupon")
    Call<CouponCodeResponse> applyCouponCode(@Field("customer_id") String customer_id,
                                             @Field("coupon_code") String coupon_code,
                                             @Field("order_total_amount") int order_total_amount);


    @FormUrlEncoded
    @POST("User_API/customer_social_login")
    Call<CustomerSignUpResponse> socialRegistration(@Field("oauth_provider") String oauth_provider,
                                                    @Field("oauth_uid") String oauth_uid,
                                                    @Field("first_name") String first_name,
                                                    @Field("last_name") String last_name,
                                                    @Field("customer_gender") String customer_gender,
                                                    @Field("customer_email") String customer_email,
                                                    @Field("customer_text_notify") String customer_text_notify,
                                                    @Field("customer_email_notify") String customer_email_notify,
                                                    @Field("customer_push_notify") String customer_push_notify,
                                                    @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST("Order_API/order_list")
    Call<OrderResponse> getOrderList(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("Order_API/order_details")
    Call<OrderDetailResponse> getOrderDetail(@Field("customer_id") String customer_id,
                                             @Field("order_id") String order_id);

    @Multipart
    @POST("User_API/add_testimonial")
    Call<AddTestmonialResponse> addTestimonial(@Part("customer_id") RequestBody customer_id,
                                               @Part("testimonial_cust_name") RequestBody testimonial_cust_name,
                                               @Part("testimonial_cust_address") RequestBody testimonial_cust_address,
                                               @Part("testimonial_details") RequestBody testimonial_details,
                                               @Part MultipartBody.Part payment_img);


    @FormUrlEncoded
    @POST("Event_API/book_event")
    Call<CommonResponse> bookEvent(@Field("customer_id") String customer_id,
                                   @Field("event_id") String event_id,
                                   @Field("event_per_fee") String event_per_fee,
                                   @Field("num_of_seat") String num_of_seat,
                                   @Field("total_amount") String total_amount,
                                   @Field("payment_id") String payment_id);


    @FormUrlEncoded
    @POST("Payment_API/make_payment")
    Call<PaymentResponse> makePayment(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("card_number") String card_number,
                                      @Field("card_exp_month") String card_exp_month,
                                      @Field("card_exp_year") String card_exp_year,
                                      @Field("card_cvc") String card_cvc,
                                      @Field("amount") String amount);


    @GET("Master_API/reward_list")
    Call<GetRewardList> getRewardList();


    @FormUrlEncoded
    @POST("User_API/get_customer_balance")
    Call<GetCustomerBalance> getCustomerBalance(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("Master_API/nearest_branch_details")
    Call<NearestBranchResponse> getNearestBranchList(@Field("latitude") String latitude,
                                                     @Field("longitude") String longitude);


    @FormUrlEncoded
    @POST("User_API/get_profile")
    Call<GetCustomerProfileResponse> getProfileDetail(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("User_API/send_referral_sms")
    Call<CommonResponse> sendRefSMS(@Field("customer_id") String customer_id,
                                    @Field("ref_person_name") String ref_person_name,
                                    @Field("ref_person_mobile") String ref_person_mobile);


    @FormUrlEncoded
    @POST("User_API/referral_list")
    Call<GetRefResponseList> getReferResponseList(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("User_API/possible_reward_list")
    Call<GetPossibleRewardResponse> possibleRewardList(@Field("customer_id") String customer_id);


    @FormUrlEncoded
    @POST("Master_API/page_content")
    Call<PageContentResponse> getPageContent(@Field("page_content_page") String page_content_page);



    @GET("Master_API/faq_list")
    Call<FaqResponse> getFaqResponse();

     @GET("Master_API/app_image")
    Call<AppImageResponse> getAppImage();
}



