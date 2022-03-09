package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.LoginActivity;
import com.technothinksup.consciouskitchen.PageContentActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;
import java.io.File;
import java.util.Objects;
import static android.content.Context.MODE_PRIVATE;

import org.checkerframework.common.reflection.qual.NewInstance;

public class ProfileFragment extends Fragment {
    private View view;
    private LinearLayout linearLayout_ReferFriend, linearLayout_RecentsFavorites, linearLayout_CouponsAndRewards,
            linearLayout_PaymentInformation, linearLayout_Logout, linearLayout_FindLocation, linearLayout_AboutUs,
            linearLayout_Profile,linearLayout_socialMedia;

    private ImageView imageView_BackProfile, imageView_RewardProfile, imageView_CartProfile,imageView_UserProfile,imageView_InstaNav,imageView_FBNav,iv_UpdateProfileActivity_changeImage;
    TextView textView_Nav_Privacy, tv_terms_and_condition, textView_Nav_CustomerCare,textView_UserName;

    public static String profile_image = "";

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();
        String lt = getActivity().getSharedPreferences("ck", MODE_PRIVATE).getString("logintype", "");
        profile_image = Common.getSavedUserData(getActivity(), "image_path")+Common.getSavedUserData(getActivity(), "customer_image");
        Glide.with(this).asBitmap().load(profile_image).error(R.drawable.profile).into(imageView_UserProfile);

        if (lt.equals("g"))
            linearLayout_Logout.setVisibility(View.GONE);
        else
            linearLayout_Logout.setVisibility(View.VISIBLE);

        textView_UserName.setText(Common.getSavedUserData(getActivity(), "fName") + " " + Common.getSavedUserData(getActivity(), "lName"));
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();

//        setHasOptionsMenu(true);
        return view;
    }

    private void listener() {

        linearLayout_AboutUs.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PageContentActivity.class);
            i.putExtra("page_content", 1);
            startActivity(i);
           /* HomePageActivity.page_content="1";
            ((HomePageActivity)getActivity()).loadFragment(new PageContentFragment(),true);*/
        });

        textView_Nav_Privacy.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PageContentActivity.class);
            i.putExtra("page_content", 2);
            startActivity(i);
        });

        tv_terms_and_condition.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PageContentActivity.class);
            i.putExtra("page_content", 3);
            startActivity(i);
        });

        textView_Nav_CustomerCare.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), PageContentActivity.class);
            i.putExtra("page_content", 4);
            startActivity(i);
        });



        linearLayout_FindLocation.setOnClickListener(view -> loadRestaurantLocationFragment());

        linearLayout_ReferFriend.setOnClickListener(view -> loadReferFriendFragment());

        linearLayout_RecentsFavorites.setOnClickListener(view -> loadRecentShowFragment());

        linearLayout_CouponsAndRewards.setOnClickListener(view -> loadMyRewardFragment());

        linearLayout_PaymentInformation.setOnClickListener(view -> {
            // loadPaymentMethodFragment();
            ((HomePageActivity) getActivity()).loadFragment(new CreditCardListFragment(), true);
        });

        linearLayout_Logout.setOnClickListener(view -> {
            //  startActivity(new Intent(getActivity(), LoginActivity.class));
            logout();
        });

        linearLayout_Profile.setOnClickListener(v -> ((HomePageActivity) getActivity()).loadFragment(new EditProfileFragment(), true));


        imageView_BackProfile.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_InstaNav.setOnClickListener(new View.OnClickListener() {
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
                } }
        });


        imageView_FBNav.setOnClickListener(new View.OnClickListener() {
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


        linearLayout_socialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lodeTestimonialFragment();
            }

            private void lodeTestimonialFragment() {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_Home, TestimonialFragment.newInstance())
                        .addToBackStack(null)
                        .commit();


            }
        });

        iv_UpdateProfileActivity_changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lodeEditProfileFragment();
            }

            private void lodeEditProfileFragment() {

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_Home, EditProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        });



        imageView_RewardProfile.setOnClickListener(view -> loadRewardProcessFragment());

        imageView_CartProfile.setOnClickListener(view -> loadReviewOrderFragment());

    }

    private void init() {
        imageView_UserProfile = view.findViewById(R.id.imageView_UserProfile);
        linearLayout_ReferFriend = view.findViewById(R.id.linearLayout_ReferFriend);
        linearLayout_RecentsFavorites = view.findViewById(R.id.linearLayout_RecentsFavorites);
        linearLayout_CouponsAndRewards = view.findViewById(R.id.linearLayout_CouponsAndRewards);
        linearLayout_PaymentInformation = view.findViewById(R.id.linearLayout_PaymentInformation);
        linearLayout_Logout = view.findViewById(R.id.linearLayout_Logout);
        linearLayout_FindLocation = view.findViewById(R.id.linearLayout_FindLocation);
        linearLayout_AboutUs = view.findViewById(R.id.linearLayout_AboutUs);
        linearLayout_Profile = view.findViewById(R.id.linearLayout_Profile);
        imageView_InstaNav = view.findViewById(R.id.imageView_Nav_Insta);
        imageView_FBNav =view.findViewById(R.id.imageView_Nav_Fb);
        linearLayout_socialMedia =view.findViewById(R.id.linearLayout_socialMedia);
        imageView_BackProfile = view.findViewById(R.id.imageView_BackProfile);
        imageView_RewardProfile = view.findViewById(R.id.imageView_RewardProfile);
        imageView_CartProfile = view.findViewById(R.id.imageView_CartProfile);
        iv_UpdateProfileActivity_changeImage = view.findViewById(R.id.iv_UpdateProfileActivity_changeImage);
        textView_Nav_Privacy = view.findViewById(R.id.textView_Nav_Privacy);
        tv_terms_and_condition = view.findViewById(R.id.tv_terms_and_condition);
        textView_Nav_CustomerCare = view.findViewById(R.id.textView_Nav_CustomerCare);
        textView_UserName = view.findViewById(R.id.textView_UserName);
    }

    private void loadMyRewardFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MyRewardFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRestaurantLocationFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RestaurantLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    private void loadReferFriendFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReferFriendFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadRecentShowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RecentShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadReviewOrderFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
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

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.logo);
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Logout", (dialog, which) -> {

            //  Common.saveUserData(MainActivity.this, "userId", "");
            File file1 = new File("data/data/com.technothinksup.consciouskitchen/shared_prefs/user.xml");
            if (file1.exists()) {
                file1.delete();

                Common.saveUserData(getActivity(), "customer_id", "");
                HomePageActivity.userId = "";
            }

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finishAffinity();

            dialog.cancel();

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();

    }


//    @SuppressLint("NewApi")
//    @Override
//    public void onResume() {
//        super.onResume();
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if (item.getItemId() == android.R.id.home) {
//            if (getActivity() != null) {
//                loadHomeFragment();
//            }
//            return true;
//        };
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        setHasOptionsMenu(true);
//    }
//
//    private void loadHomeFragment(){
//
//        FragmentManager fragmentManager = getFragmentManager();
//        assert fragmentManager != null;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout_Home, HomeFragment.newInstance())
//                .commit();
//    }
}
