package presentation;

import business.IAdminService;
import business.impl.AdminServiceImpl;
import model.Admin;
import util.InputHelper;

import java.util.Scanner;

public class AuthView {
    private final IAdminService adminService;
    public AuthView() {
        this.adminService = new AdminServiceImpl();
    }
    public boolean login() {
        System.out.println("\n---------- ĐĂNG NHẬP QUẢN TRỊ ----------");

        while (true) {
            System.out.print("Tài khoản: ");
            String username = new Scanner(System.in).nextLine().trim();
            System.out.print("Mật khẩu : ");
            String password = new Scanner(System.in).nextLine().trim();
            System.out.println("========================================");

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println(">> Lỗi: Tài khoản và mật khẩu không được để trống!");
                System.out.println("----------------------------------------");
                continue;
            }
            Admin admin = adminService.login(username, password);
            if (admin != null) {
                System.out.println(">> Đăng nhập thành công! Xin chào " + admin.getUsername());
                return true;
            } else {
                System.out.println(">> Lỗi: Sai tên đăng nhập hoặc mật khẩu.");
                boolean retry = InputHelper.confirm("Bạn có muốn thử lại không?");
                if (!retry) {
                    return false;
                }
                System.out.println("\n---------- ĐĂNG NHẬP LẠI ----------");
            }
        }
    }


}
