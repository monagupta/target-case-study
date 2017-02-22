package com.example;

public class Product {
    public int id;
    public String name;
    public Price current_price;

    public Product() {}

    public Product(int id, String name, Price current_price) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }
}
