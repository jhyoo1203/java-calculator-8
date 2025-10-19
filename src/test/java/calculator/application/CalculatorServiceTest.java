package calculator.application;

import static org.junit.jupiter.api.Assertions.*;

import calculator.application.dto.ParseResult;
import calculator.domain.Delimiters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {

    private TestInputParseService inputParseService;
    private TestDelimiterService delimiterService;
    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        inputParseService = new TestInputParseService();
        delimiterService = new TestDelimiterService();
        calculatorService = new CalculatorService(inputParseService, delimiterService);
    }

    @Nested
    @DisplayName("plus 메서드는")
    class Describe_plus {

        @Test
        @DisplayName("빈 입력이 주어지면 0을 반환한다")
        void it_returns_zero_when_empty_input() {
            // given
            String input = "";

            // when
            int result = calculatorService.plus(input);

            // then
            assertEquals(0, result);
            assertFalse(inputParseService.parseInputCalled);
            assertFalse(delimiterService.registerDelimitersCalled);
        }

        @Test
        @DisplayName("null 입력이 주어지면 0을 반환한다")
        void it_returns_zero_when_null_input() {
            // given
            String input = null;

            // when
            int result = calculatorService.plus(input);

            // then
            assertEquals(0, result);
        }

        @Test
        @DisplayName("기본 구분자로 숫자들의 합을 계산한다")
        void it_calculates_sum_with_default_delimiters() {
            // given
            String input = "1,2,3";
            inputParseService.setParseResult(new ParseResult("", "1,2,3"));
            delimiterService.setDelimiters(new Delimiters());

            // when
            int result = calculatorService.plus(input);

            // then
            assertEquals(6, result);
            assertTrue(inputParseService.parseInputCalled);
            assertTrue(delimiterService.registerDelimitersCalled);
        }

        @Test
        @DisplayName("사용자 정의 구분자로 숫자들의 합을 계산한다")
        void it_calculates_sum_with_custom_delimiter() {
            // given
            String input = "//;\\n1;2;3";
            Delimiters delimiters = new Delimiters();
            delimiters.registerCustom(";");

            inputParseService.setParseResult(new ParseResult(";", "1;2;3"));
            delimiterService.setDelimiters(delimiters);

            // when
            int result = calculatorService.plus(input);

            // then
            assertEquals(6, result);
            assertTrue(inputParseService.parseInputCalled);
            assertTrue(delimiterService.registerDelimitersCalled);
        }

        @Test
        @DisplayName("InputParseService의 parseInput 메서드를 호출한다")
        void it_calls_input_parse_service() {
            // given
            String input = "1:2:3";
            inputParseService.setParseResult(new ParseResult("", "1:2:3"));
            delimiterService.setDelimiters(new Delimiters());

            // when
            calculatorService.plus(input);

            // then
            assertTrue(inputParseService.parseInputCalled);
        }

        @Test
        @DisplayName("DelimiterService의 registerDelimiters 메서드를 호출한다")
        void it_calls_delimiter_service() {
            // given
            String input = "1,2,3";
            inputParseService.setParseResult(new ParseResult("", "1,2,3"));
            delimiterService.setDelimiters(new Delimiters());

            // when
            calculatorService.plus(input);

            // then
            assertTrue(delimiterService.registerDelimitersCalled);
        }

        @Test
        @DisplayName("서비스에서 예외가 발생하면 그대로 전파한다")
        void it_propagates_exception_from_services() {
            // given
            String input = "invalid";
            inputParseService.setShouldThrowException(true);

            // when & then
            assertThrows(IllegalArgumentException.class, () -> calculatorService.plus(input));
        }

        @Test
        @DisplayName("여러 구분자가 혼합된 입력의 합을 계산한다")
        void it_calculates_sum_with_mixed_delimiters() {
            // given
            String input = "1,2:3";
            inputParseService.setParseResult(new ParseResult("", "1,2:3"));
            delimiterService.setDelimiters(new Delimiters());

            // when
            int result = calculatorService.plus(input);

            // then
            assertEquals(6, result);
        }
    }

    static class TestInputParseService extends InputParseService {
        boolean parseInputCalled = false;
        boolean shouldThrowException = false;
        ParseResult parseResult;

        void setParseResult(ParseResult parseResult) {
            this.parseResult = parseResult;
        }

        void setShouldThrowException(boolean shouldThrowException) {
            this.shouldThrowException = shouldThrowException;
        }

        @Override
        public ParseResult parseInput(String input) {
            parseInputCalled = true;
            if (shouldThrowException) {
                throw new IllegalArgumentException("잘못된 입력");
            }
            return parseResult;
        }
    }

    static class TestDelimiterService extends DelimiterService {
        boolean registerDelimitersCalled = false;
        Delimiters delimiters;

        void setDelimiters(Delimiters delimiters) {
            this.delimiters = delimiters;
        }

        @Override
        public Delimiters registerDelimiters(ParseResult parseResult) {
            registerDelimitersCalled = true;
            return delimiters;
        }
    }
}
