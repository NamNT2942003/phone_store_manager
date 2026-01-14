package dao.impl;

import dao.IInvoiceDAO;
import model.Invoice;
import model.InvoiceDetail;
import util.ConnectionDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceDAOImpl implements IInvoiceDAO {

    @Override
    public boolean createInvoice(Invoice invoice, List<InvoiceDetail> details) {
        Connection conn = null;
        CallableStatement callHeader = null;
        CallableStatement callDetail = null;

        try {
            conn = ConnectionDB.openConnection();
            conn.setAutoCommit(false);

            String sqlHeader = "{? = call func_create_invoice_header(?, ?)}";
            callHeader = conn.prepareCall(sqlHeader);
            callHeader.registerOutParameter(1, Types.INTEGER); // Giá trị trả về (ID)
            callHeader.setInt(2, invoice.getCustomerId());
            callHeader.setDouble(3, invoice.getTotalAmount());
            callHeader.execute();

            int newInvoiceId = callHeader.getInt(1);
            String sqlDetail = "CALL proc_add_invoice_detail(?, ?, ?, ?)";
            callDetail = conn.prepareCall(sqlDetail);

            for (InvoiceDetail detail : details) {
                callDetail.setInt(1, newInvoiceId);
                callDetail.setInt(2, detail.getProductId());
                callDetail.setInt(3, detail.getQuantity());
                callDetail.setDouble(4, detail.getUnitPrice());
                callDetail.addBatch();
            }
            callDetail.executeBatch();

            conn.commit();
            System.out.println(">> Tạo hóa đơn thành công! Mã HĐ: " + newInvoiceId);
            return true;

        } catch (Exception e) {
            // 5. Nếu có lỗi -> Rollback (Hoàn tác toàn bộ)
            try {
                if (conn != null) conn.rollback();
                System.out.println(">> Lỗi Transaction (Đã hoàn tác): " + e.getMessage());
            } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // Trả lại trạng thái mặc định
                if (callHeader != null) callHeader.close();
                if (callDetail != null) callDetail.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    @Override
    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("SELECT * FROM func_get_all_invoices()");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("out_id"));
                inv.setCustomerName(rs.getString("out_customer_name"));
                inv.setCreatedAt(rs.getObject("out_created_at", LocalDateTime.class).toLocalDate());
                inv.setTotalAmount(rs.getDouble("out_total_amount"));
                list.add(inv);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { ConnectionDB.closeConnection(conn, callSt); }
        return list;
    }

    @Override
    public List<Invoice> searchByCustomerName(String keyword) {
        List<Invoice> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection(); // Hoặc DBUtil.openConnection() nếu bạn đã đổi tên
            callSt = conn.prepareCall("SELECT * FROM func_search_invoice_by_customer(?)");
            callSt.setString(1, keyword);

            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("out_id"));
                inv.setCustomerName(rs.getString("out_customer_name"));
                inv.setCreatedAt(rs.getObject("out_created_at", LocalDateTime.class).toLocalDate());
                inv.setTotalAmount(rs.getDouble("out_total_amount"));
                list.add(inv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return list;
    }

    @Override
    public List<Invoice> searchByDate(LocalDate date) {
        List<Invoice> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement callSt = null;
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("SELECT * FROM func_search_invoice_by_date(?)");
            callSt.setDate(1, sqlDate);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("out_id"));
                inv.setCustomerName(rs.getString("out_customer_name"));
                inv.setCreatedAt(rs.getObject("out_created_at", LocalDateTime.class).toLocalDate());
                inv.setTotalAmount(rs.getDouble("out_total_amount"));
                list.add(inv);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { ConnectionDB.closeConnection(conn, callSt); }
        return list;
    }
    // Hàm helper để map kết quả thống kê
    private Map<Integer, Double> getRevenueData(String sql, Object... params) {
        Map<Integer, Double> data = new java.util.TreeMap<>();
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall(sql);
            for (int i = 0; i < params.length; i++) {
                callSt.setObject(i + 1, params[i]);
            }
            ResultSet rs = callSt.executeQuery();
            while(rs.next()) {
                data.put(rs.getInt(1), rs.getDouble(2));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { ConnectionDB.closeConnection(conn, callSt); }
        return data;
    }
    @Override
    public Map<Integer, Double> getRevenueByDay(int month, int year) {
        return getRevenueData("SELECT * FROM func_revenue_by_day(?, ?)", month, year);
    }

    @Override
    public Map<Integer, Double> getRevenueByMonth(int year) {
        return getRevenueData("SELECT * FROM func_revenue_by_month(?)", year);
    }

    @Override
    public Map<Integer, Double> getRevenueByYear() {
        return getRevenueData("SELECT * FROM func_revenue_by_year()");
    }
}
