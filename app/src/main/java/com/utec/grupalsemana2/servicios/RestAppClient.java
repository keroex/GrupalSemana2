package com.utec.grupalsemana2.servicios;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAppClient {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS)
            .build();

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://lezicalandia.ddns.net:8080/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new Gson())).build();
        }
        return retrofit;
    }

}
