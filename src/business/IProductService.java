package business;

import model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAll();
    Product getById(int id);
    void add(Product product);
    void update(Product product);
    void delete(int id);

    List<Product> searchByBrand(String keyword);
    List<Product> searchByPrice(double min, double max);
    List<Product> searchByStock(int min, int max);
}
