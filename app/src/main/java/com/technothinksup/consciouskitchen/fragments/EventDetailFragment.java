package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.extra.DrawableUtils;
import com.technothinksup.consciouskitchen.model.CommonResponse;
import com.technothinksup.consciouskitchen.model.EventDetailResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailFragment extends Fragment {

    private View view;

    private CardView cardView_EventDetailRSVP;


    private ImageView imageView_BackEventDetail, imageView_RewardEventDetail, imageView_CartEventDetail, iv_event_detail;

    private TextView textView_EventDetailTitle, textView_EventDetailDateTime, tv_event_detail, tv_rsvp,tv_event_dialuge_Title,tv_event_dialuge_date_time,tv_dialuge_event_detail;

    private RequestBody reqEventId;

    private String event_id;

    private int eventPaymentStatus = 0;

    private CalendarView calendarView;

    private String date,event_date,event_title,event_desc;

    private LinearLayout linear_layout_event_detail;


    public static EventDetailFragment newInstance() {

        return new EventDetailFragment();
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        Bundle bundle = getArguments();
        event_id = bundle.getString("event_id");

        init();

        setEventDetailData();

       // Toast.makeText(getActivity(), "" + event_id, Toast.LENGTH_SHORT).show();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();


        listener();


        return view;
    }


    private void setEventDetailData() {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

        reqEventId = RequestBody.create(MediaType.parse("multipart/form-data"), event_id);

        Call<EventDetailResponse> call = apiInterface.getEventDetail(reqEventId);

        call.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
               // Toast.makeText(getActivity(), "hii", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {

                    textView_EventDetailTitle.setText(response.body().getEventDetails().get(0).getEventName());
                    textView_EventDetailDateTime.setText(response.body().getEventDetails().get(0).getEventDate() + " " + response.body().getEventDetails().get(0).getEventTime());

                    date = response.body().getEventDetails().get(0).getEventDate();

                    Picasso.with(getActivity())
                            .load(response.body().getEventImagePath() + response.body().getEventDetails().get(0).getEventImage())
                            .into(iv_event_detail);

                    tv_event_detail.setText(response.body().getEventDetails().get(0).getEventDescr());

                    tv_rsvp.setText("" + response.body().getEventDetails().get(0).getEventAppBtnText());

                    event_title=response.body().getEventDetails().get(0).getEventName();
                    event_date=response.body().getEventDetails().get(0).getEventDate() + " " + response.body().getEventDetails().get(0).getEventTime();

                    event_desc=response.body().getEventDetails().get(0).getEventDescr();



                    try {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        List<EventDay> events = new ArrayList<>();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(simpleDateFormat.parse("2021-05-20"));
                        events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(getActivity(), response.body().getEventDetails().get(0).getEventName())));
                        calendarView.setEvents(events);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (response.body().getEventDetails().get(0).getEventTypeName().equalsIgnoreCase("Paid")) {
                        eventPaymentStatus = 1;
                    } else {
                        eventPaymentStatus = 0;
                    }
                }
            }

            @Override
            public void onFailure(Call<EventDetailResponse> call, Throwable t) {

            }
        });

    }

    private void init() {

        cardView_EventDetailRSVP = (CardView) view.findViewById(R.id.cardView_EventDetailRSVP);

        imageView_BackEventDetail = (ImageView) view.findViewById(R.id.imageView_BackEventDetail);
        imageView_RewardEventDetail = (ImageView) view.findViewById(R.id.imageView_RewardEventDetail);
        imageView_CartEventDetail = (ImageView) view.findViewById(R.id.imageView_CartEventDetail);

        textView_EventDetailTitle = view.findViewById(R.id.textView_EventDetailTitle);
        textView_EventDetailDateTime = view.findViewById(R.id.textView_EventDetailDateTime);
        iv_event_detail = view.findViewById(R.id.iv_event_detail);
        tv_event_detail = view.findViewById(R.id.tv_event_detail);

        tv_rsvp = view.findViewById(R.id.tv_rsvp);
        calendarView = view.findViewById(R.id.calendarView);
        linear_layout_event_detail = view.findViewById(R.id.linear_layout_event_detail);



        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                try {

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.event_details_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    TextView txtyes = dialog.findViewById(R.id.yes);

                    tv_event_dialuge_Title = dialog.findViewById(R.id.tv_event_dialuge_Title);
                    tv_event_dialuge_date_time = dialog.findViewById(R.id.tv_event_dialuge_date_time);
                    tv_dialuge_event_detail = dialog.findViewById(R.id.tv_dialuge_event_detail);

                    tv_event_dialuge_Title.setText(event_title);
                    tv_event_dialuge_date_time.setText(event_date);
                    tv_dialuge_event_detail.setText(event_desc);



                    txtyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "your event is book", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                        }

                    });

                    dialog.show();

                   /* orderAmount = orderAmount + Double.parseDouble(customerResponseList.get(i).getOrderAmount());

                    textView.setText("Total Amount " + ""+MainPage.currency+""+orderAmount);*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


        private void listener() {

        imageView_BackEventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardEventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartEventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });


        cardView_EventDetailRSVP.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (eventPaymentStatus == 1) {
                      ((HomePageActivity) getActivity()).loadFragment(new BookEventPaymentFragment(), false);


                } else if (eventPaymentStatus == 0){


                    ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                    Call<CommonResponse>call=apiInterface.bookEvent(HomePageActivity.userId,event_id,"","","","");

                    call.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                            if(response.body().getStatus()==1){
                                Toast.makeText(getActivity(), "Book Your Event Date", Toast.LENGTH_SHORT).show();
                                calendarView.setVisibility(View.VISIBLE);
                                linear_layout_event_detail.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {

                        }
                    });



                }


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

    private static void addToCalendar(Context ctx, final String title, final long dtstart, final long dtend) {
        final ContentResolver cr = ctx.getContentResolver();
        Cursor cursor;
        if (Integer.parseInt(Build.VERSION.SDK) >= 8)
            cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{"_id", "displayname"}, null, null, null);
        else
            cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{"_id", "displayname"}, null, null, null);
        if (cursor.moveToFirst()) {
            final String[] calNames = new String[cursor.getCount()];
            final int[] calIds = new int[cursor.getCount()];
            for (int i = 0; i < calNames.length; i++) {
                calIds[i] = cursor.getInt(0);
                calNames[i] = cursor.getString(1);
                cursor.moveToNext();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setSingleChoiceItems(calNames, -1, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentValues cv = new ContentValues();
                    cv.put("calendar_id", calIds[which]);
                    cv.put("title", title);
                    cv.put("dtstart", dtstart);
                    cv.put("hasAlarm", 1);
                    cv.put("dtend", dtend);

                    Uri newEvent;
                    if (Integer.parseInt(Build.VERSION.SDK) >= 8)
                        newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
                    else
                        newEvent = cr.insert(Uri.parse("content://calendar/events"), cv);

                    if (newEvent != null) {
                        long id = Long.parseLong(newEvent.getLastPathSegment());
                        ContentValues values = new ContentValues();
                        values.put("event_id", id);
                        values.put("method", 1);
                        values.put("minutes", 15); // 15 minutes
                        if (Integer.parseInt(Build.VERSION.SDK) >= 8)
                            cr.insert(Uri.parse("content://com.android.calendar/reminders"), values);
                        else
                            cr.insert(Uri.parse("content://calendar/reminders"), values);

                    }
                    dialog.dismiss();
                }

            });

            builder.create().show();
        }
        cursor.close();


//    private void loadCalenderFragment() {
//
//        FragmentManager fragmentManager = getFragmentManager();
//        assert fragmentManager != null;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout_Home, CalenderFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
//    }

    /*    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), this, yy, mm, dd);
            }

            public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                populateSetDate(yy, mm + 1, dd);
                System.out.println("Click");
            }

            public void populateSetDate(int year, int month, int day) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_Home, ProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }

        }*/

//    private void loadProfileFragment() {
//
//        FragmentManager fragmentManager = getFragmentManager();
//        assert fragmentManager != null;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout_Home, ProfileFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
//    }
    }


}
