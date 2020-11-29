package com.aapnarshop.seller.Http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Anik Roy on 15,September,2020
 */
public class ApiClient {
    public static final String BASE_URL = "http://116.212.109.34:9090/";
    private static Retrofit retrofit = null;

    public static Retrofit getBaseClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}
