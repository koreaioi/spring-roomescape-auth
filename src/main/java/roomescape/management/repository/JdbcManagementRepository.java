package roomescape.management.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.management.domain.Management;
import roomescape.member.domain.Member;
import roomescape.member.domain.Role;
import roomescape.store.domain.Store;

import java.util.List;

@Repository
public class JdbcManagementRepository implements ManagementRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Management> managementRowMapper = (resultSet, rowNumber) -> Management.load(
            resultSet.getLong("management_id"),
            Member.load(
                    resultSet.getLong("member_id"),
                    resultSet.getString("member_name"),
                    resultSet.getString("password"),
                    Role.valueOf(resultSet.getString("role"))
            ),
            Store.load(
                    resultSet.getLong("store_id"),
                    resultSet.getString("store_name")
            )
    );

    public JdbcManagementRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Management> findAllByManagerId(Long managerId) {
        String sql = """
                SELECT 
                    m.id AS management_id,
                    mem.id AS member_id,
                    mem.name AS member_name,
                    mem.password,
                    mem.role,
                    s.id AS store_id,
                    s.name AS store_name
                FROM management m
                JOIN member mem ON m.manager_id = mem.id
                JOIN store s ON m.store_id = s.id
                WHERE m.manager_id = :managerId
                """;
        MapSqlParameterSource params = new MapSqlParameterSource("managerId", managerId);
        return jdbcTemplate.query(sql, params, managementRowMapper);
    }

}
