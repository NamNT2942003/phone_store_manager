package model;

import util.InputHelper;

public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private int stock;

    public Product() {
    }
    public Product(int id, String name, String brand, double price, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void inputData() {
        this.name = InputHelper.getString("Nhập tên sản phẩm: ");
        this.brand = InputHelper.getString("Nhập hãng (Brand): ");
        this.price = InputHelper.getPositiveDouble("Nhập giá bán: ");
        this.stock = InputHelper.getPositiveInt("Nhập số lượng tồn kho: ");
    }
    @Override
    public String toString() {
        return String.format("ID: %d | Tên: %-20s | Hãng: %-10s | Giá: %,.0f VND | Tồn: %d",
                id, name, brand, price, stock);
    }

}
