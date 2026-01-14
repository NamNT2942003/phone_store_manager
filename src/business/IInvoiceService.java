package business;

import model.Invoice;
import model.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IInvoiceService {
    void createOrder(int customerId, List<InvoiceDetail> cart);
    List<Invoice> getAll();

    List<Invoice> searchByCustomer(String name);
    List<Invoice> searchByDate(LocalDate date);

    Map<Integer, Double> getRevenueByDay(int month, int year);
    Map<Integer, Double> getRevenueByMonth(int year);
    Map<Integer, Double> getRevenueByYear();
}
