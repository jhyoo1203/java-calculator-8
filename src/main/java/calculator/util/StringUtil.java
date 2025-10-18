package calculator.util;

public class StringUtil {

    private StringUtil() { }

    public static boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
