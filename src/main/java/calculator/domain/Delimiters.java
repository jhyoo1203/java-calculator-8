package calculator.domain;

import calculator.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Delimiters {

    private final List<Delimiter> delimiters = new ArrayList<>();

    private static final String DEFAULT_COMMA = ",";
    private static final String DEFAULT_COLON = ":";
    private static final Pattern SPECIAL_CHARS_ONLY = Pattern.compile("^\\p{Punct}+$");

    public Delimiters() {
        // 기본 구분자 등록
        delimiters.add(Delimiter.from(DEFAULT_COMMA));
        delimiters.add(Delimiter.from(DEFAULT_COLON));
    }

    /**
     * 사용자 정의 구분자 등록
     * <li>특수문자만 허용</li>
     * <li>빈 값은 등록할 대상이 없다고 판단하여 예외 대신 early return</li>
     * @param rawHeader 구분자 문자열
     */
    public void registerCustom(String rawHeader) {
        if (StringUtil.isEmpty(rawHeader)) {
            return;
        }

        if (!SPECIAL_CHARS_ONLY.matcher(rawHeader).matches()) {
            throw new IllegalArgumentException(String.format("구분자는 특수문자만 허용됩니다: %s", rawHeader));
        }

        delimiters.add(Delimiter.from(rawHeader));
    }

    public String toRegex() {
        return delimiters.stream()
                .map(Delimiter::getValue)
                .map(Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElseThrow(() -> new IllegalArgumentException("구분자가 존재하지 않습니다."));
    }
}
