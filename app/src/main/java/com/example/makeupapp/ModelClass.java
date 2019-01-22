package com.example.makeupapp;

import android.widget.Toast;

public class ModelClass {

    String id,brand,name,price,price_sign,image_link,product_link,website_link,description,
            category,colour_name;

    public ModelClass(String id, String brand, String name, String price, String price_sign,
                      String image_link,String product_link, String website_link,
                      String description, String category, String colour_name)
    {
        this.id=id;
        this.brand=brand;
        this.name=name;
        this.price=price;
        this.price_sign=price_sign;
        this.image_link=image_link;
        this.product_link=product_link;
        this.website_link=website_link;
        this.description=description;
        this.category=category;
        this.colour_name=colour_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_sign() {
        return price_sign;
    }

    public void setPrice_sign(String price_sign) {
        this.price_sign = price_sign;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getProduct_link() {
        return product_link;
    }

    public void setProduct_link(String product_link) {
        this.product_link = product_link;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColour_name() {
        return colour_name;
    }

    public void setColour_name(String colour_name) {
        this.colour_name = colour_name;
    }
}
