package business.impl;

import business.IInvoiceService;
import dao.IInvoiceDAO;
import dao.impl.InvoiceDAOImpl;
import model.Invoice;
import model.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceDAO invoiceDAO;

    public InvoiceServiceImpl() {
        this.invoiceDAO = new InvoiceDAOImpl();
    }
    @Override
    public void createOrder(int customerId, List<InvoiceDetail> cart) {
        double totalAmount = 0;
        for (InvoiceDetail item : cart) {
            totalAmount += item.getSubTotal();
        }
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setTotalAmount(totalAmount);
        invoiceDAO.createInvoice(invoice, cart);
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceDAO.getAll();
    }
    @Override
    public List<Invoice> searchByCustomer(String name) {
        return invoiceDAO.searchByCustomerName(name);
    }

    @Override
    public List<Invoice> searchByDate(LocalDate date) {
        return invoiceDAO.searchByDate(date);
    }

    @Override
    public Map<Integer, Double> getRevenueByDay(int month, int year) {
        return invoiceDAO.getRevenueByDay(month, year);
    }

    @Override
    public Map<Integer, Double> getRevenueByMonth(int year) {
        return invoiceDAO.getRevenueByMonth(year);
    }

    @Override
    public Map<Integer, Double> getRevenueByYear() {
        return invoiceDAO.getRevenueByYear();
    }
}
