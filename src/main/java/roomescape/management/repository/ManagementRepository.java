package roomescape.management.repository;

import roomescape.management.domain.Management;

import java.util.List;

public interface ManagementRepository {

    List<Management> findAllByManagerId(Long managerId);

}
