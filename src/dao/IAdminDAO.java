package dao;

import model.Admin;

public interface IAdminDAO {
    Admin getUserByUserName(String userName);
}
