package roomescape.store.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import roomescape.common.exception.ErrorInformation;

@Getter
@AllArgsConstructor
public enum StoreErrorInformation implements ErrorInformation {

    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "STORE_001", "해당 매장을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
