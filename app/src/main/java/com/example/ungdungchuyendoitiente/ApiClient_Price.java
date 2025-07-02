package com.example.ungdungchuyendoitiente;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient_Price {
    private static  final String BASE_URL = "https://v6.exchangerate-api.com/v6/33504ddc8498cae0008c0ffb/";

    private static Retrofit retrofit;

//    public static Retrofit getClient() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .build();
//        }
//        return retrofit;
//    }
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
