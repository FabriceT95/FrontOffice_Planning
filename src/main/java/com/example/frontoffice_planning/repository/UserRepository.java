package com.example.frontoffice_planning.repository;
import com.example.frontoffice_planning.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPseudoEquals(String pseudo);
    Optional<Users> findByEmail(String email);
}
