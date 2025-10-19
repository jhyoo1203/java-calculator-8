package calculator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Nested
    @DisplayName("isEmpty 메서드는")
    class Describe_isEmpty {

        @Test
        @DisplayName("null일 때 참을 반환한다")
        void it_returns_true_with_null() {
            // given
            String str = null;

            // when
            boolean result = StringUtil.isEmpty(str);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("빈 문자열일 때 참을 반환한다")
        void it_returns_true_with_empty_string() {
            // given
            String str = "";

            // when
            boolean result = StringUtil.isEmpty(str);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("공백 문자열일 때 참을 반환한다")
        void it_returns_true_with_blank_string() {
            // given
            String str = "   ";

            // when
            boolean result = StringUtil.isEmpty(str);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("문자열이 있을 때 거짓을 반환한다")
        void it_returns_false_with_string() {
            // given
            String str = "abc";

            // when
            boolean result = StringUtil.isEmpty(str);

            // then
            assertFalse(result);
        }
    }
}
