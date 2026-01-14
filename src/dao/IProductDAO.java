package dao;

import model.Product;

import java.util.List;

public interface IProductDAO {
    List<Product> getAllProducts();
    Product getProductById(int id);
    void addProduct(Product p);
    void updateProduct(Product p);
    void deleteProduct(int id);

    // Các hàm tìm kiếm
    List<Product> searchByBrand(String keyword);
    List<Product> searchByPrice(double min, double max);
    List<Product> searchByStock(int min, int max);
}
