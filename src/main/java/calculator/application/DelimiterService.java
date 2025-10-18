package calculator.application;

import calculator.application.dto.ParseResult;
import calculator.domain.Delimiters;

public class DelimiterService {

    public Delimiters registerDelimiters(ParseResult parsed) {
        Delimiters delimiters = new Delimiters();

        if (parsed.hasCustomDelimiter()) {
            delimiters.registerCustom(parsed.delimiterHeader());
        }

        return delimiters;
    }
}
