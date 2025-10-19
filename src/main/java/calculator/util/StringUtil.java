package calculator.util;

public final class StringUtil {

    private StringUtil() { }

    public static boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
