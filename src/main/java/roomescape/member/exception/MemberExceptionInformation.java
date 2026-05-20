package roomescape.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import roomescape.common.exception.ErrorInformation;

@Getter
@AllArgsConstructor
public enum MemberExceptionInformation implements ErrorInformation {

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_001", "해당 회원을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
