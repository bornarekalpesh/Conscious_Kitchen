package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import com.andreabaccega.widget.FormEditText;
import com.technothinksup.consciouskitchen.HomePageActivity;
import com.technothinksup.consciouskitchen.R;
import com.technothinksup.consciouskitchen.model.PaymentCardResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.kofigyan.stateprogressbar.StateProgressBar;

import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

import java.util.Objects;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOnlyAddCreditCard extends Fragment implements EncryptTransactionCallback {

    private View view;

    //String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private FormEditText et_cardNo,et_month,et_cvv,et_name,et_yy;

    private CardView cardView_AddCreditCardContinueToPay;

    private TextView textView_AddCreditCardCross,textView_AddCreditCardDone;


    private RelativeLayout relativeLayout_ScanCard;

    private  ImageView imageView_BackAddCard,imageView_RewardAddCard,imageView_CartAddCard;

    private AcceptSDKApiClient apiClient;
    public static int MY_SCAN_REQUEST_CODE=110;

    private final String CARD_NUMBER = "4111111111111111";
    private final String EXPIRATION_MONTH = "11";
    private final String EXPIRATION_YEAR = "2025";
    private final String CVV = "256";
    private final String POSTAL_CODE = "98001";
    private final String CLIENT_KEY =
            "3smkphZB792en5XhJSjC8rABTEt2392XnFTvp48PxmjS93gv4Cg9L8JxGEJCwwF4";
    // replace with your CLIENT KEY
    private final String API_LOGIN_ID = "6E3W2rnh4J"; // replace with your API LOGIN_ID


    public static FragmentAddCreditCard newInstance() {

        return new FragmentAddCreditCard();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            apiClient = new AcceptSDKApiClient.Builder(getActivity(),
                    AcceptSDKApiClient.Environment.SANDBOX).connectionTimeout(
                    4000) // optional connection time out in milliseconds
                    .build();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_only_add_credit_card, container, false);


        init();

        listener();


        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();



        return view;
    }

    private void listener() {

        textView_AddCreditCardCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_BackAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });

        imageView_RewardAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardProcessFragment();
            }
        });

        imageView_CartAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReviewOrderFragment();
            }
        });

        textView_AddCreditCardDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  loadConfirmAndPayFragment();
            }
        });

        cardView_AddCreditCardContinueToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);

                Call<PaymentCardResponse>call=apiInterface.savePaymentCard(HomePageActivity.userId,et_cardNo.getText().toString(),et_month.getText().toString(),et_yy.getText().toString(),et_cvv.getText().toString(),et_name.getText().toString());

                call.enqueue(new Callback<PaymentCardResponse>() {
                    @Override
                    public void onResponse(Call<PaymentCardResponse> call, Response<PaymentCardResponse> response) {

                        //  Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();


                        if(response.body().getStatus()==1){
                            //  loadTrackNowFragment();

                          /*  ConfirmAndPayFragment confirmAndPayFragment = new ConfirmAndPayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", "3");
                            confirmAndPayFragment.setArguments(bundle);
                            ((HomePageActivity) getActivity()).loadFragment(confirmAndPayFragment, true);
*/
                             Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }

                    @Override
                    public void onFailure(Call<PaymentCardResponse> call, Throwable t) {

                    }
                });



            }
        });

        relativeLayout_ScanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadScanCardFragment();
                onScanPress(view);


            }
        });
    }




    private void init() {

      //  StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        //stateProgressBar.setStateDescriptionData(descriptionData);


        //stateProgressBar.setStateDescriptionTypeface("fonts/RobotoSlab-Light.ttf");
        //stateProgressBar.setStateNumberTypeface("fonts/Questrial-Regular.ttf");

        cardView_AddCreditCardContinueToPay =  view.findViewById(R.id.cardView_AddCreditCardContinueToPay);
        relativeLayout_ScanCard =  view.findViewById(R.id.relativeLayout_ScanCard);
        textView_AddCreditCardCross =  view.findViewById(R.id.textView_AddCreditCardCross);
        textView_AddCreditCardDone = view.findViewById(R.id.textView_AddCreditCardDone);


        imageView_BackAddCard =  view.findViewById(R.id.imageView_BackAddCard);
        imageView_RewardAddCard = view.findViewById(R.id.imageView_RewardAddCard);
        imageView_CartAddCard =  view.findViewById(R.id.imageView_CartAddCard);



        et_cardNo =  view.findViewById(R.id.et_cardNo);
        et_month =  view.findViewById(R.id.et_month);
        et_cvv =  view.findViewById(R.id.et_cvv);
        et_name =  view.findViewById(R.id.et_name);
        et_yy =  view.findViewById(R.id.et_yy);


    }

    private EncryptTransactionObject prepareTransactionObject() {
        ClientKeyBasedMerchantAuthentication merchantAuthentication =
                ClientKeyBasedMerchantAuthentication.
                        createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

        // create a transaction object by calling the predefined api for creation
        return TransactionObject.
                createTransactionObject(
                        TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(prepareTestCardData()) // card data to get Token
                .merchantAuthentication(merchantAuthentication).build();
    }

    private EncryptTransactionObject prepareTestTransactionObject() {
        ClientKeyBasedMerchantAuthentication merchantAuthentication =
                ClientKeyBasedMerchantAuthentication.
                        createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);

        // create a transaction object by calling the predefined api for creation
        return EncryptTransactionObject.
                createTransactionObject(
                        TransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(prepareTestCardData()) // card data to prepare token
                .merchantAuthentication(merchantAuthentication).build();
    }

    private CardData prepareTestCardData() {
        return new CardData.Builder(CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR).cvvCode(CVV)
                .zipCode(POSTAL_CODE)
                .cardHolderName("john")
                .build();
    }

    /*private CardData prepareCardDataFromFields() {
        return new CardData.Builder(cardNumber, month, year).cvvCode(cvv) //CVV Code is optional
                .build();
    }*/

    private void loadTrackNowFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, TrackNowFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void loadScanCardFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ScanCardFragment.newInstance())
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

    private void loadConfirmAndPayFragment() {

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_Home, ConfirmAndPayFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onErrorReceived(ErrorTransactionResponse error) {
        Message errors = error.getFirstErrorMessage();
        Toast.makeText(getActivity(),
                errors.getMessageCode() + " : " + errors.getMessageText() ,
                Toast.LENGTH_LONG)
                .show();

        Log.e("error",""+errors.getMessageCode() + " : " + errors.getMessageText());

    }

    @Override
    public void onEncryptionFinished(EncryptTransactionResponse response) {

        Toast.makeText(getActivity(),
                response.getDataDescriptor() + " : " + response.getDataValue(),
                Toast.LENGTH_LONG)
                .show();
    }

    public void onScanPress(View v) {
        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
            Toast.makeText(getActivity(),resultDisplayStr,Toast.LENGTH_SHORT).show();
        }
        // else handle other activity results
    }

}

