package util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        boolean result = BCrypt.checkpw(plainPassword, hashedPassword);
        return result;
    }

        public static void main(String[] args) {
            // Mã hóa mật khẩu
            String rawPass = "123456";
            String hashPass1 = null;
            String hashPass2 = null;
            String hashPass3 = null;
            hashPass1 = BCrypt.hashpw(rawPass,BCrypt.gensalt(10));
            System.out.println("hash-pass 1 : "+hashPass1);
            hashPass2 = BCrypt.hashpw(rawPass,BCrypt.gensalt(10));
            System.out.println("hash-pass 2 : "+hashPass2);
            hashPass3 = BCrypt.hashpw(rawPass,BCrypt.gensalt(10));
            System.out.println("hash-pass 3 : "+hashPass3);

            // kiểm tra trùng
            boolean check = BCrypt.checkpw("rawPass",hashPass1);
            System.out.println(check);

            // Logic :
            // Lấy về hashpass
            // So sánh với pass nhập vào : BCrypt.checkpw()

        }
}
