package calculator.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TargetNumbersTest {

    @Nested
    @DisplayName("of 메서드는")
    class Describe_of {

        @Test
        @DisplayName("구분자로 분리된 숫자들로 TargetNumbers 객체를 생성한다")
        void it_creates_target_numbers_with_valid_input() {
            // given
            String input = "1,2,3";
            Delimiters delimiters = new Delimiters();

            // when
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // then
            assertNotNull(targetNumbers);
            assertEquals(6, targetNumbers.sum());
        }

        @Test
        @DisplayName("공백 토큰을 제외하고 숫자를 추출한다")
        void it_filters_blank_tokens() {
            // given
            String input = "1,,2, ,3";
            Delimiters delimiters = new Delimiters();

            // when
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // then
            assertEquals(6, targetNumbers.sum());
        }

        @Test
        @DisplayName("사용자 정의 구분자로 숫자를 분리한다")
        void it_splits_by_custom_delimiter() {
            // given
            String input = "1;2;3";
            Delimiters delimiters = new Delimiters();
            delimiters.registerCustom(";");

            // when
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // then
            assertEquals(6, targetNumbers.sum());
        }

        @Test
        @DisplayName("숫자 형식이 아닌 값이 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_invalid_number_format() {
            // given
            String input = "1,a,3";
            Delimiters delimiters = new Delimiters();

            // when & then
            assertThrows(IllegalArgumentException.class, () -> TargetNumbers.of(input, delimiters));
        }

        @Test
        @DisplayName("음수가 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_negative_number() {
            // given
            String input = "1,-2,3";
            Delimiters delimiters = new Delimiters();

            // when & then
            assertThrows(IllegalArgumentException.class, () -> TargetNumbers.of(input, delimiters));
        }

        @Test
        @DisplayName("빈 입력으로 빈 TargetNumbers 객체를 생성한다")
        void it_creates_empty_target_numbers_when_empty_input() {
            // given
            String input = "";
            Delimiters delimiters = new Delimiters();

            // when
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // then
            assertEquals(0, targetNumbers.sum());
        }
    }

    @Nested
    @DisplayName("sum 메서드는")
    class Describe_sum {

        @Test
        @DisplayName("모든 숫자의 합을 반환한다")
        void it_returns_sum_of_all_numbers() {
            // given
            String input = "1,2,3,4,5";
            Delimiters delimiters = new Delimiters();
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // when
            int result = targetNumbers.sum();

            // then
            assertEquals(15, result);
        }

        @Test
        @DisplayName("빈 목록의 합은 0이다")
        void it_returns_zero_when_empty() {
            // given
            String input = "";
            Delimiters delimiters = new Delimiters();
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // when
            int result = targetNumbers.sum();

            // then
            assertEquals(0, result);
        }

        @Test
        @DisplayName("합이 int 범위를 초과하면 예외를 발생시킨다")
        void it_throws_exception_when_sum_exceeds_int_max() {
            // given
            String input = String.format("%d,%d", Integer.MAX_VALUE, 1);
            Delimiters delimiters = new Delimiters();
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // when & then
            assertThrows(IllegalArgumentException.class, targetNumbers::sum);
        }

        @Test
        @DisplayName("큰 숫자들의 합을 정확히 계산한다")
        void it_calculates_sum_of_large_numbers() {
            // given
            String input = "1000000,2000000,3000000";
            Delimiters delimiters = new Delimiters();
            TargetNumbers targetNumbers = TargetNumbers.of(input, delimiters);

            // when
            int result = targetNumbers.sum();

            // then
            assertEquals(6000000, result);
        }
    }
}
