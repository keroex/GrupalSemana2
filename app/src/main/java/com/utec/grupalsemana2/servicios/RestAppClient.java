package com.utec.grupalsemana2.servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAppClient {

    private static OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if(retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.iagro.edu.uy/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
