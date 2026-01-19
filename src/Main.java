import presentation.AuthView;
import presentation.MainMenu;
import util.InputHelper;

public class Main {
    public static void main(String[] args) {
        AuthView authView = new AuthView();
        MainMenu mainMenu = new MainMenu();

        do {
            System.out.println("\n ========= HỆ THỐNG QUẢN LÝ CỬA HÀNG ==========");
            System.out.println("1. Đăng nhập Admin");
            System.out.println("2. Thoát");
            System.out.println("=============================================");

            int choice = InputHelper.getInt("Nhập lựa chọn: ");
            switch (choice) {
                case 1:
                    boolean logged = authView.login();
                    if (logged) {
                        mainMenu.display();
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println(">> Lựa chọn không hợp lệ!");
            }

        } while (true);


    }
}
