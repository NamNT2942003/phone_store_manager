package view;

import util.InputHelper;

public class MainMenu {

    private final ProductView productView;
    private final CustomerView customerView;
    private final InvoiceView invoiceView;

    public MainMenu() {
        this.productView = new ProductView();
        this.customerView = new CustomerView();
        this.invoiceView = new InvoiceView();
    }

    public void display() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   HỆ THỐNG QUẢN LÝ CỬA HÀNG ĐIỆN THOẠI");
            System.out.println("========================================");
            System.out.println("1. Quản lý Điện thoại (Sản phẩm)");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Mua bán & Hóa đơn");
            System.out.println("0. Thoát chương trình");
            System.out.println("========================================");

            int choice = InputHelper.getInt("Mời bạn chọn chức năng (0-3): ");

            switch (choice) {
                case 1:
                    productView.displayMenu();
                    break;
                case 2:
                    customerView.displayMenu();
                    break;
                case 3:
                    invoiceView.displayMenu();
                    break;
                case 0:
                    System.out.println("Đang thoát chương trình... Hẹn gặp lại!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(">> Lỗi: Lựa chọn không hợp lệ. Vui lòng nhập từ 0 đến 3.");
            }
        }
    }
}
