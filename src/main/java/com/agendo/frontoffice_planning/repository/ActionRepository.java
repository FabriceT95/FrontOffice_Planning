package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
}
