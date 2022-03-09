package com.technothinksup.consciouskitchen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.fragments.CurbsideInstructionFragment;
import com.technothinksup.consciouskitchen.fragments.DeliveryLocationFragment;
import com.technothinksup.consciouskitchen.fragments.MenuCategoryShowFragment;
import com.technothinksup.consciouskitchen.fragments.ReviewOrderFragment;
import com.technothinksup.consciouskitchen.fragments.RewardProcessFragment;
import com.technothinksup.consciouskitchen.model.BranchResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResturantLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;


    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private RequestBody reqLatitide, reqLongitude;

    private TextView tv_branch_name, tv_branch_time, tv_branch_address;

    private View view;

    private Button button_PickUp, button_Delivery, button_CurbSide;

    private ImageView imageView_Call, imageView_MapOne, imageView_MapTwo, imageView_BackRestaurantLocation, imageView_RewardRestaurantLocation, imageView_CartRestaurantLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_location);

        getSupportActionBar().hide();


        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        init();
        listener();
        setBranchData();


        //Objects.requireNonNull(getApplicationContext()).getSupportActionBar().hide();


    }

    private void init() {


        StateProgressBar stateProgressBar = findViewById(R.id.stateprogressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        button_PickUp = findViewById(R.id.button_PickUp);
        button_Delivery = findViewById(R.id.button_Delivery);
        button_CurbSide = findViewById(R.id.button_CurbSide);

        imageView_Call = findViewById(R.id.imageView_Call);
        imageView_MapOne = findViewById(R.id.imageView_MapOne);
        imageView_MapTwo = findViewById(R.id.imageView_MapTwo);

        imageView_BackRestaurantLocation = findViewById(R.id.imageView_BackRestaurantLocation);
        imageView_RewardRestaurantLocation = findViewById(R.id.imageView_RewardRestaurantLocation);
        imageView_CartRestaurantLocation = findViewById(R.id.imageView_CartRestaurantLocation);


        tv_branch_name = findViewById(R.id.tv_branch_name);
        tv_branch_time = findViewById(R.id.tv_branch_time);
        tv_branch_address = findViewById(R.id.tv_branch_address);
        // mapFragmentContainer = view.findViewById(R.id.mapFragmentContainer);
    }

    private void listener() {

        imageView_BackRestaurantLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardRestaurantLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartRestaurantLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        button_PickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Common.order_type = "1";

                // Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();
                loadMenuShowFragment();
            }
        });

        button_Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.order_type = "2";
                loadDeliveryLocationFragment();

                //Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();

                //loadMenuShowFragment();
            }
        });

        button_CurbSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.order_type = "3";
                // Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();
                loadCurbsideInstructionFragment();
            }
        });

        imageView_Call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + "5137700160"));
                Objects.requireNonNull(getApplicationContext()).startActivity(callIntent);
            }
        });

        imageView_MapOne.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 54.8068581,-68.3378227);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Objects.requireNonNull(getApplicationContext()).startActivity(intent);
            }
        });

        imageView_MapTwo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                mCurrLocationMarker.getPosition();
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 54.8068581,-68.3378227);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Objects.requireNonNull(getApplicationContext()).startActivity(intent);
            }
        });
    }


    private void setBranchData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        reqLatitide = RequestBody.create(MediaType.parse("multipart/form-data"), "111");
        reqLongitude = RequestBody.create(MediaType.parse("multipart/form-data"), "222");

        Call<BranchResponse> call = apiInterface.getBranchList(reqLatitide, reqLongitude);

        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                // Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();

                tv_branch_name.setText("" + response.body().getBranchList().get(0).getBranchName());
                tv_branch_time.setText("" + response.body().getBranchList().get(0).getBranchCreatedAt());
                tv_branch_address.setText("" + response.body().getBranchList().get(0).getBranchAddress());


            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {

            }
        });
    }


    private void loadMenuShowFragment() {

       /* FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();*/

        ((HomePageActivity) getApplicationContext()).loadFragment(new MenuCategoryShowFragment(), true);
    }

    private void loadDeliveryLocationFragment() {

       /* FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();*/
        ((HomePageActivity) getApplicationContext()).loadFragment(new DeliveryLocationFragment(), true);
    }

    private void loadCurbsideInstructionFragment() {

       /* FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideInstructionFragment.newInstance())
                .addToBackStack(null)
                .commit();
*/
        ((HomePageActivity) getApplicationContext()).loadFragment(new CurbsideInstructionFragment(), true);


    }

    private void loadReviewOrderFragment() {

       /* FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ReviewOrderFragment.newInstance())
                .addToBackStack(null)
                .commit();*/

        ((HomePageActivity) getApplicationContext()).loadFragment(new ReviewOrderFragment(), true);
    }

    private void loadRewardProcessFragment() {

        /*FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();*/

        ((HomePageActivity) getApplicationContext()).loadFragment(new RewardProcessFragment(), true);
    }


    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
        //     mLocationRequest.setFastestInterval(1000);
        //       mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this::onLocationChanged);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

        Common.saveUserData(getApplicationContext(),"lat",""+location.getLatitude());
        Common.saveUserData(getApplicationContext(),"long",""+location.getLongitude());

        Log.e("lat",""+location.getLatitude());
        Log.e("lat",""+location.getLongitude());




    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ResturantLocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }








            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}