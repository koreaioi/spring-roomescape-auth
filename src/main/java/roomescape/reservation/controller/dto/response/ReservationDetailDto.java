package roomescape.reservation.controller.dto.response;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetailDto(
        Long id,
        String name,
        LocalDate date,
        LocalTime time,
        Long themeId,
        String themeName,
        String themeThumbnailUrl,
        Long storeId,
        String storeName,
        ReservationStatus status
) {

    public static ReservationDetailDto from(Reservation reservation) {
        return new ReservationDetailDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getDate(),
                reservation.getTime().getStartAt(),
                reservation.getTheme().getId(),
                reservation.getTheme().getName(),
                reservation.getTheme().getThumbnailUrl(),
                reservation.getStore().getId(),
                reservation.getStore().getName(),
                reservation.getStatus()
        );
    }

}
