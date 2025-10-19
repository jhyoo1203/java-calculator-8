package calculator.application.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ParseResultTest {

    @Nested
    @DisplayName("of 메서드는")
    class Describe_of {

        @Test
        @DisplayName("구분자 헤더와 숫자 부분을 포함한 ParseResult를 생성한다")
        void it_creates_parse_result_with_delimiter_and_numbers() {
            // when
            ParseResult result = ParseResult.of(";", "1;2;3");

            // then
            assertEquals(";", result.delimiterHeader());
            assertEquals("1;2;3", result.numberPart());
            assertTrue(result.hasCustomDelimiter());
        }

        @Test
        @DisplayName("구분자 헤더가 null인 경우 사용자 정의 구분자가 없는 것으로 판단한다")
        void it_treats_null_delimiter_as_no_custom_delimiter() {
            // when
            ParseResult result = ParseResult.of(null, "1,2,3");

            // then
            assertNull(result.delimiterHeader());
            assertEquals("1,2,3", result.numberPart());
            assertFalse(result.hasCustomDelimiter());
        }
    }

    @Nested
    @DisplayName("from 메서드는")
    class Describe_from {

        @Test
        @DisplayName("숫자 부분만으로 ParseResult를 생성한다")
        void it_creates_parse_result_with_numbers_only() {
            // when
            ParseResult result = ParseResult.from("1,2,3");

            // then
            assertNull(result.delimiterHeader());
            assertEquals("1,2,3", result.numberPart());
            assertFalse(result.hasCustomDelimiter());
        }

        @Test
        @DisplayName("빈 문자열로 ParseResult를 생성한다")
        void it_creates_parse_result_with_empty_string() {
            // when
            ParseResult result = ParseResult.from("");

            // then
            assertNull(result.delimiterHeader());
            assertEquals("", result.numberPart());
            assertFalse(result.hasCustomDelimiter());
        }
    }

    @Nested
    @DisplayName("hasCustomDelimiter 메서드는")
    class Describe_hasCustomDelimiter {

        @Test
        @DisplayName("구분자 헤더가 있으면 true를 반환한다")
        void it_returns_true_when_delimiter_header_exists() {
            // given
            ParseResult result = ParseResult.of(";", "1;2;3");

            // when
            boolean hasCustom = result.hasCustomDelimiter();

            // then
            assertTrue(hasCustom);
        }

        @Test
        @DisplayName("구분자 헤더가 null이면 false를 반환한다")
        void it_returns_false_when_delimiter_header_is_null() {
            // given
            ParseResult result = ParseResult.from("1,2,3");

            // when
            boolean hasCustom = result.hasCustomDelimiter();

            // then
            assertFalse(hasCustom);
        }

        @Test
        @DisplayName("빈 문자열 구분자 헤더는 사용자 정의 구분자로 인정한다")
        void it_returns_true_when_delimiter_header_is_empty_string() {
            // given
            ParseResult result = ParseResult.of("", "1,2,3");

            // when
            boolean hasCustom = result.hasCustomDelimiter();

            // then
            assertTrue(hasCustom);
        }
    }
}
