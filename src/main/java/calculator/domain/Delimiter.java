package calculator.domain;

import calculator.util.StringUtil;

public class Delimiter {

    public static final String REGISTRATION_PREFIX = "//";
    public static final String REGISTRATION_POSTFIX = "\\n";

    private final String value;

    private Delimiter(String value) {
        validate(value);
        this.value = value;
    }

    public static Delimiter from(String value) {
        return new Delimiter(value);
    }

    private void validate(String value) {
        if (StringUtil.isEmpty(value)) {
            throw new IllegalArgumentException("구분자는 빈 값일 수 없습니다.");
        }

        if (value.contains(REGISTRATION_PREFIX) || value.contains(REGISTRATION_POSTFIX)) {
            throw new IllegalArgumentException("구분자에 사용할 수 없는 문자가 포함되어 있습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
