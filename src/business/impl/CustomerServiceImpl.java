package business.impl;

import business.ICustomerService;
import dao.ICustomerDAO;
import dao.impl.CustomerDAOImpl;
import model.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO;

    public CustomerServiceImpl() {
        this.customerDAO = new CustomerDAOImpl();
    }
    @Override
    public List<Customer> getAll() {
        return customerDAO.getAll();
    }
    @Override
    public Customer getById(int id) {
        return customerDAO.getById(id);
    }
    @Override
    public void add(Customer customer) {
        customerDAO.add(customer);
    }
    @Override
    public void update(Customer customer) {
        customerDAO.update(customer);
    }
    @Override
    public void delete(int id) {
        customerDAO.delete(id);
    }



}
