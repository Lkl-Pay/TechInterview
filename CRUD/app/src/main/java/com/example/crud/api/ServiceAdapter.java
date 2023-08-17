package com.example.crud.api;

import com.example.crud.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAdapter {

    public static Retrofit buildRetrofit ()
    {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://crudapi.co.uk")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
