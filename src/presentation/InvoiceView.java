package view;

import util.InputHelper;

public class InvoiceView {

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ MUA BÁN & DOANH THU ---");
            System.out.println("1. Tạo đơn hàng mới (Mua bán)");
            System.out.println("2. Xem danh sách hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Thống kê doanh thu (Ngày/Tháng/Năm)");
            System.out.println("0. Quay về menu chính");

            int choice = InputHelper.getInt("Chọn chức năng: ");

            switch (choice) {
                case 1:
                    System.out.println(">> Chức năng bán hàng đang phát triển...");
                    break;
                case 2:
                    System.out.println(">> Chức năng xem hóa đơn đang phát triển...");
                    break;
                case 3:
                    System.out.println(">> Chức năng tìm kiếm đang phát triển...");
                    break;
                case 4:
                    System.out.println(">> Chức năng thống kê đang phát triển...");
                    break;
                case 0:
                    return; // Quay về MainMenu
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
    }
}
