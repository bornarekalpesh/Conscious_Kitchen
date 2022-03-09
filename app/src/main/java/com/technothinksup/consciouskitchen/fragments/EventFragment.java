package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.technothinksup.consciouskitchen.adapter.HorizontalAdapter;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.adapter.PopularEventAdapter;
import com.technothinksup.consciouskitchen.adapter.UpcomingEventAdapter;
import com.technothinksup.consciouskitchen.model.EventList;
import com.technothinksup.consciouskitchen.model.EventResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {

    private HorizontalAdapter horizontalAdapter;
    private RecyclerView recyclerView_EventFirst, rv_upcoming_event, rv_popular_event;
    private View view;
    List<EventList> eventLists = new ArrayList<>();

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        init();
        AppCompatImageView imageView_BackEvents = view.findViewById(R.id.imageView_BackEvents);
        AppCompatImageView imageView_RewardEvents = view.findViewById(R.id.imageView_RewardEvents);
        AppCompatImageView imageView_CartEvents = view.findViewById(R.id.imageView_CartEvents);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        imageView_BackEvents.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        imageView_RewardEvents.setOnClickListener(view -> loadRewardProcessFragment());
        imageView_CartEvents.setOnClickListener(view -> loadReviewOrderFragment());
        setFirstAdapter();

        //setUpcomingAdapter();
        //   setpopularAdapter();
      /*  final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_EventFirst.setLayoutManager(layoutManager);
        recyclerView_EventFirst.setAdapter(new HorizontalAdapter(new int[]{R.drawable.friday_food,R.drawable.friday_food},new String[]{"Friday Food Festival","Celebrate Local Food"}));*/
     /*   final LinearLayoutManager layoutManagerSecond = new LinearLayoutManager(this.getActivity());
        layoutManagerSecond.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_EventSecond.setLayoutManager(layoutManagerSecond);
        recyclerView_EventSecond.setAdapter(new HorizontalAdapter(new int[]{R.drawable.celebrate_local, R.drawable.celebrate_local}, new String[]{"Celebrate Local Food", "Friday Food Festival"}));
        final LinearLayoutManager layoutManagerThird = new LinearLayoutManager(this.getActivity());
        layoutManagerThird.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_EventThird.setLayoutManager(layoutManagerThird);
        recyclerView_EventThird.setAdapter(new HorizontalAdapter(new int[]{R.drawable.valentine, R.drawable.valentine}, new String[]{"Valentine Day", "Friday Food Festival"}));
        return view;*/

        return view;
    }

    private void init() {
        recyclerView_EventFirst = view.findViewById(R.id.recyclerView_EventFirst);
        rv_upcoming_event = view.findViewById(R.id.rv_upcoming_event);
        rv_popular_event = view.findViewById(R.id.rv_popular_event);
    }

    private void setFirstAdapter() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<EventResponse> call = apiInterface.getEventList();
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                horizontalAdapter = new HorizontalAdapter(getActivity(), response.body().getEventList(), response.body().getEventImagePath());
                recyclerView_EventFirst.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView_EventFirst.setAdapter(horizontalAdapter);
                horizontalAdapter.notifyDataSetChanged();
                recyclerView_EventFirst.setHasFixedSize(true);
                setUpcomingAdapter();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
            }
        });
    }


    private void setUpcomingAdapter() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<EventResponse> call = apiInterface.getEventList();
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                List<EventList> polularEventLists = new ArrayList<>();
                eventLists = response.body().getEventList();
                if (eventLists.size() > 0) {
                    for (int i = 0; i < eventLists.size(); i++) {
                        try {
                            // Create SimpleDateFormat object
                            SimpleDateFormat sdfo = new SimpleDateFormat("dd-MM-yyyy");
                            // Get the two dates to be compared
                            Date d1 = sdfo.parse("" + eventLists.get(i).getEventDate());
                            Date c = Calendar.getInstance().getTime();
                            System.out.println("Current time => " + c);
                            String formattedDate = sdfo.format(c);
                            Date d2 = sdfo.parse("" + formattedDate);
                            // Print the dates
                            System.out.println("Date1 : " + sdfo.format(d1));
                            System.out.println("Date2 : " + sdfo.format(d2));
                            // Compare the dates using compareTo()
                            if (d1.compareTo(d2) > 0) {
                                // When Date d1 > Date d2
                                System.out.println("Date1 is before Date2");
                                polularEventLists.add(eventLists.get(i));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("eventLists", "" + polularEventLists.size());
                }
                if (polularEventLists.size() > 0) {
                    rv_upcoming_event.setVisibility(View.VISIBLE);
                    UpcomingEventAdapter upcomingEventAdapter = new UpcomingEventAdapter(getActivity(), polularEventLists, response.body().getEventImagePath());
                    rv_upcoming_event.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    rv_upcoming_event.setAdapter(upcomingEventAdapter);
                    upcomingEventAdapter.notifyDataSetChanged();
                    rv_upcoming_event.setHasFixedSize(true);
                } else {
                    rv_upcoming_event.setVisibility(View.GONE);
                }
                setpopularAdapter();
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
            }
        });
    }


    private void setpopularAdapter() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        Call<EventResponse> call = apiInterface.getEventList();
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                List<EventList> polularEventLists = new ArrayList<>();
                eventLists = response.body().getEventList();
                if (eventLists.size() > 0) {
                    for (int i = 0; i < eventLists.size(); i++) {
                        try {
                            if (eventLists.get(i).getEventIsPopular().equalsIgnoreCase("1")) {
                                polularEventLists.add(eventLists.get(i));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                   /* try {
                        // Create SimpleDateFormat object
                        SimpleDateFormat sdfo = new SimpleDateFormat("dd-MM-yyyy");
                        // Get the two dates to be compared
                        Date d1 = sdfo.parse(""+eventLists.get(i).getEventDate());
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        String formattedDate = sdfo.format(c);
                        Date d2 = sdfo.parse(""+formattedDate);
                        // Print the dates
                        System.out.println("Date1 : " + sdfo.format(d1));
                        System.out.println("Date2 : " + sdfo.format(d2));
                        // Compare the dates using compareTo()
                        if (d1.compareTo(d2) > 0) {
                            // When Date d1 > Date d2
                            System.out.println("Date1 is after Date2");
                            Toast.makeText(getActivity(), "greater date", Toast.LENGTH_SHORT).show();
                            UpcomingEventAdapter horizontalAdapter = new UpcomingEventAdapter(getActivity(), response.body().getEventList(),response.body().getEventImagePath());
                            rv_upcoming_event.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                            rv_upcoming_event.setAdapter(horizontalAdapter);
                            horizontalAdapter.notifyDataSetChanged();
                            rv_upcoming_event.setHasFixedSize(true);
                        } else {
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    }
                    Log.e("eventLists", "" + polularEventLists.size());

                }
                PopularEventAdapter horizontalAdapter = new PopularEventAdapter(getActivity(), polularEventLists, response.body().getEventImagePath());
                rv_popular_event.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rv_popular_event.setAdapter(horizontalAdapter);
                horizontalAdapter.notifyDataSetChanged();
                rv_popular_event.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
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

    private void loadRewardProcessFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, RewardProcessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

}
