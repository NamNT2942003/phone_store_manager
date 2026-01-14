package view;

import model.Product;
import service.ProductService;
import util.Formatter;
import util.InputHelper;

import java.util.List;

public class ProductView {

    private final ProductService productService;

    public ProductView() {
        this.productService = new ProductService();
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ĐIỆN THOẠI ---");
            System.out.println("1. Hiển thị danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm");
            System.out.println("0. Quay lại Menu chính");

            int choice = InputHelper.getInt("Chọn chức năng: ");

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addNewProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    searchProductMenu();
                    break;
                case 0:
                    System.out.println(">> Quay lại Menu chính.");
                    return;
                default:
                    System.out.println(">> Lỗi: Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }
    private void printList(List<Product> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(">> Không tìm thấy sản phẩm nào phù hợp!");
            return;
        }

        System.out.println("\n+-----+----------------------+-------------+-----------------+----------+");
        System.out.printf("| %-3s | %-20s | %-11s | %-15s | %-8s |\n", "ID", "TÊN SP", "HÃNG", "GIÁ BÁN", "TỒN KHO");
        System.out.println("+-----+----------------------+-------------+-----------------+----------+");

        for (Product p : list) {
            System.out.printf("| %-3d | %-20s | %-11s | %-15s | %-8d |\n",
                    p.getId(),
                    p.getName(),
                    p.getBrand(),
                    Formatter.formatMoney(p.getPrice()),
                    p.getStock());
        }
        System.out.println("+-----+----------------------+-------------+-----------------+----------+");
    }
    private void displayAllProducts() {
        List<Product> list = productService.getAll();
        printList(list);
        InputHelper.pressEnterToContinue();
    }

    private void addNewProduct() {
        System.out.println("\n--- THÊM SẢN PHẨM MỚI ---");
        Product p = new Product();
        p.inputData();
        productService.add(p);
        InputHelper.pressEnterToContinue();
    }

    private void updateProduct() {
        System.out.println("\n--- CẬP NHẬT SẢN PHẨM ---");
        int id;
        Product product = null;

        while (true) {
            id = InputHelper.getInt("Nhập ID sản phẩm cần sửa (0 để thoát): ");
            if (id == 0) return;

            product = productService.getById(id);
            if (product != null) {
                break;
            } else {
                System.out.println(">> Lỗi: ID không tồn tại. Vui lòng nhập lại!");
            }
        }
        boolean isModified = false;
        boolean keepEditing = true;

        while (keepEditing) {
            System.out.println("\n------------------------------------------------");
            System.out.println("THÔNG TIN ĐANG SỬA (Lưu trên RAM):");
            System.out.println(product.toString());
            System.out.println("------------------------------------------------");
            System.out.println("   1. Sửa Tên sản phẩm");
            System.out.println("   2. Sửa Hãng (Brand)");
            System.out.println("   3. Sửa Giá bán");
            System.out.println("   4. Sửa Tồn kho");
            System.out.println("   0. LƯU THAY ĐỔI VÀ THOÁT");
            System.out.println("   9. Hủy bỏ (Không lưu)");
            int choice = InputHelper.getInt("Chọn trường muốn sửa: ");
            switch (choice) {
                case 1:
                    String newName = InputHelper.getString("Nhập tên mới: ");
                    product.setName(newName);
                    isModified = true;
                    System.out.println(">> Đã cập nhật Tên (chưa lưu vào DB).");
                    break;
                case 2:
                    String newBrand = InputHelper.getString("Nhập hãng mới: ");
                    product.setBrand(newBrand);
                    isModified = true;
                    System.out.println(">> Đã cập nhật Hãng (chưa lưu vào DB).");
                    break;
                case 3:
                    double newPrice = InputHelper.getPositiveDouble("Nhập giá mới: ");
                    product.setPrice(newPrice);
                    isModified = true;
                    System.out.println(">> Đã cập nhật Giá (chưa lưu vào DB).");
                    break;
                case 4:
                    int newStock = InputHelper.getPositiveInt("Nhập số lượng tồn kho mới: ");
                    product.setStock(newStock);
                    isModified = true;
                    System.out.println(">> Đã cập nhật Tồn kho (chưa lưu vào DB).");
                    break;
                case 0:
                    if (isModified) {
                        productService.update(product);
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
    private void deleteProduct() {
        System.out.println("\n--- XÓA SẢN PHẨM ---");
        int id;
        Product product = null;

        while (true) {
            id = InputHelper.getInt("Nhập ID sản phẩm muốn xóa (0 để thoát): ");
            if (id == 0) return;

            product = productService.getById(id);
            if (product != null) {
                break;
            } else {
                System.out.println(">> Lỗi: ID không tồn tại!");
            }
        }
        System.out.println(">> Bạn đang chọn xóa: " + product.getName());
        boolean confirm = InputHelper.confirm("Bạn có chắc chắn muốn xóa vĩnh viễn? (Y/N)");
        if (confirm) {
            productService.delete(id);
        } else {
            System.out.println(">> Đã hủy thao tác xóa.");
        }
        InputHelper.pressEnterToContinue();
    }



    private void searchProductMenu() {
        while (true) {
            System.out.println("\n--- TÌM KIẾM SẢN PHẨM ---");
            System.out.println("1. Tìm theo Nhãn hàng (Brand)");
            System.out.println("2. Tìm theo Khoảng giá (Price Range)");
            System.out.println("3. Tìm theo Tồn kho (Stock Availability)");
            System.out.println("0. Quay lại");

            int choice = InputHelper.getInt("Chọn tiêu chí tìm kiếm: ");
            List<Product> result = null;

            switch (choice) {
                case 1:
                    String brandKeyword = InputHelper.getString("Nhập tên hãng cần tìm: ");
                    result = productService.searchByBrand(brandKeyword);
                    System.out.println(">> Kết quả tìm kiếm hãng '" + brandKeyword + "':");
                    printList(result);
                    break;
                case 2:
                    System.out.println(">> Nhập khoảng giá:");
                    double minPrice = InputHelper.getPositiveDouble(" - Giá thấp nhất: ");
                    double maxPrice = InputHelper.getPositiveDouble(" - Giá cao nhất: ");
                    result = productService.searchByPrice(minPrice, maxPrice);
                    System.out.println(">> Kết quả tìm kiếm giá từ " + Formatter.formatMoney(minPrice) + " đến " + Formatter.formatMoney(maxPrice) + ":");
                    printList(result);
                    break;
                case 3:
                    System.out.println(">> Nhập khoảng số lượng tồn kho:");
                    int minStock = InputHelper.getPositiveInt(" - Số lượng tối thiểu: ");
                    int maxStock = InputHelper.getPositiveInt(" - Số lượng tối đa: ");
                    result = productService.searchByStock(minStock, maxStock);
                    System.out.println(">> Kết quả tìm kiếm tồn kho từ " + minStock + " đến " + maxStock + ":");
                    printList(result);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }
            if (choice != 0) InputHelper.pressEnterToContinue();
        }
    }





}
