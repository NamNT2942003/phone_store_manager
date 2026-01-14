package dao;

import model.Invoice;
import model.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IInvoiceDAO {
    boolean createInvoice(Invoice invoice, List<InvoiceDetail> details);
    List<Invoice> getAll();
    List<Invoice> searchByCustomerName(String keyword);
    List<Invoice> searchByDate(LocalDate date);

    Map<Integer, Double> getRevenueByDay(int month, int year);
    Map<Integer, Double> getRevenueByMonth(int year);
    Map<Integer, Double> getRevenueByYear();
}