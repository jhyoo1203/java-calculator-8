package calculator.application;

import calculator.application.dto.ParseResult;
import calculator.domain.Delimiters;
import calculator.domain.TargetNumbers;
import calculator.util.StringUtil;

public class CalculatorService {

    private final InputParseService inputParseService;
    private final DelimiterService delimiterService;

    public CalculatorService(InputParseService inputParseService,
                             DelimiterService delimiterService
    ) {
        this.inputParseService = inputParseService;
        this.delimiterService = delimiterService;
    }

    public int plus(String input) {
        // 입력이 비어있을 경우 0 리턴
        if (StringUtil.isEmpty(input)) {
            return 0;
        }

        // 1. 입력 파싱
        ParseResult parsed = inputParseService.parseInput(input);
        // 2. 구분자 등록
        Delimiters delimiters = delimiterService.registerDelimiters(parsed);
        // 3. 숫자 추출
        TargetNumbers targetNumbers = TargetNumbers.of(parsed.numberPart(), delimiters);

        return targetNumbers.sum();
    }

}
