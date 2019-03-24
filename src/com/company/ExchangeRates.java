package com.company;

public class ExchangeRates {
    private String currency;
    private String saleRateNB;

    public String getCurrency() {
        return currency;
    }

    public String getSaleRateNB() {
        return saleRateNB;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "currency='" + currency + '\'' +
                ", saleRateNB='" + saleRateNB + '\'' +
                '}';
    }
}
