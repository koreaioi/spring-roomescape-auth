package roomescape.store.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.store.domain.Store;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStoreRepository implements StoreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Store> storeRowMapper = (resultSet, rowMapper) ->
            Store.load(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            );

    public JdbcStoreRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Store> findAll() {
        String sql = "SELECT * FROM store";
        return jdbcTemplate.query(sql, storeRowMapper);
    }

    @Override
    public Optional<Store> findById(Long id) {
        String sql = "SELECT * FROM store WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, storeRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
