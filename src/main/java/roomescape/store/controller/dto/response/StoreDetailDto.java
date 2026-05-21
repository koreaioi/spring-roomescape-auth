package roomescape.store.controller.dto.response;

import roomescape.store.domain.Store;

public record StoreDetailDto(
        Long id,
        String name
) {
    public static StoreDetailDto from(Store store) {
        return new StoreDetailDto(
                store.getId(),
                store.getName()
        );
    }
}
