package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.entity.Address;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.AddressRepository;
import com.example.frontoffice_planning.repository.PlanningRepository;
import com.example.frontoffice_planning.repository.RoleRepository;
import com.example.frontoffice_planning.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlanningRepository planningRepository;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, PlanningRepository planningRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.planningRepository = planningRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Users createUser(Users user) {
        return userRepository.save(user);
    }
}
