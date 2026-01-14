package model;

import util.Formatter;

public class InvoiceDetail {
    private int id;
    private int invoiceId;
    private int productId;
    private int quantity;
    private double unitPrice;

    public InvoiceDetail() {
    }

    public InvoiceDetail(int id, int invoiceId, int productId, int quantity, double unitPrice) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubTotal() {
        return this.quantity * this.unitPrice;
    }

    @Override
    public String toString() {
        return String.format("Mã SP: %-4d | SL: %-3d | Đơn giá: %-12s | Thành tiền: %s",
                productId,
                quantity,
                Formatter.formatMoney(unitPrice),
                Formatter.formatMoney(getSubTotal()));
    }
}