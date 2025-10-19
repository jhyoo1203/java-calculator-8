package calculator.application;

import static org.junit.jupiter.api.Assertions.*;

import calculator.application.dto.ParseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InputParseServiceTest {

    private InputParseService inputParseService;

    @BeforeEach
    void setUp() {
        inputParseService = new InputParseService();
    }

    @Nested
    @DisplayName("parseInput 메서드는")
    class Describe_parseInput {

        @Test
        @DisplayName("사용자 정의 구분자가 없는 경우 숫자만 포함된 ParseResult를 반환한다")
        void it_returns_parse_result_without_custom_delimiter() {
            // given
            String input = "1,2,3";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertNotNull(result);
            assertFalse(result.hasCustomDelimiter());
            assertEquals("1,2,3", result.numberPart());
        }

        @Test
        @DisplayName("사용자 정의 구분자가 있는 경우 헤더와 숫자를 분리한 ParseResult를 반환한다")
        void it_returns_parse_result_with_custom_delimiter() {
            // given
            String input = "//;\\n1;2;3";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertNotNull(result);
            assertTrue(result.hasCustomDelimiter());
            assertEquals(";", result.delimiterHeader());
            assertEquals("1;2;3", result.numberPart());
        }

        @Test
        @DisplayName("여러 문자로 구성된 사용자 정의 구분자를 파싱한다")
        void it_parses_multi_character_custom_delimiter() {
            // given
            String input = "//;;\\n1;;2;;3";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertTrue(result.hasCustomDelimiter());
            assertEquals(";;", result.delimiterHeader());
            assertEquals("1;;2;;3", result.numberPart());
        }

        @Test
        @DisplayName("특수 문자를 사용자 정의 구분자로 파싱한다")
        void it_parses_special_character_as_custom_delimiter() {
            // given
            String input = "//*\\n1*2*3";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertTrue(result.hasCustomDelimiter());
            assertEquals("*", result.delimiterHeader());
            assertEquals("1*2*3", result.numberPart());
        }

        @Test
        @DisplayName("잘못된 형식의 입력이 주어지면 예외를 발생시킨다")
        void it_throws_exception_when_invalid_format() {
            // given
            String input = "//;1;2;3";

            // when & then
            assertThrows(IllegalArgumentException.class, () -> inputParseService.parseInput(input));
        }

        @Test
        @DisplayName("구분자 등록 접두사만 있고 접미사가 없으면 예외를 발생시킨다")
        void it_throws_exception_when_missing_postfix() {
            // given
            String input = "//;";

            // when & then
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> inputParseService.parseInput(input)
            );
            assertEquals("잘못된 입력 형식입니다. '\\n'이 필요합니다.", exception.getMessage());
        }

        @Test
        @DisplayName("빈 문자열을 입력하면 빈 숫자 부분을 가진 ParseResult를 반환한다")
        void it_returns_parse_result_with_empty_numbers() {
            // given
            String input = "";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertNotNull(result);
            assertFalse(result.hasCustomDelimiter());
            assertEquals("", result.numberPart());
        }

        @Test
        @DisplayName("구분자 등록 후 숫자가 없으면 빈 숫자 부분을 반환한다")
        void it_returns_empty_numbers_after_delimiter_registration() {
            // given
            String input = "//;\\n";

            // when
            ParseResult result = inputParseService.parseInput(input);

            // then
            assertTrue(result.hasCustomDelimiter());
            assertEquals(";", result.delimiterHeader());
            assertEquals("", result.numberPart());
        }
    }
}
