package business.impl;

import business.IProductService;
import dao.IProductDAO;
import dao.impl.ProductDAOImpl;
import model.Product;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    private final IProductDAO productDAO;
    public ProductServiceImpl() {
        this.productDAO = new ProductDAOImpl();
    }
    @Override
    public List<Product> getAll() {
        return productDAO.getAllProducts();
    }
    @Override
    public void add(Product product) {
        productDAO.addProduct(product);
    }
    @Override
    public Product getById(int id) { return productDAO.getProductById(id); }
    @Override
    public void update(Product product) { productDAO.updateProduct(product); }
    @Override
    public void delete(int id) { productDAO.deleteProduct(id); }
    @Override
    public List<Product> searchByBrand(String keyword) { return productDAO.searchByBrand(keyword); }
    @Override
    public List<Product> searchByPrice(double min, double max) { return productDAO.searchByPrice(min, max); }
    @Override
    public List<Product> searchByStock(int min, int max) { return productDAO.searchByStock(min, max); }
}
















