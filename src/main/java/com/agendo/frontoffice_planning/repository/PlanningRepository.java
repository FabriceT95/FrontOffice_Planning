package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

}
