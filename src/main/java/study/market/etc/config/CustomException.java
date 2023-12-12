package study.market.etc.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.market.etc.enumType.ErrorCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
}
