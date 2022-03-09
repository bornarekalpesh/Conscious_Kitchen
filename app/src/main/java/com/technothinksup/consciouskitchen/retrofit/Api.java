package com.technothinksup.consciouskitchen.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {

    public static String token = null;
    private static Retrofit retrofit = null;
   // private static String BASE_URL = "https://consciouskitchen513.com/Admin/Mobile_App/";
    //private static String BASE_URL = "http://consciouskitchen513.aog.vby.mybluehost.me/Admin/Mobile_App/";
    private static String BASE_URL = "http://consciouskitchen513.aog.vby.mybluehost.me/Dev-Admin/Mobile_App/";

    // private static String BASE_URL = "https://technothinksupapps.com/restaurant/Mobile_App/";


    public static Retrofit getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.MINUTES)
                    .readTimeout(50, TimeUnit.MINUTES)
                    .addInterceptor(logging)
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
