package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.date.domain.ReservationDate;
import roomescape.date.exception.ReservationDateException;
import roomescape.date.repository.ReservationDateRepository;
import roomescape.store.domain.ManagedStore;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.dto.ReservationChangeCommand;
import roomescape.reservation.service.dto.ReservationSaveCommand;
import roomescape.store.domain.Store;
import roomescape.store.exception.StoreException;
import roomescape.store.repository.StoreRepository;
import roomescape.theme.domain.Theme;
import roomescape.theme.exception.ThemeException;
import roomescape.theme.repository.ThemeRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.exception.ReservationTimeException;
import roomescape.time.repository.ReservationTimeRepository;

import java.util.List;

import static roomescape.date.exception.ReservationDateErrorInformation.DATE_NOT_FOUND;
import static roomescape.reservation.domain.ReservationStatus.CANCELED;
import static roomescape.reservation.exception.ReservaitonErrorInformation.RESERVATION_ALREADY_BOOKED;
import static roomescape.reservation.exception.ReservaitonErrorInformation.RESERVATION_NOT_FOUND;
import static roomescape.store.exception.StoreErrorInformation.STORE_NOT_FOUND;
import static roomescape.theme.exception.ThemeErrorInformation.THEME_NOT_FOUND;
import static roomescape.time.exception.ReservationTimeErrorInformation.TIME_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationDateRepository reservationDateRepository;
    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> readAllByName(String name) {
        return reservationRepository.findAllByNameOrderByDateAndTime(name);
    }

    @Transactional
    public Reservation reserve(String name, ReservationSaveCommand command) {
        ReservationTime reservationTime = getReservationTime(command.timeId());
        reservationTime.validateIsInactive();

        ReservationDate reservationDate = getReservationDate(command.dateId());
        reservationDate.validateIsInactive();

        Theme theme = getTheme(command.themeId());
        theme.validateIsInactive();

        Store store = getStore(command.storeId());

        validateNotAlreadyBookedByOthers(reservationDate.getId(), reservationTime.getId(), theme.getId());
        return reservationRepository.save(
                Reservation.create(name, reservationDate, reservationTime, theme, store)
        );
    }

    @Transactional
    public Reservation cancelByManager(Long id, ManagedStore managedStore) {
        Reservation reservation = getReservation(id);
        validateManagedStore(managedStore, reservation);

        reservation.updateStatus(CANCELED);
        reservationRepository.updateStatus(reservation);
        return reservation;
    }

    @Transactional
    public Reservation cancel(Long id, String requesterName) {
        Reservation reservation = getReservation(id);
        reservation.cancel(requesterName);
        reservationRepository.updateStatus(reservation);
        return reservation;
    }

    @Transactional
    public Reservation changeSchedule(ReservationChangeCommand command) {
        Reservation reservation = getReservation(command.id());
        ReservationTime newTime = getReservationTime(command.timeId());
        newTime.validateIsInactive();

        ReservationDate newDate = getReservationDate(command.dateId());
        newDate.validateIsInactive();

        validateNotAlreadyBookedByOthers(command.dateId(), command.timeId(), reservation.getTheme().getId());

        reservation.changeSchedule(command.requesterName(), newDate, newTime);
        reservationRepository.updateSchedule(reservation);
        return reservation;
    }

    @Transactional
    public Reservation changeScheduleByManager(ReservationChangeCommand command, ManagedStore managedStore) {
        Reservation reservation = getReservation(command.id());
        validateManagedStore(managedStore, reservation);

        ReservationTime newTime = getReservationTime(command.timeId());
        newTime.validateIsInactive();

        ReservationDate newDate = getReservationDate(command.dateId());
        newDate.validateIsInactive();

        validateNotAlreadyBookedByOthers(command.dateId(), command.timeId(), reservation.getTheme().getId());

        reservation.changeScheduleByManager(newDate, newTime);
        reservationRepository.updateSchedule(reservation);
        return reservation;
    }

    private ReservationTime getReservationTime(Long timeId) {
        return reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new ReservationTimeException(TIME_NOT_FOUND));
    }

    private ReservationDate getReservationDate(Long dateId) {
        return reservationDateRepository.findById(dateId)
                .orElseThrow(() -> new ReservationDateException(DATE_NOT_FOUND));
    }

    private Theme getTheme(Long themeId) {
        return themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeException(THEME_NOT_FOUND));
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException(RESERVATION_NOT_FOUND));
    }

    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(STORE_NOT_FOUND));
    }

    private void validateNotAlreadyBookedByOthers(Long dateId, Long timeId, Long themeId) {
        if (reservationRepository.existsByDateAndTimeAndThemeId(dateId, timeId, themeId)) {
            throw new ReservationException(RESERVATION_ALREADY_BOOKED);
        }
    }

    private void validateManagedStore(ManagedStore managedStore, Reservation reservation) {
        Store store = reservation.getStore();
        managedStore.validateCanManage(store);
    }

}
