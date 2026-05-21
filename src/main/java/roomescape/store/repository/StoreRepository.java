package roomescape.store.repository;

import roomescape.store.domain.Store;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {

    Optional<Store> findById(Long id);

    List<Store> findAll();

}
