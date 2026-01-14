package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    //1. Phương thức mở kết nối --> Connection
    public static Connection openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //2. Đóng các kết nối
    public static void closeConnection(Connection conn, CallableStatement callSt) {
        if (conn != null) {
            try {
                conn.close();
            }catch(SQLException e) {
                throw  new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = openConnection();

            if (conn != null) {
                System.out.println("Kết nối CSDL thành công!");
            } else {
                System.out.println("Kết nối CSDL thất bại!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi kết nối CSDL");
            e.printStackTrace();
        } finally {
            // Đảm bảo luôn đóng kết nối
            closeConnection(conn, callSt);
        }
    }
}