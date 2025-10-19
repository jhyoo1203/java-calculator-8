package calculator.application;

import static org.junit.jupiter.api.Assertions.*;

import calculator.application.dto.ParseResult;
import calculator.domain.Delimiters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DelimiterServiceTest {

    private DelimiterService delimiterService;

    @BeforeEach
    void setUp() {
        delimiterService = new DelimiterService();
    }

    @Nested
    @DisplayName("registerDelimiters 메서드는")
    class Describe_registerDelimiters {

        @Test
        @DisplayName("기본 구분자만 등록된 Delimiters를 반환한다")
        void it_returns_delimiters_with_default_only() {
            // given
            ParseResult parseResult = new ParseResult(null, "1,2:3");

            // when
            Delimiters delimiters = delimiterService.registerDelimiters(parseResult);

            // then
            assertNotNull(delimiters);
            assertFalse(parseResult.hasCustomDelimiter());
        }

        @Test
        @DisplayName("사용자 정의 구분자를 등록한 Delimiters를 반환한다")
        void it_returns_delimiters_with_custom_delimiter() {
            // given
            ParseResult parseResult = new ParseResult(";", "1;2;3");

            // when
            Delimiters delimiters = delimiterService.registerDelimiters(parseResult);

            // then
            assertNotNull(delimiters);
            assertTrue(parseResult.hasCustomDelimiter());
        }

        @Test
        @DisplayName("빈 구분자 헤더가 주어지면 기본 구분자만 등록한다")
        void it_registers_default_only_when_empty_header() {
            // given
            ParseResult parseResult = new ParseResult(null, "1,2,3");

            // when
            Delimiters delimiters = delimiterService.registerDelimiters(parseResult);

            // then
            assertNotNull(delimiters);
            assertFalse(parseResult.hasCustomDelimiter());
        }

        @Test
        @DisplayName("여러 문자로 구성된 사용자 정의 구분자를 등록한다")
        void it_registers_multi_character_custom_delimiter() {
            // given
            ParseResult parseResult = new ParseResult(";;", "1;;2;;3");

            // when
            Delimiters delimiters = delimiterService.registerDelimiters(parseResult);

            // then
            assertNotNull(delimiters);
            assertTrue(parseResult.hasCustomDelimiter());
        }

        @Test
        @DisplayName("특수 문자를 사용자 정의 구분자로 등록한다")
        void it_registers_special_character_as_custom_delimiter() {
            // given
            ParseResult parseResult = new ParseResult("*", "1*2*3");

            // when
            Delimiters delimiters = delimiterService.registerDelimiters(parseResult);

            // then
            assertNotNull(delimiters);
            assertTrue(parseResult.hasCustomDelimiter());
        }
    }
}
