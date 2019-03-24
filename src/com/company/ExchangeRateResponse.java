package com.company;

import java.util.List;

public class ExchangeRateResponse {
    private String date;

   private String baseCurrency;

    private List<ExchangeRates> exchangeRate;



    public List<ExchangeRates> getExchangeRate() {

        return exchangeRate;

    }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "date='" + date + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", exchangeRates=" + exchangeRate +
                '}';
    }
}
