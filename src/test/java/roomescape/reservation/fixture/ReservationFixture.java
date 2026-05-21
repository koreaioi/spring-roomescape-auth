package roomescape.reservation.fixture;

import roomescape.date.domain.ReservationDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationStatus;
import roomescape.reservation.controller.dto.request.ReservationSaveDto;
import roomescape.reservation.service.dto.ReservationSaveCommand;
import roomescape.store.domain.Store;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.ReservationTime;

public class ReservationFixture {

    public static Reservation reservation(
            String name,
            ReservationDate date,
            ReservationTime time,
            Theme theme,
            Store store
    ) {
        return Reservation.create(name, date, time, theme, store);
    }

    public static Reservation canceledReservation(
            String name,
            ReservationDate date,
            ReservationTime time,
            Theme theme,
            Store store
    ) {
        Reservation reservation = Reservation.create(name, date, time, theme, store);
        reservation.updateStatus(ReservationStatus.CANCELED);
        return reservation;
    }

    public static ReservationSaveCommand toCommand(
            ReservationDate date,
            ReservationTime time,
            Theme theme,
            Store store
    ) {
        return new ReservationSaveCommand(date.getId(), time.getId(), theme.getId(), store.getId());
    }

    public static ReservationSaveCommand toCommand(
            ReservationDate date,
            Long timeId,
            Theme theme,
            Store store
    ) {
        return new ReservationSaveCommand(date.getId(), timeId, theme.getId(), store.getId());
    }

    public static ReservationSaveDto toCommand(
            Long dateId,
            ReservationTime time,
            Theme theme,
            Store store
    ) {
        return new ReservationSaveDto(dateId, time.getId(), theme.getId(), store.getId());
    }

    public static ReservationSaveCommand toCommand(
            ReservationDate date,
            ReservationTime time,
            Long themeId,
            Store store
    ) {
        return new ReservationSaveCommand(date.getId(), time.getId(), themeId, store.getId());
    }

}
