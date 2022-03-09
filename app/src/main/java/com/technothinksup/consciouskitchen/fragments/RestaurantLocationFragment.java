package com.technothinksup.consciouskitchen.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.BranchResponse;
import com.technothinksup.consciouskitchen.model.NearestBranchResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class RestaurantLocationFragment extends Fragment implements OnMapReadyCallback,
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
    private TextView tv_branch_name, tv_branch_time, tv_branch_address, tv_mon_to_sat_time, tv_sun_time;
    private View view;
    private LinearLayoutCompat button_Delivery, button_CurbSide;
    private LinearLayoutCompat button_PickUp;
    private ImageView imageView_Call, imageView_MapOne, imageView_MapTwo, imageView_BackRestaurantLocation, imageView_RewardRestaurantLocation, imageView_CartRestaurantLocation;

    public static RestaurantLocationFragment newInstance() {
        return new RestaurantLocationFragment();
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_location, container, false);
        init();
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
       /* mapFrag = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(RestaurantLocationFragment.this);*/
        //  autocompleteFragment = (AutocompleteSupportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
    /*    mMapView = (MapView) view.findViewById(R.id.users_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });*/
        // setBranchData();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        listener();
        //   SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        /*SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        if (isNetworkAvailable(getActivity())) {
            setNearestBranchData();
        } else NetworkError(getActivity());

        return view;
    }

    private void showErrorLog(final String message) {
        getActivity().runOnUiThread(() ->
        {
            removeProgressBar(getActivity());
            onErrorSnack(getActivity(), message);
        });
    }

    private void init() {
        StateProgressBar stateProgressBar = view.findViewById(R.id.stateprogressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("font/poppinslight.ttf");
        stateProgressBar.setStateNumberTypeface("font/poppinslight.ttf");

        button_PickUp = view.findViewById(R.id.button_PickUp);
        button_Delivery = view.findViewById(R.id.button_Delivery);
        button_CurbSide = view.findViewById(R.id.button_CurbSide);

        imageView_Call = view.findViewById(R.id.imageView_Call);
        imageView_MapOne = view.findViewById(R.id.imageView_MapOne);
        imageView_MapTwo = view.findViewById(R.id.imageView_MapTwo);

        imageView_BackRestaurantLocation = view.findViewById(R.id.imageView_BackRestaurantLocation);
        imageView_RewardRestaurantLocation = view.findViewById(R.id.imageView_RewardRestaurantLocation);
        imageView_CartRestaurantLocation = view.findViewById(R.id.imageView_CartRestaurantLocation);

        tv_branch_name = view.findViewById(R.id.tv_branch_name);
        tv_branch_time = view.findViewById(R.id.tv_branch_time);
        tv_branch_address = view.findViewById(R.id.tv_branch_address);
        tv_mon_to_sat_time = view.findViewById(R.id.tv_mon_to_sat_time);
        tv_sun_time = view.findViewById(R.id.tv_sun_time);
        // mapFragmentContainer = view.findViewById(R.id.mapFragmentContainer);
    }

    private void listener() {
        imageView_BackRestaurantLocation.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardRestaurantLocation.setOnClickListener(view -> loadRewardProcessFragment());
        imageView_CartRestaurantLocation.setOnClickListener(view -> loadReviewOrderFragment());

        button_PickUp.setOnClickListener(view -> {
            Common.order_type = "1";
            // Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();
            loadMenuShowFragment();
        });

        button_Delivery.setOnClickListener(view -> {
            Common.order_type = "2";
            loadDeliveryLocationFragment();
            //Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();
            //loadMenuShowFragment();
        });

        button_CurbSide.setOnClickListener(view -> {
            Common.order_type = "3";
            // Toast.makeText(getActivity(), ""+Common.order_type, Toast.LENGTH_SHORT).show();
            loadCurbsideInstructionFragment();
        });

        imageView_Call.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            callIntent.setData(Uri.parse("tel:" + "5137700160"));
            Objects.requireNonNull(getActivity()).startActivity(callIntent);
        });

        imageView_MapOne.setOnClickListener(view -> {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 39.13345045049334, -84.50896588162078);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            Objects.requireNonNull(getActivity()).startActivity(intent);
        });

        imageView_MapTwo.setOnClickListener(view -> {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 39.13345045049334, -84.50896588162078);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            Objects.requireNonNull(getActivity()).startActivity(intent);
        });
    }

    private void setBranchData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody reqLatitude = RequestBody.create(MediaType.parse("multipart/form-data"), "111");
        RequestBody reqLongitude = RequestBody.create(MediaType.parse("multipart/form-data"), "222");
        Call<BranchResponse> call = apiInterface.getBranchList(reqLatitude, reqLongitude);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                if(response.isSuccessful()){
                    tv_branch_name.setText("" + response.body().getBranchList().get(0).getBranchName());
                    tv_branch_time.setText("" + response.body().getBranchList().get(0).getBranchCreatedAt());
                    String branchAddress = response.body().getBranchList().get(0).getBranchAddress();
                    //  String[] bAdd = branchAddress.split(",");
                    //for(int i=0;i<bAdd.length;i++)
                    tv_branch_address.setText(branchAddress);
                }else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<BranchResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }


    private void loadMenuShowFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, MenuCategoryShowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadDeliveryLocationFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, DeliveryLocationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadCurbsideInstructionFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, CurbsideInstructionFragment.newInstance())
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

    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
    /*    if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
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
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        if (ContextCompat.checkSelfPermission(getActivity(),
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
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9));
        Common.saveUserData(getActivity(), "lat", "" + location.getLatitude());
        Common.saveUserData(getActivity(), "long", "" + location.getLongitude());
        Log.e("lat", "" + location.getLatitude());
        Log.e("lat", "" + location.getLongitude());
        Common.latitude = "" + location.getLatitude();
        Common.longitude = "" + location.getLongitude();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(getActivity(),
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
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setNearestBranchData() {

        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<NearestBranchResponse> call = apiInterface.getNearestBranchList(Common.latitude, Common.longitude);
        call.enqueue(new Callback<NearestBranchResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<NearestBranchResponse> call, Response<NearestBranchResponse> response) {
                if(response.isSuccessful()){
                    // String branchId = response.body().getBranchList().get(0).getBranchId();
                    tv_branch_name.setText("" + response.body().getBranchList().get(0).getBranchName());
                    tv_branch_time.setText("" + response.body().getBranchList().get(0).getDistance() + "Mi");
                    tv_branch_address.setText("" + response.body().getBranchList().get(0).getBranchAddress());

                    tv_mon_to_sat_time.setText("" + response.body().getBranchList().get(0).getMonSatTime());
                    tv_sun_time.setText("" + response.body().getBranchList().get(0).getSunTime());
                }else {
                    switch (response.code()) {
                        case 404:
                            showErrorLog(getString(R.string.something_went_wrong_try_again));
                            break;
                        case 500:
                            showErrorLog(getString(R.string.server_error_msg));
                            break;
                        default:
                            showErrorLog(getString(R.string.unknown_error_try_again) + " " + response.code());
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<NearestBranchResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }
}
