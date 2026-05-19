package roomescape.common.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import roomescape.common.exception.ErrorInformation;

@Getter
@AllArgsConstructor
public enum AuthExceptionInformation implements ErrorInformation {

    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_001", "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_002", "올바른 권한이 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
