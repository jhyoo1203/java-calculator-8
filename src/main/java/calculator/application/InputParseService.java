package calculator.application;

import calculator.application.dto.ParseResult;
import calculator.domain.Delimiter;

public class InputParseService {

    /**
     * 입력 파싱을 위한 메서드
     * <li>커스텀 구분자가 존재하는지 확인</li>
     * <li>존재할 경우 헤더와 숫자 부분으로 분리</li>
     * <li>존재하지 않을 경우 숫자 부분만 반환</li>
     * @param input 사용자 입력 문자열
     * @return ParseResult - 파싱 결과 객체
     */
    public ParseResult parseInput(String input) {
        if (input.startsWith(Delimiter.REGISTRATION_PREFIX)) {
            int index = input.indexOf(Delimiter.REGISTRATION_POSTFIX);
            validateFormat(index);

            String header = input.substring(Delimiter.REGISTRATION_PREFIX.length(), index);
            String numbers = input.substring(index + Delimiter.REGISTRATION_POSTFIX.length());

            return ParseResult.of(header, numbers);
        }

        return ParseResult.from(input);
    }

    private void validateFormat(int index) {
        if (index == -1) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. '\\n'이 필요합니다.");
        }
    }
}
