package com.technothinksup.consciouskitchen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.andreabaccega.widget.FormEditText;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.technothinksup.consciouskitchen.extra.Common;
import com.technothinksup.consciouskitchen.model.CustomerLoginResponse;
import com.technothinksup.consciouskitchen.model.CustomerSignUpResponse;
import com.technothinksup.consciouskitchen.retrofit.Api;
import com.technothinksup.consciouskitchen.retrofit.ApiInterface;
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleCallback;
import com.willowtreeapps.signinwithapplebutton.SignInWithAppleConfiguration;
import com.willowtreeapps.signinwithapplebutton.view.SignInWithAppleButton;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;
import static com.technothinksup.consciouskitchen.extra.Common.removeProgressBar;
import static com.technothinksup.consciouskitchen.extra.Helper.NetworkError;
import static com.technothinksup.consciouskitchen.extra.Helper.isNetworkAvailable;
import static com.technothinksup.consciouskitchen.extra.Helper.onErrorSnack;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    ActionBar actionBar;
    AppCompatTextView textView_LoginForgot;
    AppCompatButton button_Login, button_LoginJoinNow, button_LoginApple, button_LoginGuest;
    LoginButton loginButton;
    SignInButton button_LoginGoogle;
    public static final String TAG = "MyTag";



    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};
    private FormEditText et_email, et_password;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private final int RC_SIGN_IN = 100;


    SignInWithAppleButton signInWithAppleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        init();
        isLoggedIn();
        signInWithGoogle();
        loginWithFB();
        onClickListeners();
        signInWithApple();

    }

    private void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();*/
       /* googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        callbackManager = CallbackManager.Factory.create();
        //   loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.setReadPermissions("email", "public_profile");
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void onClickListeners() {
        textView_LoginForgot.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotPasswordOneActivity.class)));
        button_LoginJoinNow.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        button_Login.setOnClickListener(view -> {
            if (et_email.testValidity() && et_password.testValidity()) {
                if (isNetworkAvailable(LoginActivity.this)) {
                    login(); //call login
                } else NetworkError(LoginActivity.this);
            }
        });
        button_LoginGoogle.setOnClickListener(view -> signIn());
        button_LoginApple.setOnClickListener(view -> {
            Intent appleLogin = new Intent(LoginActivity.this, HomePageActivity.class);
            appleLogin.putExtra("logintype", "o");
            startActivity(appleLogin);
        });
        button_LoginGuest.setOnClickListener(view -> {
            Intent guestLogin = new Intent(LoginActivity.this, HomePageActivity.class);
            guestLogin.putExtra("logintype", "g");
            startActivity(guestLogin);
        });
    }

    private void signInWithApple() {
        SignInWithAppleConfiguration configuration = new SignInWithAppleConfiguration.Builder()
                .clientId("apple.com")
                .redirectUri("https://your-redirect-uri.com/callback")
                .scope("email name")
                .build();
        SignInWithAppleCallback callback = new SignInWithAppleCallback() {
            @Override
            public void onSignInWithAppleSuccess(@NonNull String authorizationCode) {
                Toast.makeText(LoginActivity.this, authorizationCode, LENGTH_SHORT).show();
            }

            @Override
            public void onSignInWithAppleFailure(@NonNull Throwable error) {
                Log.d("SAMPLE_APP", "Received error from Apple Sign In " + error.getMessage());
            }

            @Override
            public void onSignInWithAppleCancel() {
                Log.d("SAMPLE_APP", "User canceled Apple Sign In");
            }
        };
        signInWithAppleButton.setUpSignInWithAppleOnClick(getSupportFragmentManager(), configuration, callback);
    }

    private void loginWithFB() {
        loginButton.setOnClickListener(view -> {
            // Toast.makeText(LoginActivity.this,"On click Login Btn", LENGTH_SHORT).show();
            //startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
        });
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(LoginActivity.this, "hii", LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onCancel() {
                // Toast.makeText(LoginActivity.this," Login Btn Cancel", LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                //Toast.makeText(LoginActivity.this, "fbExc"+exception.getMessage(), LENGTH_SHORT).show();
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        /* Intent i =new Intent(LoginActivity.this,HomePageActivity.class);
                        startActivity(i);*/
                        //   Toast.makeText(LoginActivity.this,"On Success Login Manager", LENGTH_SHORT).show();
                        System.out.println("onSuccess");
                        String accessToken = loginResult.getAccessToken()
                                .getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), (object, response) -> {
                                    Toast.makeText(LoginActivity.this, "Successfully Logged In", LENGTH_SHORT).show();
                                    Log.i("LoginActivity",
                                            response.toString());
                                    try {
                                        String id = object.getString("id");
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        if (isNetworkAvailable(LoginActivity.this)) {
                                            //call login
                                            socialRegistration("facebook",id, name, email);
                                        } else NetworkError(LoginActivity.this);
                                        /* try {
                                            URL profile_pic = new URL(
                                                    "http://graph.facebook.com/" + id + "/picture?type=large");
                                            Log.i("profile_pic",
                                                    profile_pic + "");

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }*//*
                                         */
                                        /* String gender = object.getString("gender");
                                        String birthday = object.getString("birthday");
                                        String hometown = object.getString("hometown");*/
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(LoginActivity.this, "APi Erro" + e.getMessage(), LENGTH_SHORT).show();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email, gender, birthday, hometown");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "fbexc" + exception.getMessage(), Toast.LENGTH_LONG).show();
                        // App code
                    }
                });
    }

    private void isLoggedIn() {
        boolean fromChangePasswordScreen = false;
        if (getIntent() != null) {
            if (getIntent().hasExtra("FromChangePasswordScreen")) {
                fromChangePasswordScreen = getIntent().getBooleanExtra("FromChangePasswordScreen", false);
            }
        }
        if (!fromChangePasswordScreen) {
            File file = new File("data/data/com.technothinksup.consciouskitchen/shared_prefs/user.xml");
            if (file.exists()) {
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                intent.putExtra("logintype", "o");
                startActivity(intent);
                finish();
            }
        }
    }

    private void login() {
        Common.showProgressBar(LoginActivity.this);
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        RequestBody req_email = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_email.getText().toString());
        RequestBody req_password = RequestBody.create(MediaType.parse("multipart/form-data"), "" + et_password.getText().toString());
        RequestBody req_dev_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

        Call<CustomerLoginResponse> call = apiInterface.loginCustomer(req_email, req_password, req_dev_id);

        call.enqueue(new Callback<CustomerLoginResponse>() {
            @Override
            public void onResponse(Call<CustomerLoginResponse> call, Response<CustomerLoginResponse> response) {
                if (response.isSuccessful()) {
                    removeProgressBar(LoginActivity.this);
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), LENGTH_SHORT).show();
                        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("UserLogin", "UserLoginSuccessful");
                        editor.apply();
                        editor.commit();
                        Common.saveUserData(LoginActivity.this, "image_path", response.body().getImagePath());
                        Common.saveUserData(LoginActivity.this, "customer_image", response.body().getCustomerInfo().get(0).getCustomerImage());
                        Common.saveUserData(LoginActivity.this, "customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                        Common.saveUserData(LoginActivity.this, "fName", response.body().getCustomerInfo().get(0).getCustomerName());
                        Common.saveUserData(LoginActivity.this, "lName", response.body().getCustomerInfo().get(0).getCustomerLname());
                        Common.saveUserData(LoginActivity.this, "email", response.body().getCustomerInfo().get(0).getCustomerEmail());
                        Common.saveUserData(LoginActivity.this, "mobile", response.body().getCustomerInfo().get(0).getCustomerMobile());
                        Common.saveUserData(LoginActivity.this, "password", response.body().getCustomerInfo().get(0).getCustomerPassword());
                        Common.saveUserData(LoginActivity.this, "dob", response.body().getCustomerInfo().get(0).getCustomer_dob());

                        Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                        i.putExtra("logintype", "o");
                        startActivity(i);
                        finish();
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), LENGTH_SHORT).show();
                    }
                } else {
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
            public void onFailure(Call<CustomerLoginResponse> call, Throwable e) {
                Log.e("TAG", "onError: " + e.toString());
                if (e instanceof SocketTimeoutException)
                    showErrorLog(getString(R.string.connection_time_out));
                else if (e instanceof IOException)
                    showErrorLog(getString(R.string.weak_connection));
                else showErrorLog(e.toString());
            }
        });
    }

    private void showErrorLog(final String message) {
        runOnUiThread(() ->
        {
            removeProgressBar(LoginActivity.this);
            onErrorSnack(this, message);
        });
    }

    private void signIn() {
        Log.e("Sign In Completed", "Sign In Completed");
        Intent signInIntent = googleSignInClient.getSignInIntent();
        // Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void init() {
        textView_LoginForgot = findViewById(R.id.textView_LoginForgot);
        button_Login = findViewById(R.id.button_Login);
        button_LoginJoinNow = findViewById(R.id.button_LoginJoinNow);
        loginButton = findViewById(R.id.button_LoginFB);
        button_LoginGoogle = findViewById(R.id.button_LoginGoogle);
        button_LoginApple = findViewById(R.id.button_LoginApple);
        button_LoginGuest = findViewById(R.id.button_LoginGuest);
        signInWithAppleButton = findViewById(R.id.sign_in_with_apple_button);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
    }

    private void socialRegistration(String oauth_provider, String id, String name, String email) {
        ApiInterface apiInterface = Api.getClient().create(ApiInterface.class);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@afu.org.checkerframework.checker.nullness.qual.NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;

                        }
                        String device_id;
                        device_id = task.getResult();
                        Call<CustomerSignUpResponse> call = apiInterface.socialRegistration(oauth_provider, id, name, "", "", email, "0", "0", "0", device_id);
                        Common.showProgressBar(LoginActivity.this);
                        call.enqueue(new Callback<CustomerSignUpResponse>() {
                            @Override
                            public void onResponse(Call<CustomerSignUpResponse> call, Response<CustomerSignUpResponse> response) {

                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == 1) {
                                        removeProgressBar(LoginActivity.this);
                                        pref = getSharedPreferences("user", Context.MODE_PRIVATE);
                                        editor = pref.edit();
                                        editor.putString("UserLogin", "UserLoginSuccessful");
                                        editor.apply();
                                        editor.commit();

                                        Common.saveUserData(LoginActivity.this, "customer_id", response.body().getCustomerInfo().get(0).getCustomerId());
                                        Common.saveUserData(LoginActivity.this, "fName", response.body().getCustomerInfo().get(0).getCustomerName());
                                        Common.saveUserData(LoginActivity.this, "lName", response.body().getCustomerInfo().get(0).getCustomerLname());
                                        Common.saveUserData(LoginActivity.this, "email", response.body().getCustomerInfo().get(0).getCustomerEmail());
                                        Common.saveUserData(LoginActivity.this, "mobile", response.body().getCustomerInfo().get(0).getCustomerMobile());
                                        Common.saveUserData(LoginActivity.this, "password", response.body().getCustomerInfo().get(0).getCustomerPassword());
                                        Common.saveUserData(LoginActivity.this, "dob", response.body().getCustomerInfo().get(0).getCustomer_dob());

                                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), LENGTH_SHORT).show();

                                        Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                                        i.putExtra("logintype", "o");
                                        startActivity(i);
                                    } else if (response.body().getStatus() == 0) {

                                        removeProgressBar(LoginActivity.this);
                                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), LENGTH_SHORT).show();
                                    }
                                } else {
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
                            public void onFailure(Call<CustomerSignUpResponse> call, Throwable e) {
                                removeProgressBar(LoginActivity.this);
                                Log.e("TAG", "onError: " + e.toString());
                                if (e instanceof SocketTimeoutException)
                                    showErrorLog(getString(R.string.connection_time_out));
                                else if (e instanceof IOException)
                                    showErrorLog(getString(R.string.weak_connection));
                                else showErrorLog(e.toString());
                            }
                        });
                    }
                });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //     GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //     int statuscode=result.getStatus().getStatusCode();
            //    Log.e("GoogleSignInResult", ""+statuscode);
            handleSignInResult(task);
            Log.e("Sign In done", "Sign In done");
           /* Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.e("google Task", ""+task);
            handleSignInResult(data);*/
        } else {
            Log.e("String", "eee");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.e("InHandlesigninresult", "eee");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            // Signed in successfully, show authenticated UI.
            //   updateUI(account);
            if (acct != null) {
                String id = acct.getId();
                String personName = acct.getDisplayName();
                String personPhotoUrl = acct.getPhotoUrl().toString();
                String email = acct.getEmail();
                Log.e("cbhf", "Name: " + personName + ", email: " + email
                        + ", Image: " + personPhotoUrl);
                if (isNetworkAvailable(LoginActivity.this)) {
                    //call login
                    socialRegistration("google",id, personName, email);
                } else NetworkError(LoginActivity.this);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("GOOGLE SIGN IN", "signInResult:failed code=" + e.getStatusCode());
            //  updateUI(null);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}