package com.technothinksup.consciouskitchen;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.fragments.AddDeviceFragment;
import com.technothinksup.consciouskitchen.fragments.AddGiftCardFragment;
import com.technothinksup.consciouskitchen.fragments.AddPickUpInstructionFragment;
import com.technothinksup.consciouskitchen.fragments.AddTasteFragment;
import com.technothinksup.consciouskitchen.fragments.BranchListFragment;
import com.technothinksup.consciouskitchen.fragments.ConfirmAndPayFragment;
import com.technothinksup.consciouskitchen.fragments.CouponFragment;
import com.technothinksup.consciouskitchen.fragments.CreditCardListFragment;
import com.technothinksup.consciouskitchen.fragments.CurbsideInstructionListFragment;
import com.technothinksup.consciouskitchen.fragments.DeliveryInstructionFragment;
import com.technothinksup.consciouskitchen.fragments.DeliveryLocationFragment;
import com.technothinksup.consciouskitchen.fragments.EditProfileFragment;
import com.technothinksup.consciouskitchen.fragments.EnterManualFragment;
import com.technothinksup.consciouskitchen.fragments.EventFragment;
import com.technothinksup.consciouskitchen.fragments.FaqFragment;
import com.technothinksup.consciouskitchen.fragments.FavoriteShowFragment;
import com.technothinksup.consciouskitchen.fragments.FeatureShowFragment;
import com.technothinksup.consciouskitchen.fragments.GetCurrentLocationActivity;
import com.technothinksup.consciouskitchen.fragments.HomeFragment;
import com.technothinksup.consciouskitchen.fragments.HowItWorkCouponFragment;
import com.technothinksup.consciouskitchen.fragments.HowItWorkReferFriendFragment;
import com.technothinksup.consciouskitchen.fragments.HowItWorkSocialMediaFragment;
import com.technothinksup.consciouskitchen.fragments.MenuCategoryFragment;
import com.technothinksup.consciouskitchen.fragments.MenuCategoryShowFragment;
import com.technothinksup.consciouskitchen.fragments.MyRewardFragment;
import com.technothinksup.consciouskitchen.fragments.NotificationPreferenceFragment;
import com.technothinksup.consciouskitchen.fragments.OrderDeliveryFragment;
import com.technothinksup.consciouskitchen.fragments.OrderFragment;
import com.technothinksup.consciouskitchen.fragments.OrderListFragment;
import com.technothinksup.consciouskitchen.fragments.PaymentMethodFragment;
import com.technothinksup.consciouskitchen.fragments.ProfileFragment;
import com.technothinksup.consciouskitchen.fragments.RecentShowFragment;
import com.technothinksup.consciouskitchen.fragments.ReferFriendFragment;
import com.technothinksup.consciouskitchen.fragments.RestaurantLocationFragment;
import com.technothinksup.consciouskitchen.fragments.ReviewOrderFragment;
import com.technothinksup.consciouskitchen.fragments.RewardProcessFragment;
import com.technothinksup.consciouskitchen.fragments.RewardScanFragment;
import com.technothinksup.consciouskitchen.fragments.RewardScanRestaurantFragment;
import com.technothinksup.consciouskitchen.fragments.TestimonialDataFragment;
import com.technothinksup.consciouskitchen.fragments.TestimonialFragment;
import com.technothinksup.consciouskitchen.fragments.TrackerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.Objects;

import static android.view.View.GONE;

public class HomePageActivity extends AppCompatActivity {

    public static String userId = "", name = "", email = "", currency = "$",profile_image = "";
    BottomNavigationView bottomNavigationView_Home;
    public DrawerLayout mDrawer;
    public NavigationView nvDrawer;
    public ActionBarDrawerToggle drawerToggle;
    String logintype = "";
    public Toolbar toolbar;
    ImageView imageView_CloseNavigation, imageView_UserProfile,imageView_NavInsta,imageView_NavFacebook,iv_UpdateProfileActivity_changeImage;
    TextView textView_Nav_SignIn, textView_Nav_JoinNow, textView_userName,textView_location,tv_add_post;
    LinearLayout linearLayout_Profile, linearLayout_CouponsAndRewards, linearLayout_AboutUs, linearLayout_Menu, linearLayout_ReferFriend, linearLayout_OrderFavorites,
            linearLayout_RestaurantLocation, linearLayout_Notifications, linearLayout_PaymentInformation, linearLayout_Logout, linearLayout_FollowUs, linearLayout_CustomerCare,
            linearLayout_profile_setting, linearLayout_default_setting, linearLayout_payment_method, linearLayout_transaction_history, linearLayout_social_preference, linearLayout_paywith_wallet, linearLayout_SignInOrJoinNow, linearLayout_faq;
    LinearLayout linearLayout_submenu_MEnu, linearLayout_Reward, linearLayout_submenu_reward, linearLayout_submenu_refer_friend, linearLayout_coupons_offer, linearLayout_coupons_offeri, linearLayout_socialmedia_get_point,
            linearLayout_submenu_pofile_preference, linearLayout_submenu_socialmedia, linearLayout_submenu_pofile_setting, linearLayout_submenu_default_setting, linearLayout_submenu_payment_methods;
    private TextView tv_order_by_phone, tv_recent, tv_favorite, tv_feature, tv_reward_how_it_wok, tv_my_reward, tv_scan_qrcode, tv_scan_receipt, tv_refer_friend_how_it_work, tv_social_media_how_it_work,
            tv_testimonial, tv_edit_profile, tv_pickup_instruction, tv_curbside_instruction, tv_delivery_instruction, tv_resturant, tv_taste, tv_device, tv_wallet, tv_gift_card, tv_credit_card,
            tv_notification, tv_transaction_history, textView_Nav_Privacy, tv_terms_cond, textView_Nav_CustomerCare;
    RelativeLayout relativeLayout_Profile;
    public static double reward_point, wallet_balance;
    boolean doubleBackToExitPressedOnce = false;
    GoogleSignInClient mGoogleSignInClient;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.menu_small);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        logintype = getIntent().getStringExtra("logintype");
        try {
            userId = Common.getSavedUserData(getApplicationContext(), "customer_id");
            name = (Common.getSavedUserData(getApplicationContext(), "fName") + " " + Common.getSavedUserData(getApplicationContext(), "lName"));
            email = Common.getSavedUserData(getApplicationContext(), "email");
            profile_image = Common.getSavedUserData(getApplicationContext(), "image_path")+Common.getSavedUserData(getApplicationContext(), "customer_image");
            //  Toast.makeText(this, "" + userId, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();
        listener();

        setupBottomNavigation();
        if (savedInstanceState == null) {
            loadHomeFragment();
        }
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.str_open_drawer, R.string.str_close_drawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                name = (Common.getSavedUserData(getApplicationContext(), "fName") + " " + Common.getSavedUserData(getApplicationContext(), "lName"));
                profile_image = Common.getSavedUserData(getApplicationContext(), "image_path")+Common.getSavedUserData(getApplicationContext(), "customer_image");
                Glide.with(HomePageActivity.this).asBitmap().load(profile_image).error(R.drawable.profile).into(imageView_UserProfile);
                textView_userName.setText(name);
            }
        };
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (userId.equalsIgnoreCase("")) {
            tv_recent.setVisibility(GONE);
            tv_favorite.setVisibility(GONE);
            tv_feature.setVisibility(GONE);
            tv_reward_how_it_wok.setVisibility(GONE);
            tv_my_reward.setVisibility(GONE);
            tv_scan_qrcode.setVisibility(GONE);
            tv_scan_receipt.setVisibility(GONE);
            linearLayout_profile_setting.setVisibility(GONE);
            linearLayout_default_setting.setVisibility(GONE);
            linearLayout_payment_method.setVisibility(GONE);
            linearLayout_Notifications.setVisibility(GONE);
            linearLayout_transaction_history.setVisibility(GONE);
            linearLayout_social_preference.setVisibility(GONE);
            linearLayout_paywith_wallet.setVisibility(GONE);
            linearLayout_SignInOrJoinNow.setVisibility(View.VISIBLE);
            linearLayout_Logout.setVisibility(GONE);
        } else {
            Common.getProfileData(HomePageActivity.this, userId);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    // ...
                });
    }

    private void init() {
        bottomNavigationView_Home = findViewById(R.id.bottomNavigationView_Home);
        bottomNavigationView_Home.setItemIconTintList(null);
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nav_view);

        imageView_CloseNavigation = findViewById(R.id.imageView_CloseNavigation);
        imageView_NavInsta =findViewById(R.id.imageView_Nav_Insta);
        imageView_NavFacebook =findViewById(R.id.imageView_Nav_Fb);
        linearLayout_Profile = findViewById(R.id.linearLayout_Profile);
        iv_UpdateProfileActivity_changeImage = findViewById(R.id.iv_UpdateProfileActivity_changeImage);

        linearLayout_CouponsAndRewards = findViewById(R.id.linearLayout_CouponsAndRewards);
        linearLayout_coupons_offeri = findViewById(R.id.linearLayout_coupons_offeri);
        linearLayout_AboutUs = findViewById(R.id.linearLayout_AboutUs);
        linearLayout_Menu = findViewById(R.id.linearLayout_Menu);
        linearLayout_OrderFavorites = findViewById(R.id.linearLayout_OrderFavorites);
        linearLayout_RestaurantLocation = findViewById(R.id.linearLayout_RestaurantLocation);
        linearLayout_Notifications = findViewById(R.id.linearLayout_Notifications);
        linearLayout_PaymentInformation = findViewById(R.id.linearLayout_PaymentInformation);
        linearLayout_CustomerCare = findViewById(R.id.linearLayout_CustomerCare);
        linearLayout_Logout = findViewById(R.id.linearLayout_Logout);
        linearLayout_FollowUs = findViewById(R.id.linearLayout_FollowUs);
        textView_Nav_SignIn = findViewById(R.id.textView_Nav_SignIn);
        textView_Nav_JoinNow = findViewById(R.id.textView_Nav_JoinNow);
        relativeLayout_Profile = findViewById(R.id.relativeLayout_Profile);
        linearLayout_Reward = findViewById(R.id.linearLayout_Reward);
        linearLayout_ReferFriend = findViewById(R.id.linearLayout_ReferFriend);
        linearLayout_coupons_offer = findViewById(R.id.linearLayout_coupons_offer);
        linearLayout_socialmedia_get_point = findViewById(R.id.linearLayout_socialmedia_get_point);
        linearLayout_profile_setting = findViewById(R.id.linearLayout_profile_setting);
        linearLayout_default_setting = findViewById(R.id.linearLayout_default_setting);
        linearLayout_payment_method = findViewById(R.id.linearLayout_payment_method);
        linearLayout_transaction_history = findViewById(R.id.linearLayout_transaction_history);
        linearLayout_social_preference = findViewById(R.id.linearLayout_social_preference);
        linearLayout_paywith_wallet = findViewById(R.id.linearLayout_paywith_wallet);
        linearLayout_SignInOrJoinNow = findViewById(R.id.linearLayout_SignInOrJoinNow);
        linearLayout_faq = findViewById(R.id.linearLayout_faq);
        //   linearLayout_profile_preference_setting=findViewById(R.id.linearLayout_profile_preference_setting);
        linearLayout_submenu_reward = findViewById(R.id.linearLayout_submenu_reward);
        linearLayout_submenu_MEnu = findViewById(R.id.linearLayout_submenu_MEnu);
        linearLayout_submenu_refer_friend = findViewById(R.id.linearLayout_submenu_refer_friend);
        linearLayout_submenu_socialmedia = findViewById(R.id.linearLayout_submenu_socialmedia);

        linearLayout_submenu_pofile_setting = findViewById(R.id.linearLayout_submenu_pofile_setting);
        linearLayout_submenu_default_setting = findViewById(R.id.linearLayout_submenu_default_setting);
        linearLayout_submenu_payment_methods = findViewById(R.id.linearLayout_submenu_payment_methods);
        //  linearLayout_submenu_pofile_preference=findViewById(R.id.linearLayout_submenu_pofile_preference);
        tv_order_by_phone = findViewById(R.id.tv_order_by_phone);
        tv_recent = findViewById(R.id.tv_recent);
        tv_favorite = findViewById(R.id.tv_favorite);
        tv_feature = findViewById(R.id.tv_feature);
        tv_reward_how_it_wok = findViewById(R.id.tv_reward_how_it_wok);
        tv_my_reward = findViewById(R.id.tv_my_reward);
        tv_scan_qrcode = findViewById(R.id.tv_scan_qrcode);
        tv_add_post = findViewById(R.id.tv_add_post);
        tv_scan_receipt = findViewById(R.id.tv_scan_receipt);
        tv_refer_friend_how_it_work = findViewById(R.id.tv_refer_friend_how_it_work);
        tv_social_media_how_it_work = findViewById(R.id.tv_social_media_how_it_work);
        tv_testimonial = findViewById(R.id.tv_testimonial);
        tv_edit_profile = findViewById(R.id.tv_edit_profile);
        tv_pickup_instruction = findViewById(R.id.tv_pickup_instruction);
        tv_curbside_instruction = findViewById(R.id.tv_curbside_instruction);
        tv_delivery_instruction = findViewById(R.id.tv_delivery_instruction);
        tv_resturant = findViewById(R.id.tv_resturant);
        tv_taste = findViewById(R.id.tv_taste);
        tv_device = findViewById(R.id.tv_device);
        tv_wallet = findViewById(R.id.tv_wallet);
        tv_gift_card = findViewById(R.id.tv_gift_card);
        tv_credit_card = findViewById(R.id.tv_credit_card);
        textView_Nav_Privacy = findViewById(R.id.textView_Nav_Privacy);
        tv_terms_cond = findViewById(R.id.tv_terms_cond);
        textView_Nav_CustomerCare = findViewById(R.id.textView_Nav_CustomerCare);
        textView_userName = findViewById(R.id.textView_UserName);
       // textView_location = findViewById(R.id.textView_location);
        imageView_UserProfile = findViewById(R.id.imageView_UserProfile);
        getSharedPreferences("ck", MODE_PRIVATE).edit().putString("logintype", logintype).apply();
        if (logintype.equals("g")) {
            textView_userName.setVisibility(GONE);
            imageView_UserProfile.setVisibility(GONE);
            relativeLayout_Profile.setVisibility(GONE);
        } else {
            textView_userName.setVisibility(View.VISIBLE);
            imageView_UserProfile.setVisibility(View.VISIBLE);
            relativeLayout_Profile.setVisibility(View.VISIBLE);
            if (!name.isEmpty() | name != null || !name.equals("")) {
                textView_userName.setText(name);
            }
            Glide.with(this).asBitmap().load(profile_image).error(R.drawable.profile).into(imageView_UserProfile);
        }
       /* tv_personal_info=findViewById(R.id.tv_personal_info);
        tv_default_setting=findViewById(R.id.tv_default_setting);
        tv_payment_method=findViewById(R.id.tv_payment_method);
        tv_notification=findViewById(R.id.tv_notification);
        tv_transaction_history=findViewById(R.id.tv_transaction_history);*/
    }

    private void listener() {
        imageView_CloseNavigation.setOnClickListener(view -> mDrawer.closeDrawers());
        linearLayout_Profile.setOnClickListener(view -> {
            loadProfileFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_CouponsAndRewards.setOnClickListener(view -> {
            loadCouponFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_Menu.setOnClickListener(view -> {
            //   loadMenuFragment();
            linearLayout_submenu_MEnu.setVisibility(View.VISIBLE);
            linearLayout_submenu_reward.setVisibility(GONE);
            linearLayout_submenu_refer_friend.setVisibility(GONE);
            linearLayout_submenu_socialmedia.setVisibility(GONE);
            // linearLayout_submenu_pofile_preference.setVisibility(View.GONE);
            // mDrawer.closeDrawers();
        });

        iv_UpdateProfileActivity_changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadEditProfileFragment();
                mDrawer.closeDrawers();
            }
        });


        linearLayout_ReferFriend.setOnClickListener(view -> {
            //loadReferFriendFragment();
            linearLayout_submenu_MEnu.setVisibility(GONE);
            linearLayout_submenu_reward.setVisibility(GONE);
            linearLayout_submenu_refer_friend.setVisibility(View.VISIBLE);
            linearLayout_submenu_socialmedia.setVisibility(GONE);
            //  mDrawer.closeDrawers();
        });
        linearLayout_OrderFavorites.setOnClickListener(view -> {
            loadFavoriteShowFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_RestaurantLocation.setOnClickListener(view -> {
            loadRestaurantLocationFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_Notifications.setOnClickListener(view -> {
//                loadCouponFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_PaymentInformation.setOnClickListener(view -> {
            loadConfirmAndPayFragment();
            mDrawer.closeDrawers();
        });
        linearLayout_Logout.setOnClickListener(view -> {
            logout();
            mDrawer.closeDrawers();
        });
        linearLayout_FollowUs.setOnClickListener(view -> {
            //loadCouponFragment();
            mDrawer.closeDrawers();
        });


        tv_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTestimonialDataFragment();
                mDrawer.closeDrawers();
            }


        });

        linearLayout_CustomerCare.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.setData(Uri.parse("tel:" + "5137700160"));
            startActivity(callIntent);
        });


        imageView_NavInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://instagram.com/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/")));
                }
            }
        });

        imageView_NavFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://facebook.com/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.facebook.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://facebook.com/")));
                }

            }
        });

        textView_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEditProfileFragment();
                mDrawer.closeDrawers();
            }
        });

//        textView_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadRestaurantLocationFragment();
//                mDrawer.closeDrawers();
//            }
//        });




        textView_Nav_SignIn.setOnClickListener(view -> startActivity(new Intent(HomePageActivity.this, LoginActivity.class)));
        textView_Nav_JoinNow.setOnClickListener(view -> startActivity(new Intent(HomePageActivity.this, SignUpActivity.class)));
        linearLayout_Reward.setOnClickListener(view -> {
            linearLayout_submenu_reward.setVisibility(View.VISIBLE);
            linearLayout_submenu_MEnu.setVisibility(GONE);
            linearLayout_submenu_refer_friend.setVisibility(GONE);
            linearLayout_submenu_socialmedia.setVisibility(GONE);
            //   linearLayout_submenu_pofile_preference.setVisibility(View.GONE);
        });

        linearLayout_coupons_offer.setOnClickListener(view -> {
            linearLayout_submenu_MEnu.setVisibility(GONE);
            linearLayout_submenu_reward.setVisibility(GONE);
            linearLayout_submenu_refer_friend.setVisibility(GONE);
            linearLayout_submenu_socialmedia.setVisibility(GONE);
            loadCouponFragment();
            //  linearLayout_submenu_pofile_preference.setVisibility(View.GONE);
        });

        linearLayout_socialmedia_get_point.setOnClickListener(view -> {
            linearLayout_submenu_reward.setVisibility(GONE);
            linearLayout_submenu_MEnu.setVisibility(GONE);
            linearLayout_submenu_refer_friend.setVisibility(GONE);
            linearLayout_submenu_socialmedia.setVisibility(View.VISIBLE);
            //   linearLayout_submenu_pofile_preference.setVisibility(View.GONE);
        });

        linearLayout_profile_setting.setOnClickListener(v -> {
            linearLayout_submenu_pofile_setting.setVisibility(View.VISIBLE);
            linearLayout_submenu_default_setting.setVisibility(GONE);
            linearLayout_submenu_payment_methods.setVisibility(GONE);
        });

        linearLayout_default_setting.setOnClickListener(v -> {
            linearLayout_submenu_pofile_setting.setVisibility(GONE);
            linearLayout_submenu_default_setting.setVisibility(View.VISIBLE);
            linearLayout_submenu_payment_methods.setVisibility(GONE);
        });

        linearLayout_payment_method.setOnClickListener(v -> {
            linearLayout_submenu_pofile_setting.setVisibility(GONE);
            linearLayout_submenu_default_setting.setVisibility(GONE);
            linearLayout_submenu_payment_methods.setVisibility(View.VISIBLE);
        });

        linearLayout_transaction_history.setOnClickListener(v -> {
        });

        linearLayout_social_preference.setOnClickListener(v -> {
        });

        linearLayout_paywith_wallet.setOnClickListener(v -> {
        });

        textView_Nav_Privacy.setOnClickListener(v -> {
            Intent i = new Intent(HomePageActivity.this, PageContentActivity.class);
            i.putExtra("page_content", 2);
            startActivity(i);
            mDrawer.closeDrawers();
        });

        tv_terms_cond.setOnClickListener(v -> {
            Intent i = new Intent(HomePageActivity.this, PageContentActivity.class);
            i.putExtra("page_content", 3);
            startActivity(i);
           /* page_content="3";
            loadFragment(new PageContentFragment(),true);*/
            // Toast.makeText(HomePageActivity.this, ""+page_content, Toast.LENGTH_SHORT).show();
            mDrawer.closeDrawers();
        });

        textView_Nav_CustomerCare.setOnClickListener(v -> {
            Intent i = new Intent(HomePageActivity.this, PageContentActivity.class);
            i.putExtra("page_content", 4);
            startActivity(i);
            mDrawer.closeDrawers();
        });

        linearLayout_AboutUs.setOnClickListener(v -> {
            Intent i = new Intent(HomePageActivity.this, PageContentActivity.class);
            i.putExtra("page_content", 1);
            startActivity(i);
            mDrawer.closeDrawers();
        });

        linearLayout_faq.setOnClickListener(v -> {
            loadFragment(new FaqFragment(), true);
            mDrawer.closeDrawers();
        });

       /* linearLayout_profile_preference_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_submenu_reward.setVisibility(View.GONE);
                linearLayout_submenu_MEnu.setVisibility(View.GONE);
                linearLayout_submenu_refer_friend.setVisibility(View.GONE);
                linearLayout_submenu_socialmedia.setVisibility(View.GONE);
                linearLayout_submenu_pofile_preference.setVisibility(View.VISIBLE);
            }
        });*/

        tv_order_by_phone.setOnClickListener(view -> {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }
            loadMenuShowFragment();

        });

        tv_recent.setOnClickListener(view -> {
            loadFragment(new RecentShowFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_favorite.setOnClickListener(view -> {
            loadFragment(new FavoriteShowFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_feature.setOnClickListener(view -> {
            loadFragment(new FeatureShowFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_reward_how_it_wok.setOnClickListener(view -> {
            loadFragment(new HowItWorkCouponFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_my_reward.setOnClickListener(view -> {
            // loadFragment(new RewardScanFragment(), false);
            loadFragment(new MyRewardFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_scan_qrcode.setOnClickListener(view -> {
            loadFragment(new RewardScanFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_scan_receipt.setOnClickListener(view -> mDrawer.closeDrawers());
        tv_refer_friend_how_it_work.setOnClickListener(view -> {
            loadFragment(new HowItWorkReferFriendFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_social_media_how_it_work.setOnClickListener(view -> {
            loadFragment(new EnterManualFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_testimonial.setOnClickListener(view -> {
            loadFragment(new TestimonialFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_edit_profile.setOnClickListener(v -> {
            loadEditProfileFragment();
            mDrawer.closeDrawers();
        });

        tv_pickup_instruction.setOnClickListener(view -> {
            loadFragment(new AddPickUpInstructionFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_delivery_instruction.setOnClickListener(view -> {
            loadFragment(new DeliveryInstructionFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_curbside_instruction.setOnClickListener(view -> {
            loadFragment(new CurbsideInstructionListFragment(), true);
            mDrawer.closeDrawers();
        });


        tv_resturant.setOnClickListener(view -> {
            loadFragment(new BranchListFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_taste.setOnClickListener(view -> {
            loadFragment(new AddTasteFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_device.setOnClickListener(view -> {
            loadFragment(new AddDeviceFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_gift_card.setOnClickListener(view -> {
            loadFragment(new AddGiftCardFragment(), true);
            mDrawer.closeDrawers();
        });

        tv_credit_card.setOnClickListener(v -> {
            loadFragment(new CreditCardListFragment(), true);
            mDrawer.closeDrawers();
        });

        linearLayout_Notifications.setOnClickListener(v -> {
            loadFragment(new NotificationPreferenceFragment(), true);
            mDrawer.closeDrawers();
        });

        linearLayout_transaction_history.setOnClickListener(v -> {
            loadFragment(new OrderListFragment(), true);
            mDrawer.closeDrawers();
        });
    }





    private void setupBottomNavigation() {
        bottomNavigationView_Home.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.home_menu) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                loadHomeFragment();
                return true;
            } else if (itemId == R.id.home_order) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                loadRestaurantLocationFragment();
                return true;
            } else if (itemId == R.id.home_tracker) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                loadTrackerFragment();
                return true;
            } else if (itemId == R.id.home_event) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                loadEventFragment();
                return true;
            } else if (itemId == R.id.home_more) {
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                       mDrawer.openDrawer(GravityCompat.START);
                loadProfileFragment();
                return true;
            }
            return false;
        });
    }



    private void loadHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, HomeFragment.newInstance())
                .commit();
    }



    private void loadMenuShowFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    private void loadTestimonialDataFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, TestimonialDataFragment.newInstance())
                .addToBackStack(null)
                .commit();

    }



    private void loadEventFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, EventFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadOrderFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, OrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadTrackerFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, TrackerFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadMyRewardFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, MyRewardFragment.newInstance())
                .addToBackStack(null)
                .commit();
        mDrawer.closeDrawers();
    }

    private void loadProfileFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, ProfileFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCouponFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, CouponFragment.newInstance())
                .addToBackStack(null)
                .commit();
        mDrawer.closeDrawers();
    }

    private void loadEditProfileFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, EditProfileFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardScanFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, RewardScanFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardScanRestaurantFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, RewardScanRestaurantFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    public void loadFragment(Fragment fragment, Boolean bool) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_Home, fragment);
        if (bool) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void loadReferFriendFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, ReferFriendFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadFavoriteShowFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, FavoriteShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadOrderDeliveryFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, OrderDeliveryFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadReviewOrderFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRestaurantLocationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, RestaurantLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadDeliveryLocationFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, DeliveryLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRewardProcessFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadConfirmAndPayFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, ConfirmAndPayFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadPaymentMethodFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_Home, PaymentMethodFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.notification_cart_menu) {
            loadReviewOrderFragment();
        }
        if (item.getItemId() == R.id.notification_reward_menu) {
            loadRewardProcessFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.logo);
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Logout", (dialog, which) -> {
            //  Common.saveUserData(MainActivity.this, "userId", "");
            File file1 = new File("data/data/com.technothinksup.consciouskitchen/shared_prefs/user.xml");
            if (file1.exists()) {
                file1.delete();
                Common.saveUserData(getApplicationContext(), "customer_id", "");
                HomePageActivity.userId = "";
            }

            Toast.makeText(HomePageActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
            //Display Toast first and after 1 second start login activity
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();
                signOut();
                dialog.cancel();
            }, 1000);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping back stack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on back stack, calling super");
            super.onBackPressed();
        }
    }

    //    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        actionBarDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        actionBarDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = drawer_layout.isDrawerOpen(drawer_layout);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
   /* @Override
    public void onBackPressed() {
        // double press to exit
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        toolbar.setTitle("");
        this.doubleBackToExitPressedOnce = true;


        Toast.makeText(this, "Press back once more to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/


//    public boolean checkDrawer() {
//        //drawer is open
//        return mDrawer.isDrawerOpen(GravityCompat.START);
//    }
}