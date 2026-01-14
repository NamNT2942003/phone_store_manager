package dao.impl;

import dao.ICustomerDAO;
import model.Customer;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {
    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_get_all_customers()";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_phone"),
                        rs.getString("out_email"),
                        rs.getString("out_address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }
    @Override
    public Customer getById(int id) {
        Customer c = null;
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "SELECT * FROM func_get_customer_by_id(?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                c = new Customer(
                        rs.getInt("out_id"),
                        rs.getString("out_name"),
                        rs.getString("out_phone"),
                        rs.getString("out_email"),
                        rs.getString("out_address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return c;
    }
    @Override
    public void add(Customer c) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_insert_customer(?, ?, ?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setString(1, c.getName());
            callSt.setString(2, c.getPhone());
            callSt.setString(3, c.getEmail());
            callSt.setString(4, c.getAddress());
            callSt.execute();
            System.out.println(">> Thêm khách hàng thành công!");
        } catch (Exception e) {
            System.out.println(">> Lỗi thêm khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
    @Override
    public void update(Customer c) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_update_customer(?, ?, ?, ?, ?)";

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, c.getId());
            callSt.setString(2, c.getName());
            callSt.setString(3, c.getPhone());
            callSt.setString(4, c.getEmail());
            callSt.setString(5, c.getAddress());
            callSt.execute();
            System.out.println(">> Cập nhật khách hàng thành công!");
        } catch (Exception e) {
            System.out.println(">> Lỗi cập nhật: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
    @Override
    public void delete(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        String sql = "call proc_delete_customer(?)";
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            callSt.setInt(1, id);
            callSt.execute();
            System.out.println(">> Xóa khách hàng thành công!");
        } catch (Exception e) {
            System.out.println(">> Lỗi xóa khách hàng: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }



}
