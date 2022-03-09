package com.technothinksup.consciouskitchen.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;


public class BookEventPaymentFragment extends Fragment {

    private View view;

    private CardView cardView_Wallet,cardView_GiftCard,cardView_CreditCard;
    private ImageView imageView_Back;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_book_event_payment, container, false);

        init();

        listener();

        return view;
    }



    private void init() {
        cardView_Wallet=view.findViewById(R.id.cardView_Wallet);
        cardView_GiftCard=view.findViewById(R.id.cardView_GiftCard);
        cardView_CreditCard=view.findViewById(R.id.cardView_CreditCard);
        imageView_Back=view.findViewById(R.id.imageView_Back);
    }

    private void listener() {
        cardView_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBookingConfirmAndPayFragment eventBookingConfirmAndPayFragment = new EventBookingConfirmAndPayFragment();
             Bundle bundle = new Bundle();
             bundle.putString("id", "1");
             eventBookingConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(eventBookingConfirmAndPayFragment, true);
            }
        });

        cardView_GiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBookingConfirmAndPayFragment eventBookingConfirmAndPayFragment = new EventBookingConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "2");
                eventBookingConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(eventBookingConfirmAndPayFragment, true);

            }
        });

        cardView_CreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBookingConfirmAndPayFragment eventBookingConfirmAndPayFragment = new EventBookingConfirmAndPayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "3");
                eventBookingConfirmAndPayFragment.setArguments(bundle);
                ((HomePageActivity) getActivity()).loadFragment(eventBookingConfirmAndPayFragment, true);

            }
        });

        imageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
    }
}