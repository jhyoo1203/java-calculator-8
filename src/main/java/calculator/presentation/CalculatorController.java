package calculator.presentation;

import calculator.application.CalculatorService;
import camp.nextstep.edu.missionutils.Console;

public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public void plus() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String cmd = Console.readLine();

        int result = calculatorService.plus(cmd);

        System.out.printf("결과 : %d%n\n", result);
    }
}
