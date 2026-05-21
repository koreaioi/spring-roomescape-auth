package roomescape.management.exception;

import roomescape.common.exception.ErrorInformation;
import roomescape.common.exception.RoomEscapeException;

public class ManagementException extends RoomEscapeException {
    public ManagementException(ErrorInformation errorInformation) {
        super(errorInformation);
    }
}
