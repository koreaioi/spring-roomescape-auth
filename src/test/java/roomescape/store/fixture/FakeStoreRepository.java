package roomescape.store.fixture;

import roomescape.store.domain.Store;
import roomescape.store.repository.StoreRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeStoreRepository implements StoreRepository {

    private final Map<Long, Store> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public FakeStoreRepository() {
        saveDummy("강남점");
        saveDummy("판교점");
        saveDummy("신촌점");
    }

    @Override
    public Optional<Store> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Store> findAll() {
        return List.copyOf(store.values());
    }

    private void saveDummy(String name) {
        Long id = idGenerator.getAndIncrement();
        Store dummyStore = Store.load(id, name);
        store.put(id, dummyStore);
    }

}
