package presentation;

import business.ICustomerService;
import business.impl.CustomerServiceImpl;
import model.Customer;
import util.InputHelper;

import java.util.List;

public class CustomerView {
    private final ICustomerService customerService;

    public CustomerView() {
        this.customerService = new CustomerServiceImpl();
    }

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
                    displayAllCustomers();
                    break;
                case 2:
                    addNewCustomer();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 0:
                    System.out.println(">> Quay lại Menu chính.");
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
    }
    // --- 1. Hiển thị danh sách ---
    private void displayAllCustomers() {
        List<Customer> list = customerService.getAll();
        if (list.isEmpty()) {
            System.out.println(">> Danh sách khách hàng đang trống!");
            return;
        }

        System.out.println("\n+-----+----------------------+--------------+----------------------+----------------------+");
        System.out.printf("| %-3s | %-20s | %-12s | %-20s | %-20s |\n", "ID", "HỌ TÊN", "SĐT", "EMAIL", "ĐỊA CHỈ");
        System.out.println("+-----+----------------------+--------------+----------------------+----------------------+");
        for (Customer c : list) {
            System.out.printf("| %-3d | %-20s | %-12s | %-20s | %-20s |\n",
                    c.getId(), c.getName(), c.getPhone(),
                    (c.getEmail() == null ? "N/A" : c.getEmail()),
                    c.getAddress());
        }
        System.out.println("+-----+----------------------+--------------+----------------------+----------------------+");
        InputHelper.pressEnterToContinue();
    }
    // --- 2. Thêm khách hàng ---
    private void addNewCustomer() {
        System.out.println("\n--- THÊM KHÁCH HÀNG MỚI ---");
        Customer c = new Customer();
        c.inputData(); // Hàm này đã có sẵn trong Model Customer
        customerService.add(c);
        InputHelper.pressEnterToContinue();
    }
    // --- 3. Cập nhật thông tin
    private void updateCustomer() {
        System.out.println("\n--- CẬP NHẬT THÔNG TIN KHÁCH HÀNG ---");
        int id;
        Customer customer = null;

        while (true) {
            id = InputHelper.getInt("Nhập ID khách hàng cần sửa (0 để thoát): ");
            if (id == 0) return;

            customer = customerService.getById(id);
            if (customer != null) break;
            System.out.println(">> Lỗi: ID không tồn tại!");
        }

        boolean isModified = false;
        boolean keepEditing = true;

        while (keepEditing) {
            System.out.println("\n--- THÔNG TIN HIỆN TẠI ---");
            System.out.println(customer.toString());
            System.out.println("--------------------------");
            System.out.println("1. Sửa Tên");
            System.out.println("2. Sửa Số điện thoại");
            System.out.println("3. Sửa Email");
            System.out.println("4. Sửa Địa chỉ");
            System.out.println("0. LƯU VÀ THOÁT");
            System.out.println("9. Hủy bỏ");

            int choice = InputHelper.getInt("Chọn trường muốn sửa: ");
            switch (choice) {
                case 1:
                    customer.setName(InputHelper.getString("Nhập tên mới: "));
                    isModified = true;
                    break;
                case 2:
                    customer.setPhone(InputHelper.getPhoneNumber("Nhập SĐT mới: "));
                    isModified = true;
                    break;
                case 3:
                    customer.setEmail(InputHelper.getString("Nhập Email mới: "));
                    isModified = true;
                    break;
                case 4:
                    customer.setAddress(InputHelper.getString("Nhập địa chỉ mới: "));
                    isModified = true;
                    break;
                case 0:
                    if (isModified) {
                        customerService.update(customer);
                    } else {
                        System.out.println(">> Bạn chưa thay đổi gì cả.");
                    }
                    keepEditing = false;
                    break;
                case 9:
                    System.out.println(">> Đã hủy bỏ thao tác.");
                    keepEditing = false;
                    break;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
        }
        InputHelper.pressEnterToContinue();
    }

    // --- 4. Xóa khách hàng ---
    private void deleteCustomer() {
        System.out.println("\n--- XÓA KHÁCH HÀNG ---");
        int id;
        Customer customer = null;

        while (true) {
            id = InputHelper.getInt("Nhập ID khách hàng muốn xóa (0 để thoát): ");
            if (id == 0) return;

            customer = customerService.getById(id);
            if (customer != null) break;
            System.out.println(">> Lỗi: ID không tồn tại!");
        }

        System.out.println(">> Bạn đang chọn xóa: " + customer.getName());
        if (InputHelper.confirm("Bạn có chắc chắn muốn xóa không?")) {
            customerService.delete(id);
        } else {
            System.out.println(">> Đã hủy thao tác xóa.");
        }
        InputHelper.pressEnterToContinue();
    }












}