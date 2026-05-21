package roomescape.store.domain;

import roomescape.management.domain.Management;
import roomescape.management.exception.ManagementException;

import java.util.List;
import java.util.Objects;

import static roomescape.management.exception.ManagementErrorInformation.NO_MANAGED_STORE;

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
            throw new ManagementException(NO_MANAGED_STORE);
        }
    }

}
