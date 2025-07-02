package com.example.ungdungchuyendoitiente;

import com.google.gson.annotations.SerializedName;
import java.util.Map;
public class ExchangeRateResponse {

    @SerializedName("base_code")
    private String baseCurrency;


    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates;

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

}
