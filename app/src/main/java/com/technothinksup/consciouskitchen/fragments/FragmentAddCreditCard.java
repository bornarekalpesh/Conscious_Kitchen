package com.technothinksup.consciouskitchen.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddCreditCard extends Fragment implements EncryptTransactionCallback {

    private View view;

    String[] descriptionData = {"Choose\nLocation", "Order\nSelection", "Review\nOrder", "Schedule\nOrder", "Confirm\n & Pay"};

    private FormEditText et_cardNo,et_month,et_cvv,et_name,et_yy;

    private CardView cardView_AddCreditCardContinueToPay;

    private TextView textView_AddCreditCardCross,textView_AddCreditCardDone;


    private RelativeLayout relativeLayout_ScanCard;

    private  ImageView imageView_BackAddCard,imageView_RewardAddCard,imageView_CartAddCard;

    private AcceptSDKApiClient apiClient;


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
         view = inflater.inflate(R.layout.fragment_add_credit, container, false);


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
               loadConfirmAndPayFragment();

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

                            ConfirmAndPayFragment confirmAndPayFragment = new ConfirmAndPayFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id", "3");
                            confirmAndPayFragment.setArguments(bundle);
                            ((HomePageActivity) getActivity()).loadFragment(confirmAndPayFragment, true);


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
            }
        });
    }




    private void init() {

        StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.confirm_pay_progressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);


        stateProgressBar.setStateDescriptionTypeface("fonts/RobotoSlab-Light.ttf");
        stateProgressBar.setStateNumberTypeface("fonts/Questrial-Regular.ttf");

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




        et_cardNo.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }
        });



    }



        private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
            boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
            for (int i = 0; i < s.length(); i++) { // check that every element is right
                if (i > 0 && (i + 1) % dividerModulo == 0) {
                    isCorrect &= divider == s.charAt(i);
                } else {
                    isCorrect &= Character.isDigit(s.charAt(i));
                }
            }
            return isCorrect;
        }

        private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
            final StringBuilder formatted = new StringBuilder();

            for (int i = 0; i < digits.length; i++) {
                if (digits[i] != 0) {
                    formatted.append(digits[i]);
                    if ((i > 0) && (i < (digits.length -1)) && (((i + 1) % dividerPosition) == 0)) {
                        formatted.append(divider);
                    }
                }
            }

            return formatted.toString();
        }

        private char[] getDigitArray(final Editable s, final int size) {
            char[] digits = new char[size];
            int index = 0;
            for (int i = 0; i < s.length() && index < size; i++) {
                char current = s.charAt(i);
                if (Character.isDigit(current)) {
                    digits[index] = current;
                    index++;
                }
            }
            return digits;
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


}
