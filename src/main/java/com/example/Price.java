package com.example;

public class Price {
    public Double value;
    public String currency_code;

    public Price() {}

    public Price(Double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }
}
