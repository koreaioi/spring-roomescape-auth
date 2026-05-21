package roomescape.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.common.auth.annotation.AuthGuard;
import roomescape.common.auth.annotation.LoginMember;
import roomescape.common.auth.annotation.CurrentManagedStore;
import roomescape.store.domain.ManagedStore;
import roomescape.member.domain.Member;
import roomescape.reservation.controller.dto.request.ReservationChangeScheduleDto;
import roomescape.reservation.controller.dto.request.ReservationSaveDto;
import roomescape.reservation.controller.dto.response.ReservationDetailDto;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

import java.util.List;

import static roomescape.member.domain.Role.MANAGER;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReservationAdminController {

    private final ReservationService reservationService;

    @AuthGuard(roles = MANAGER)
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDetailDto>> getReservations() {
        List<ReservationDetailDto> responseData = reservationService.readAll().stream()
                .map(ReservationDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @AuthGuard(roles = MANAGER)
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDetailDto> createReservation(
            @Valid @RequestBody ReservationSaveDto dto,
            @LoginMember Member manager,
            @CurrentManagedStore ManagedStore managedStore
    ) {
        Reservation reservation = reservationService.reserveByManager(manager.getName(), dto.toCommand(), managedStore);
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

    @AuthGuard(roles = MANAGER)
    @PatchMapping("/reservations/{id}/cancel")
    public ResponseEntity<ReservationDetailDto> cancelReservation(
            @PathVariable Long id,
            @CurrentManagedStore ManagedStore managedStore
    ) {
        Reservation reservation = reservationService.cancelByManager(id, managedStore);
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

    @AuthGuard(roles = MANAGER)
    @PatchMapping("/reservations/{id}/schedule")
    public ResponseEntity<ReservationDetailDto> updateSchedule(
            @PathVariable Long id,
            @Validated @RequestBody ReservationChangeScheduleDto dto,
            @CurrentManagedStore ManagedStore managedStore
    ) {
        Reservation reservation = reservationService.changeScheduleByManager(dto.toCommand(id), managedStore);
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

}
