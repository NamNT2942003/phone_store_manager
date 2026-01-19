package util;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(">> Lỗi: Không được để trống. Vui lòng nhập lại!");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    public static int getPositiveInt(String message) {
        while (true) {
            int result = getInt(message);
            if (result >= 0) {
                return result;
            }
            System.out.println(">> Lỗi: Vui lòng nhập số dương (>= 0)!");
        }
    }

    public static double getDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println(">> Lỗi: Không được để trống. Vui lòng nhập lại!");
                    continue;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Lỗi: Vui lòng nhập số thực hợp lệ!");
            }
        }
    }

    public static double getPositiveDouble(String message) {
        while (true) {
            double result = getDouble(message);
            if (result >= 0) {
                return result;
            }
            System.out.println(">> Lỗi: Vui lòng nhập số dương (>= 0)!");
        }
    }

    public static String getString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(">> Lỗi: Nội dung không được để trống!");
        }
    }

    public static boolean confirm(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            System.out.println(">> Lỗi: Vui lòng chỉ nhập 'Y' hoặc 'N'!");
        }
    }

    public static void pressEnterToContinue() {
        System.out.println("Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }
    public static String getValidatedString(String message, String regex, String errorMsg) {
        while (true) {
            String input = getString(message);
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(">> Lỗi: " + errorMsg);
        }
    }
    public static String getPhoneNumber(String message) {
        return getValidatedString(message, "^0\\d{9}$", "Số điện thoại không hợp lệ (Phải có 10 số, bắt đầu là 0).");
    }



}
