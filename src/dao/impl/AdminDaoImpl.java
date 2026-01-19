package dao.impl;

import dao.IAdminDAO;
import model.Admin;
import util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class AdminDaoImpl implements IAdminDAO {
    @Override
    public Admin getUserByUserName(String userName){
            Admin admin = null;
            Connection conn = null;
            CallableStatement callSt = null;

            String sql = "select * from  func_get_admin_by_username(?)";
            try {
                conn = ConnectionDB.openConnection();
                callSt = conn.prepareCall(sql);
                callSt.setString(1,userName);
                ResultSet rs = callSt.executeQuery();

                if(rs.next()){
                    admin = new Admin();
                    admin.setUsername(rs.getString("out_username"));
                    admin.setPassword(rs.getString("out_password"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                ConnectionDB.closeConnection(conn,callSt);
            }
            return admin;

        }



}
