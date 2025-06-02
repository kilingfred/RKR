package com.example.rkr.models;

public class ProductModel {
    private Integer id;
    private String name;
    private String price;
    private String quantity;
    private String category;
    private String bar_code;
    private String manufacturer;
    private String images;

    public ProductModel(Integer id, String name, String price, String quantity, String category, String bar_code, String manufacturer, String images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.bar_code = bar_code;
        this.manufacturer = manufacturer;
        this.images = images;
    }

    public ProductModel(String name, String price, String quantity, String category, String bar_code, String images) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.bar_code = bar_code;
        this.images = images;
    }

    public Integer getId() {
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

    public String getCategory() {
        return category;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getImages() {
        return images;
    }
}
