package roomescape.store.domain;

import roomescape.management.domain.Management;

import java.util.List;
import java.util.Objects;

public record ManagedStore(
        List<Store> managedStores
) {

    public static ManagedStore from(List<Management> managements) {
        List<Store> stores = managements.stream()
                .map(Management::getStore)
                .toList();
        return new ManagedStore(stores);
    }

    public void validateCanManage(Store store) {
        boolean matched = managedStores.stream()
                .anyMatch(managedStore -> Objects.equals(managedStore.getId(), store.getId()));
        if (!matched) {
            throw new IllegalArgumentException("관리하는 매장이 아닙니다."); // TODO
        }
    }

}
