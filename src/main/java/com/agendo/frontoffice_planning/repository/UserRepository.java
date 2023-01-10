package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Planning;
import com.agendo.frontoffice_planning.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsUserByEmail(String email);

    boolean existsUsersByEmailAndPlanning(String email, Planning planning);


    Optional<Users> findByUsername(String pseudo);

    Optional<Users> findByEmail(String email);
}
