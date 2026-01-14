package dao;

import model.Customer;

import java.util.List;

public interface ICustomerDAO {
    List<Customer> getAll();
    Customer getById(int id);
    void add(Customer customer);
    void update(Customer customer);
    void delete(int id);
}
