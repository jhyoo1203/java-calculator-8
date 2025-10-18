package calculator.config;

import calculator.application.CalculatorService;
import calculator.application.DelimiterService;
import calculator.application.InputParseService;
import calculator.presentation.CalculatorController;

public class AppConfig {

    private final InputParseService inputParseService = new InputParseService();
    private final DelimiterService delimiterService = new DelimiterService();
    private final CalculatorService calculatorService = new CalculatorService(inputParseService, delimiterService);
    private final CalculatorController calculatorController = new CalculatorController(calculatorService);

    public CalculatorController calculatorController() {
        return calculatorController;
    }
}
