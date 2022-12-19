package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.PlanningRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlanningService {

    private final PlanningRepository planningRepository;

    public PlanningService(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }

    @Transactional
    public Planning createPlanning(String name) {
        Planning planning = new Planning(name+ "'s planning", LocalDateTime.now());
        return planningRepository.save(planning);
    }

    public Optional<Planning> getPlanningById(Long id) { return planningRepository.findById(id);}

    public Planning updatePlanningName(Planning planning, String name) {
        planning.setNamePlanning(name);
        return planningRepository.save(planning);
    }
}
