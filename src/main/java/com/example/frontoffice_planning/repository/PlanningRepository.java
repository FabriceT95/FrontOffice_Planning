package com.example.frontoffice_planning.repository;

import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

}
