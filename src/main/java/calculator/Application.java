package calculator;

import calculator.config.AppConfig;
import calculator.presentation.CalculatorController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        CalculatorController controller = config.calculatorController();

        controller.plus();
    }
}
