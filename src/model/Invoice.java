package model;

import util.Formatter;

import java.time.LocalDate;

public class Invoice {
    private int id;
    private int customerId;
    private LocalDate createdAt;
    private double totalAmount;
    private String customerName;

    public Invoice() {}

    public Invoice(int id, int customerId, LocalDate createdAt, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @Override
    public String toString() {
        return String.format("Mã HĐ: %d | Khách: %s | Ngày: %s | Tổng: %s",
                id,
                customerName, // Cập nhật toString
                Formatter.formatDate(createdAt),
                Formatter.formatMoney(totalAmount));
    }
}
