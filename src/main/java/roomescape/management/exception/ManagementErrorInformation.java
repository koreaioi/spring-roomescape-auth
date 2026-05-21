package roomescape.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import roomescape.common.exception.ErrorInformation;

@Getter
@AllArgsConstructor
public enum ManagementErrorInformation implements ErrorInformation {

    NO_MANAGEMENT_STORE(HttpStatus.BAD_REQUEST, "MANAGEMENT_001", "관리하는 매장이 없습니다."),
    NO_MANAGED_STORE(HttpStatus.BAD_REQUEST, "MANAGEMENT_002", "관리하는 매장이 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
