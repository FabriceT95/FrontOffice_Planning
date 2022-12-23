package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.UserAlreadyExistException;
import com.example.frontoffice_planning.controller.exception.UserNotFoundException;
import com.example.frontoffice_planning.controller.models.SignupRequest;
import com.example.frontoffice_planning.controller.models.UsersDTO;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlanningService planningService;

    private final RoleRepository roleRepository;

    private final AddressService addressService;

    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PlanningService planningService, RoleRepository roleRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.planningService = planningService;
        this.addressService = addressService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void signup(SignupRequest signupRequest) throws UserAlreadyExistException {
        boolean isExist = userRepository.existsUserByEmail(signupRequest.getEmail());
        if (isExist) {
            throw new UserAlreadyExistException(signupRequest.getEmail());
        } else {

            Users newUser = new Users();
            newUser.setEmail(signupRequest.getEmail());
            newUser.setUsername(signupRequest.getUsername());
            newUser.setPassword(encoder.encode(signupRequest.getPassword()));
            newUser.setActivated(true);
            newUser.addRole(roleRepository.findById(1L).get());

            Address addressDTO = new Address(signupRequest.getCity(), signupRequest.getPostalCode());

            Address address = addressService.createAddress(addressDTO);
            newUser.setAddress(address);

            Planning planning = planningService.createPlanning(newUser.getUsername());

            newUser.setPlanning(planning);


            userRepository.save(newUser);
        }
    }

    public void updateUser(UsersDTO usersDTO) throws UserNotFoundException {
        Optional<Users> OptUser = userRepository.findByEmail(usersDTO.getEmail());
        if (OptUser.isEmpty()) {
            throw new UserNotFoundException(usersDTO.getUsername());
        } else {
            Users user = OptUser.get();
            user.setUsername(usersDTO.getUsername());
            user.setPhoto(usersDTO.getPhoto());
            // TODO : password
            userRepository.save(user);
        }

    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
