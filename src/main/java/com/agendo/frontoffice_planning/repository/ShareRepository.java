package com.agendo.frontoffice_planning.repository;
import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Share;
import com.agendo.frontoffice_planning.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    Optional<Share> findByPlanningEqualsAndUsersEquals(Planning planning, Users users);

    boolean existsShareByPlanningAndUsers(Planning planning, Users users);

    List<Share> findAllByUsers(Users users);
}
