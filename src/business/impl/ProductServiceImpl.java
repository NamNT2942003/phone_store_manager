package business.impl;

import dao.IProductDAO;
import dao.impl.ProductDAOImpl;
import model.Product;

import java.util.List;

public class ProductService implements IProductDAO {
    private final  IProductDAO productDAO;

    public 














    public ProductService() {
        this.productDAO = new ProductDAOImpl();
    }
    public List<Product> getAll() {
        return productDAO.getAllProducts();
    }
    public Product getById(int id) {
        return productDAO.getProductById(id);
    }
    public void add(Product product) {
        productDAO.addProduct(product);
    }
    public void update(Product product) {
        productDAO.updateProduct(product);
    }
    public void delete(int id) {
        productDAO.deleteProduct(id);
    }
    public List<Product> searchByBrand(String keyword) {
        return productDAO.searchByBrand(keyword);
    }
    public List<Product> searchByPrice(double min, double max) {
        if (min > max) {
            System.out.println(">> Lỗi logic: Giá min không được lớn hơn giá max!");
            return null;
        }
        return productDAO.searchByPrice(min, max);
    }
    public List<Product> searchByStock(int min, int max) {
        return productDAO.searchByStock(min, max);
    }

}
