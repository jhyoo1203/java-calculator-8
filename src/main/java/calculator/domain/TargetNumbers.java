package calculator.domain;

import java.util.Arrays;
import java.util.List;

public class TargetNumbers {

    private final List<Integer> values;

    private TargetNumbers(List<Integer> values) {
        this.values = values;
    }

    public static TargetNumbers of(String input, Delimiters delimiters) {
        String[] tokens = input.split(delimiters.toRegex());

        List<Integer> values = Arrays.stream(tokens)
                .filter(token -> !token.isBlank())
                .map(TargetNumbers::extractValue)
                .toList();

        return new TargetNumbers(values);
    }

    private static int extractValue(String token) {
        int value;

        try {
             value = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("숫자 형식이 올바르지 않습니다: %s", token));
        }

        if (value < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다.");
        }

        return value;
    }

    public int sum() {
        long sum = values.stream()
                .mapToLong(Integer::longValue)
                .sum();

        // 오버플로우 검증
        if (sum > Integer.MAX_VALUE) {
            // ArithmeticException 이 논리적으로 맞지만 요구사항에 따라 IAE throw
            throw new IllegalArgumentException("결과가 int 범위를 초과했습니다.");
        }

        return (int) sum;
    }
}
