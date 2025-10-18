package calculator.application.dto;

public record ParseResult(String delimiterHeader, String numberPart) {

    public static ParseResult of(String delimiterHeader, String numberPart) {
        return new ParseResult(delimiterHeader, numberPart);
    }

    public static ParseResult from(String numberPart) {
        return new ParseResult(null, numberPart);
    }

    public boolean hasCustomDelimiter() {
        return delimiterHeader != null;
    }
}
