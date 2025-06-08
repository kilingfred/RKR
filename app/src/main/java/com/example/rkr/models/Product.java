package com.example.rkr.models;

import com.google.gson.annotations.SerializedName;
import java.util.Objects; // Додайте цей імпорт

public class Product {
    @SerializedName("id")
    private int id; // Додайте це поле

    @SerializedName("name")
    private final String name;

    @SerializedName("price")
    private final String price; // Залишаємо String

    @SerializedName("quantity")
    private final String quantity; // Залишаємо String

    @SerializedName("bar_code")
    private final String barCode;

    @SerializedName("category")
    private final String category;

    @SerializedName("manufacturer")
    private String manufacturer;

    @SerializedName("images")
    private final String imageUrl;

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

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product(Integer id, String name, String price, String quantity, String category, String bar_code, String manufacturer, String images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.barCode = bar_code;
        this.manufacturer = manufacturer;
        this.imageUrl = images;
    }

    public Product(String name, String price, String quantity, String category, String bar_code, String images) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.barCode = bar_code;
        this.imageUrl = images;
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