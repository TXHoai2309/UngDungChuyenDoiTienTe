package com.example.ungdungchuyendoitiente;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangeRateApi {
    @GET("latest/{baseCurrency}")
    Call<ExchangeRateResponse> getExchangeRates(@Path("baseCurrency") String baseCurrency);
}
