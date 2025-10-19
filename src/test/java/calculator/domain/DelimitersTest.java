package calculator.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DelimitersTest {

    @Nested
    @DisplayName("생성자는")
    class Describe_constructor {

        @Test
        @DisplayName("기본 구분자(쉼표, 콜론)를 등록한다")
        void it_registers_default_delimiters() {
            // given & when
            Delimiters delimiters = new Delimiters();

            // then
            String regex = delimiters.toRegex();
            assertTrue(regex.contains(","));
            assertTrue(regex.contains(":"));
        }
    }

    @Nested
    @DisplayName("registerCustom 메서드는")
    class Describe_registerCustom {

        @Test
        @DisplayName("특수문자로 이루어진 구분자를 등록한다")
        void it_registers_custom_delimiter_with_special_chars() {
            // given
            Delimiters delimiters = new Delimiters();
            String customDelimiter = ";";

            // when
            delimiters.registerCustom(customDelimiter);

            // then
            String regex = delimiters.toRegex();
            assertTrue(regex.contains(";"));
        }

        @Test
        @DisplayName("빈 문자열이 주어지면 등록하지 않는다")
        void it_does_not_register_when_empty_string() {
            // given
            Delimiters delimiters = new Delimiters();
            String emptyValue = "";

            // when
            delimiters.registerCustom(emptyValue);

            // then
            String regex = delimiters.toRegex();
            assertFalse(regex.isEmpty());
        }

        @Test
        @DisplayName("null이 주어지면 등록하지 않는다")
        void it_does_not_register_when_null() {
            // given
            Delimiters delimiters = new Delimiters();

            // when
            delimiters.registerCustom(null);

            // then
            String regex = delimiters.toRegex();
            assertFalse(regex.isEmpty());
        }

        @Test
        @DisplayName("특수문자가 아닌 문자가 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_contains_non_special_chars() {
            // given
            Delimiters delimiters = new Delimiters();
            String invalidDelimiter = "a";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> delimiters.registerCustom(invalidDelimiter));
        }

        @Test
        @DisplayName("숫자가 포함되면 예외를 발생시킨다")
        void it_throws_exception_when_contains_number() {
            // given
            Delimiters delimiters = new Delimiters();
            String invalidDelimiter = "1";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> delimiters.registerCustom(invalidDelimiter));
        }

        @Test
        @DisplayName("여러 특수문자를 구분자로 등록한다")
        void it_registers_multiple_special_chars() {
            // given
            Delimiters delimiters = new Delimiters();
            String customDelimiter = "!@#";

            // when
            delimiters.registerCustom(customDelimiter);

            // then
            String regex = delimiters.toRegex();
            assertTrue(regex.contains("!@#"));
        }
    }

    @Nested
    @DisplayName("toRegex 메서드는")
    class Describe_toRegex {

        @Test
        @DisplayName("등록된 구분자들을 정규식 패턴으로 변환한다")
        void it_converts_delimiters_to_regex_pattern() {
            // given
            Delimiters delimiters = new Delimiters();

            // when
            String regex = delimiters.toRegex();

            // then
            assertNotNull(regex);
            assertTrue(regex.contains("|"));
        }

        @Test
        @DisplayName("사용자 정의 구분자가 포함된 정규식을 생성한다")
        void it_creates_regex_with_custom_delimiter() {
            // given
            Delimiters delimiters = new Delimiters();
            delimiters.registerCustom(";");

            // when
            String regex = delimiters.toRegex();

            // then
            assertTrue(regex.contains(";"));
        }

        @Test
        @DisplayName("특수문자를 이스케이프 처리한다")
        void it_escapes_special_characters() {
            // given
            Delimiters delimiters = new Delimiters();

            // when
            String regex = delimiters.toRegex();

            // then
            assertTrue(regex.contains("\\Q"));
        }
    }
}
