package util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    public static String formatDate(LocalDate date) {
        if (date == null) return "N/A";
        return date.format(DATE_FORMATTER);
    }
    public static String formatMoney(double amount) {
        return CURRENCY_FORMATTER.format(amount);
    }
}
