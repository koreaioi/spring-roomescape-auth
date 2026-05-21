package roomescape.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.store.domain.Store;
import roomescape.store.repository.StoreRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> readStores() {
        return storeRepository.findAll();
    }

}
