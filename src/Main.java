import presentation.MainMenu;

public class Main {
    public static void main(String[] args) {
        // 1. Khởi tạo màn hình đăng nhập
        // AuthView authView = new AuthView();

        // 2. Thực hiện đăng nhập
        boolean isLoggedIn = true;

        if (isLoggedIn) {
            // 3. Nếu đăng nhập thành công, hiển thị Menu Chính
            MainMenu mainMenu = new MainMenu();
            mainMenu.display();
        } else {
            // Nếu chọn không thử lại khi sai pass
            System.exit(0);
        }
    }
}
