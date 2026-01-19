package presentation;

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
            System.out.println("\n========= MENU CHÍNH =========");
            System.out.println("1. Quản lý sản phẩm điện thoại");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý hóa đơn");
            System.out.println("4. Thống kê doanh thu");
            System.out.println("5. Đăng xuất");
            System.out.println("==============================");

            int choice = InputHelper.getInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1: productView.displayMenu(); break;
                case 2: customerView.displayMenu(); break;
                case 3: invoiceView.displayInvoiceManagementMenu(); break;
                case 4: invoiceView.displayStatisticsMenu(); break;
                case 5:
                    System.out.println("Đăng xuất thành công!");
                    return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
