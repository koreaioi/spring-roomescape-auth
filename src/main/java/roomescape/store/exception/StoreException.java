package roomescape.store.exception;

import roomescape.common.exception.ErrorInformation;
import roomescape.common.exception.RoomEscapeException;

public class StoreException extends RoomEscapeException {
    public StoreException(ErrorInformation errorInformation) {
        super(errorInformation);
    }
}
