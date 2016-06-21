package com.henry.freakcompany.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by henry on 2016/6/15.
 */
public class RetrofitFactory {

    public static final String BASE_URL = "http://gank.io/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit == null) {
            synchronized (RetrofitFactory.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }
}
