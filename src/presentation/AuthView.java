package view;

import util.InputHelper;

public class AuthView {

    // private AdminService adminService = new AdminService();

    public boolean login() {
        System.out.println("\n--- ĐĂNG NHẬP HỆ THỐNG ---");

        while (true) {
            String username = InputHelper.getString("Username: ");
            String password = InputHelper.getString("Password: ");

            // --- Tạm thời Hardcode để test luồng ---
            // Sau này bạn sẽ thay bằng: if (adminService.authenticate(username, password))
            if (username.equals("admin") && password.equals("123456")) {
                System.out.println(">> Đăng nhập thành công! Xin chào Admin.");
                return true;
            } else {
                System.out.println(">> Lỗi: Sai tên đăng nhập hoặc mật khẩu.");
                boolean retry = InputHelper.confirm("Bạn có muốn thử lại không?");
                if (!retry) {
                    System.out.println(">> Kết thúc chương trình.");
                    return false;
                }
            }
        }
    }
}
