package roomescape.store.domain;

import lombok.Getter;

@Getter
public class Store {

    private Long id;
    private String name;

    private Store(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Store load(Long id, String name) {
        return new Store(id, name);
    }

}
