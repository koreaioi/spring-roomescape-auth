package roomescape.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.common.auth.annotation.AuthGuard;
import roomescape.common.auth.annotation.LoginMember;
import roomescape.member.domain.Member;
import roomescape.reservation.controller.dto.request.ReservationCancelDto;
import roomescape.reservation.controller.dto.request.ReservationChangeScheduleDto;
import roomescape.reservation.controller.dto.request.ReservationSaveDto;
import roomescape.reservation.controller.dto.response.ReservationDetailDto;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

import java.util.List;

import static roomescape.member.domain.Role.MANAGER;
import static roomescape.member.domain.Role.MEMBER;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reservations")
    @AuthGuard(roles = {MEMBER, MANAGER})
    public ResponseEntity<ReservationDetailDto> create(
            @Validated @RequestBody ReservationSaveDto dto,
            @LoginMember Member member
    ) {
        Reservation reservation = reservationService.reserve(member.getName() , dto.toCommand());
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/my-reservations")
    @AuthGuard(roles = {MEMBER, MANAGER})
    public ResponseEntity<List<ReservationDetailDto>> getMyReservations(@LoginMember Member member) {
        List<ReservationDetailDto> responseData = reservationService.readAllByName(member.getName()).stream()
                .map(ReservationDetailDto::from)
                .toList();
        return ResponseEntity.ok(responseData);
    }

    @PatchMapping("/reservations/{id}/cancel")
    public ResponseEntity<ReservationDetailDto> cancel(@PathVariable Long id, @Validated @RequestBody ReservationCancelDto dto) {
        Reservation reservation = reservationService.cancel(id, dto.name());
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

    @PatchMapping("/reservations/{id}/schedule")
    public ResponseEntity<ReservationDetailDto> updateSchedule(
            @PathVariable Long id,
            @RequestParam String name,
            @Validated @RequestBody ReservationChangeScheduleDto dto
    ) {
        Reservation reservation = reservationService.changeSchedule(dto.toCommand(id, name));
        ReservationDetailDto responseData = ReservationDetailDto.from(reservation);
        return ResponseEntity.ok(responseData);
    }

}
