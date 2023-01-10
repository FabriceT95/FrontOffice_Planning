package com.agendo.frontoffice_planning.repository;

import com.agendo.frontoffice_planning.entity.Event;
import com.agendo.frontoffice_planning.entity.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findTop10ByPlanningInOrderByDateCreatedDesc(List<Planning> planningList);

    List<Event> findTop10EventsByPlanningIdPlanningOrderByDateCreatedDesc(Long id);

}
