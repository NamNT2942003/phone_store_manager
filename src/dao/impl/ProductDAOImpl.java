package dao.impl;

import dao.IProductDAO;
import model.Product;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements IProductDAO {
    public void addProduct(Product p) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_insert_product(?, ?, ?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);

            callSt.setString(1, p.getName());
            callSt.setString(2, p.getBrand());
            callSt.setDouble(3, p.getPrice());
            callSt.setInt(4, p.getStock());

            callSt.execute();
            System.out.println(">> Thêm sản phẩm thành công!");

        } catch (Exception e) {
            System.err.println(">> Lỗi thêm sản phẩm: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
    public void updateProduct(Product p) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_update_product(?, ?, ?, ?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);

            callSt.setInt(1, p.getId());
            callSt.setString(2, p.getName());
            callSt.setString(3, p.getBrand());
            callSt.setDouble(4, p.getPrice());
            callSt.setInt(5, p.getStock());

            callSt.execute();
            System.out.println(">> Cập nhật thành công!");

        } catch (Exception e) {
            System.err.println(">> Lỗi cập nhật: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
    public void deleteProduct(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_delete_product(?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);

            callSt.setInt(1, id);

            callSt.execute();
            System.out.println(">> Đã xóa sản phẩm ID: " + id);

        } catch (Exception e) {
            System.err.println(">> Lỗi xóa sản phẩm: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_get_all_products()";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_brand"),
                        rs.getDouble("out_price"),
                        rs.getInt("out_stock")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }
    public Product getProductById(int id) {
        Product p = null;
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_get_product_by_id(?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                p = new Product(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_brand"),
                        rs.getDouble("out_price"),
                        rs.getInt("out_stock")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return p;
    }


    public List<Product> searchByBrand(String keyword) {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_search_product_by_brand(?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setString(1, keyword);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_brand"),
                        rs.getDouble("out_price"),
                        rs.getInt("out_stock")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }
    public List<Product> searchByPrice(double min, double max) {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_search_product_by_price(?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setDouble(1, min);
            callSt.setDouble(2, max);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_brand"),
                        rs.getDouble("out_price"),
                        rs.getInt("out_stock")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }


    public List<Product> searchByStock(int min, int max) {
        List<Product> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_search_product_by_stock(?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, min);
            callSt.setInt(2, max);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_brand"),
                        rs.getDouble("out_price"),
                        rs.getInt("out_stock")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }







}

