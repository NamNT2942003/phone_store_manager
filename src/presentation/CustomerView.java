package view;

import util.InputHelper;

public class CustomerView {

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Thêm mới khách hàng");
            System.out.println("3. Cập nhật khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("0. Quay lại Menu chính");

            int choice = InputHelper.getInt("Chọn chức năng: ");

            switch (choice) {
                case 1:
                    System.out.println(">> Đang phát triển: Hiển thị danh sách KH...");
                    break;
                case 2:
                    System.out.println(">> Đang phát triển: Thêm mới KH...");
                    break;
                case 3:
                    System.out.println(">> Đang phát triển: Cập nhật KH...");
                    break;
                case 4:
                    System.out.println(">> Đang phát triển: Xóa KH...");
                    break;
                case 0:
                    return;
                default:
                    System.out.println(">> Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }
}