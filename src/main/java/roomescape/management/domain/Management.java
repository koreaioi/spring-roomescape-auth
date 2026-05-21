package roomescape.management.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.member.domain.Member;
import roomescape.store.domain.Store;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Management {

    private Long id;
    private Member manager;
    private Store store;

    public static Management load(Long id, Member manager, Store store) {
        return new Management(id, manager, store);
    }

}
