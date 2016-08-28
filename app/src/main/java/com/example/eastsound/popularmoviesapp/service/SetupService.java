package com.example.eastsound.popularmoviesapp.service;

import android.app.Application;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamed Fakhry on 27/08/2016.
 */
public class SetupService extends Application{
    public static Service getServiceMovies;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_Movie)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        getServiceMovies = retrofit.create(Service.class);
    }
}
