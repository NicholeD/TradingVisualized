package com.kenzie.appserver.service.model;

public class Fish {

    private String id;
    private String name;
    private float size;
    private double quantity;
    private double price;
    private String status;

    public Fish() {
    }

    public Fish(String id, String name, float size, double quantity, double price, String status) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
    public Fish(String name, float size, double quantity, double price, String status) {
        this.id = "Undefined";
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\nFish:" + "\nname: " + name + ",\nsize: " + size + ",\nquantity: " + quantity + ",\nprice: " + price + ",\nstatus: " + status;
    }
}
