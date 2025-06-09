package com.example.rkr.models;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class Product {
    @SerializedName("id")
    private Integer id; // Use Integer to allow null if not set by app

    @SerializedName("name")
    private final String name;

    @SerializedName("price")
    private final String price;

    @SerializedName("quantity")
    private final String quantity;

    @SerializedName("bar_code")
    private final String barCode;

    @SerializedName("category")
    private final String category;

    @SerializedName("manufacturer")
    private String manufacturer;

    @SerializedName("images")
    private final String imageUrl;

    public Product(Integer id, String name, String price, String quantity, String category, String barCode, String manufacturer, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.barCode = barCode;
        this.manufacturer = manufacturer;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String price, String quantity, String category, String barCode, String manufacturer, String imageUrl) {
        this(null, name, price, quantity, category, barCode, manufacturer, imageUrl); // Default id to null
    }

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public String getBarCode() { return barCode; }
    public String getManufacturer() { return manufacturer; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }

    // Setter for id (if needed by server response)
    public void setId(Integer id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
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