package com.example.rkr.models;

import com.google.gson.annotations.SerializedName;
import java.util.Objects; // Додайте цей імпорт

public class Product {
    @SerializedName("id")
    private int id; // Додайте це поле

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private String price; // Залишаємо String

    @SerializedName("quantity")
    private String quantity; // Залишаємо String

    @SerializedName("bar_code")
    private String barCode;

    @SerializedName("manufacturer")
    private String manufacturer;

    // Геттери для всіх полів
    public int getId() { // Додайте геттер для id
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        // Порівняння за id є надійнішим, якщо id унікальний
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(barCode, product.barCode) &&
                Objects.equals(manufacturer, product.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, barCode, manufacturer);
    }
}