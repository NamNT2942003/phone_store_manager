package business.impl;

import business.IAdminService;
import dao.IAdminDAO;
import dao.impl.AdminDaoImpl;
import model.Admin;
import util.PasswordUtil;

public class AdminServiceImpl implements IAdminService {
    private final IAdminDAO adminDAO;
    public AdminServiceImpl() {
        this.adminDAO = new AdminDaoImpl();
    }
    @Override
    public Admin login(String username, String password) {
        Admin admin = adminDAO.getUserByUserName(username);
        if (admin == null) {
            return null;
        }
        if (PasswordUtil.checkPassword(password, admin.getPassword())) {
            return admin;
        }else  {
            return null;
        }
    }

}
