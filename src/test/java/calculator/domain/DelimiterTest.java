package calculator.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DelimiterTest {

    @Nested
    @DisplayName("from 메서드는")
    class Describe_from {

        @Test
        @DisplayName("유효한 구분자로 Delimiter 객체를 생성한다")
        void it_creates_delimiter_with_valid_value() {
            // given
            String validValue = ",";

            // when
            Delimiter delimiter = Delimiter.from(validValue);

            // then
            assertNotNull(delimiter);
            assertEquals(validValue, delimiter.getValue());
        }

        @Test
        @DisplayName("빈 문자열이 주어지면 예외를 발생시킨다")
        void it_throws_exception_when_empty_string() {
            // given
            String emptyValue = "";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> Delimiter.from(emptyValue));
        }

        @Test
        @DisplayName("null이 주어지면 예외를 발생시킨다")
        void it_throws_exception_when_null() {
            // given
            String nullValue = null;

            // when & then
            assertThrows(IllegalArgumentException.class, () -> Delimiter.from(nullValue));
        }

        @Test
        @DisplayName("등록 접두사가 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_contains_registration_prefix() {
            // given
            String valueWithPrefix = "//test";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> Delimiter.from(valueWithPrefix));
        }

        @Test
        @DisplayName("등록 접미사가 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_contains_registration_postfix() {
            // given
            String valueWithPostfix = "test\\n";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> Delimiter.from(valueWithPostfix));
        }
    }

    @Nested
    @DisplayName("getValue 메서드는")
    class Describe_getValue {

        @Test
        @DisplayName("저장된 구분자 값을 반환한다")
        void it_returns_stored_value() {
            // given
            String value = ":";
            Delimiter delimiter = Delimiter.from(value);

            // when
            String result = delimiter.getValue();

            // then
            assertEquals(value, result);
        }
    }
}
