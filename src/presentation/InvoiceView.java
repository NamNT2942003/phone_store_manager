package presentation;

import business.ICustomerService;
import business.IInvoiceService;
import business.IProductService;
import business.impl.CustomerServiceImpl;
import business.impl.InvoiceServiceImpl;
import business.impl.ProductServiceImpl;
import model.Customer;
import model.Invoice;
import model.InvoiceDetail;
import model.Product;
import util.Formatter;
import util.InputHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceView {

    private final IInvoiceService invoiceService;
    private final IProductService productService;
    private final ICustomerService customerService;

    public InvoiceView() {
        this.invoiceService = new InvoiceServiceImpl();
        this.productService = new ProductServiceImpl();
        this.customerService = new CustomerServiceImpl();
    }

    public void displayInvoiceManagementMenu() {
        while (true) {
            System.out.println("\n========= QUẢN LÝ HÓA ĐƠN =========");
            System.out.println("1. Hiển thị danh sách hóa đơn");
            System.out.println("2. Thêm mới hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Quay lại menu chính");
            System.out.println("===================================");

            int choice = InputHelper.getInt("Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    displayList(invoiceService.getAll());
                    break;
                case 2:
                    createNewInvoice();
                    break;
                case 3:
                    displaySearchMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
    }

    private void displaySearchMenu() {
        while (true) {
            System.out.println("\n--> Menu tìm kiếm hóa đơn");
            System.out.println("1. Tìm theo tên khách hàng");
            System.out.println("2. Tìm theo ngày/tháng/năm (Ngày tạo)");
            System.out.println("3. Quay lại menu hóa đơn");

            int choice = InputHelper.getInt("Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    String name = InputHelper.getString("Nhập tên khách hàng: ");
                    displayList(invoiceService.searchByCustomer(name));
                    break;
                case 2:
                    try { String dateStr = InputHelper.getValidatedString(
                            "Nhập ngày (dd/MM/yyyy): ",
                            "^\\d{2}/\\d{2}/\\d{4}$",
                            "Định dạng ngày không đúng (vd: 25/12/2023)"
                    );
                        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        displayList(invoiceService.searchByDate(date));

                    }catch (Exception e){
                        System.out.println(">> Ngày không hợp lệ!");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
    }
    public void displayStatisticsMenu() {
        while (true) {
            System.out.println("\n========= THỐNG KÊ DOANH THU =========");
            System.out.println("1. Doanh thu theo ngày");
            System.out.println("2. Doanh thu theo tháng");
            System.out.println("3. Doanh thu theo năm");
            System.out.println("4. Quay lại menu chính");
            System.out.println("======================================");

            int choice = InputHelper.getInt("Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    int m = InputHelper.getPositiveInt("Nhập tháng: ");
                    int y = InputHelper.getPositiveInt("Nhập năm: ");
                    printRevenueTable("NGÀY", invoiceService.getRevenueByDay(m, y));
                    break;
                case 2:
                    int year = InputHelper.getPositiveInt("Nhập năm: ");
                    printRevenueTable("THÁNG", invoiceService.getRevenueByMonth(year));
                    break;
                case 3:
                    printRevenueTable("NĂM", invoiceService.getRevenueByYear());
                    break;
                case 4:
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
    }
    private void displayList(List<Invoice> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(">> Không tìm thấy hóa đơn nào!");
            return;
        }
        System.out.println("\n+-----+------------+----------------------+-----------------+");
        System.out.printf("| %-3s | %-10s | %-20s | %-15s |\n", "ID", "NGÀY TẠO", "KHÁCH HÀNG", "TỔNG TIỀN");
        System.out.println("+-----+------------+----------------------+-----------------+");

        for (Invoice i : list) {
            System.out.printf("| %-3d | %-10s | %-20s | %-15s |\n",
                    i.getId(),
                    Formatter.formatDate(i.getCreatedAt()),
                    (i.getCustomerName() == null ? "N/A" : i.getCustomerName()),
                    Formatter.formatMoney(i.getTotalAmount()));
        }
        System.out.println("+-----+------------+----------------------+-----------------+");
        InputHelper.pressEnterToContinue();
    }
    private void printRevenueTable(String label, Map<Integer, Double> data) {
        if (data.isEmpty()) {
            System.out.println(">> Không có dữ liệu doanh thu!");
            InputHelper.pressEnterToContinue();
            return;
        }
        double grandTotal = 0;
        System.out.println("\n------------------------------");
        System.out.printf("| %-10s | %-15s |\n", label, "DOANH THU");
        System.out.println("------------------------------");
        for (Map.Entry<Integer, Double> entry : data.entrySet()) {
            System.out.printf("| %-10d | %-15s |\n", entry.getKey(), Formatter.formatMoney(entry.getValue()));
            grandTotal += entry.getValue();
        }
        System.out.println("------------------------------");
        System.out.printf("| %-10s | %-15s |\n", "TỔNG CỘNG", Formatter.formatMoney(grandTotal));
        System.out.println("------------------------------");
        InputHelper.pressEnterToContinue();
    }


    private void createNewInvoice() {
        System.out.println("\n--- TẠO HÓA ĐƠN MỚI ---");
        //check khách hàng tồn tại

        int customerId = InputHelper.getInt("Nhập ID Khách hàng mua: ");
        Customer customer = customerService.getById(customerId);
        if (customer == null) {
            System.out.println(">> Lỗi: Khách hàng không tồn tại");
            return;
        }
        List<InvoiceDetail> cart = new ArrayList<>();
        boolean buying = true;
        while (buying) {

            int productId = InputHelper.getInt("Nhập ID Sản phẩm: ");
            Product p = productService.getById(productId);
            if (p == null) {
                System.out.println(">> Lỗi: Sản phẩm không tồn tại!");
                continue;
            }

            System.out.println(">> Sản phẩm: " + p.getName() + " | Giá: " + Formatter.formatMoney(p.getPrice()) + " | Tồn: " + p.getStock());
            if (p.getStock() <= 0) {
                System.out.println(">> HẾT HÀNG! Vui lòng chọn sản phẩm khác.");
                continue;
            }


            int qty = InputHelper.getPositiveInt("Nhập số lượng mua: ");
            if (qty > p.getStock()) {
                System.out.println(">> Lỗi: Số lượng mua vượt quá tồn kho (" + p.getStock() + ")");
                continue;
            }

            InvoiceDetail item = new InvoiceDetail();
            item.setProductId(productId);
            item.setQuantity(qty);
            item.setUnitPrice(p.getPrice());
            cart.add(item);

            System.out.println(">> Đã thêm vào giỏ hàng.");
            buying = InputHelper.confirm("Mua thêm sản phẩm khác không?");
        }

        if (cart.isEmpty()) {
            System.out.println(">> Giỏ hàng trống. Hủy tạo hóa đơn.");
            return;
        }
        double total = 0;
        System.out.println("\n--- XÁC NHẬN ĐƠN HÀNG ---");
        for (InvoiceDetail item : cart) {
            total += item.getSubTotal();
            System.out.printf("SP ID: %d | SL: %d | Giá: %s\n", item.getProductId(), item.getQuantity(), Formatter.formatMoney(item.getUnitPrice()));
        }
        System.out.println("=> TỔNG TIỀN: " + Formatter.formatMoney(total));

        if (InputHelper.confirm("Xác nhận thanh toán và lưu hóa đơn?")) {
            invoiceService.createOrder(customerId, cart);
        } else {
            System.out.println(">> Đã hủy đơn hàng.");
        }
        InputHelper.pressEnterToContinue();
    }










}
