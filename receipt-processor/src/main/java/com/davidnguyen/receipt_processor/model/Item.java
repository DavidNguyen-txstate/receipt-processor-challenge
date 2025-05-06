package com.davidnguyen.receipt_processor.model;

public class Item {
    private String shortDescription;
    private String price;

    // Setters
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Getters
    public String getShortDescription() {
        return shortDescription;
    }

    public String getPrice() {
        return price;
    }
}
