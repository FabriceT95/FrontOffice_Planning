package com.example.frontoffice_planning.service;

import com.example.frontoffice_planning.controller.exception.*;
import com.example.frontoffice_planning.controller.models.*;
import com.example.frontoffice_planning.entity.Address;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.PlanningRepository;
import com.example.frontoffice_planning.repository.RoleRepository;
import com.example.frontoffice_planning.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PlanningRepository planningRepository;

    private final RoleRepository roleRepository;

    private final AddressService addressService;

    private final ShareService shareService;

    @Autowired
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PlanningRepository planningRepository, RoleRepository roleRepository, AddressService addressService, ShareService shareService) {
        this.userRepository = userRepository;
        this.planningRepository = planningRepository;
        this.addressService = addressService;
        this.roleRepository = roleRepository;
        this.shareService = shareService;
    }

    public UsersDTO getLoggedUser(Users users) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setIdUser(users.getIdUser());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setPhoto(users.getPhoto());
        usersDTO.setAddressDTO(new AddressDTO(users.getAddress().getIdAddress(), users.getAddress().getCity(), users.getAddress().getPostalCode()));
        usersDTO.setPlanningId(users.getPlanning().getIdPlanning());
        usersDTO.setRoleDTOList(users.getRoles().stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setIdRole(role.getIdRole());
            roleDTO.setName(role.getName());
            return roleDTO;
        }).collect(Collectors.toList()));
        usersDTO.setSharedPlanningId(users.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
        return usersDTO;
    }

    @Transactional
    public void signup(SignupRequest signupRequest) throws UserAlreadyExistException {
        boolean isExist = userRepository.existsUserByEmail(signupRequest.getEmail());
        if (isExist) {
            throw new UserAlreadyExistException(signupRequest.getEmail());
        }

        Users newUser = new Users();
        newUser.setEmail(signupRequest.getEmail());
        newUser.setUsername(signupRequest.getUsername());
        newUser.setPassword(encoder.encode(signupRequest.getPassword()));
        newUser.setActivated(true);
        newUser.addRole(roleRepository.findById(1L).get());

        Address addressDTO = new Address(signupRequest.getCity(), signupRequest.getPostalCode());

        Address address = addressService.createAddress(addressDTO);
        newUser.setAddress(address);

        System.out.println("Planning being created for new User...");

        Planning planning = planningRepository.save(new Planning(newUser.getUsername() + "'s planning", LocalDateTime.now()));

        System.out.println("Planning created with id " + planning.getIdPlanning());

        newUser.setPlanning(planning);

        System.out.println("User being saved...");

        Users users = userRepository.save(newUser);

        System.out.println("User " + users.getIdUser() + " is saved");
    }

    public void updateUser(UsersDTO usersDTO) throws UserNotFoundException {
        Optional<Users> OptUser = userRepository.findByEmail(usersDTO.getEmail());
        if (OptUser.isEmpty()) {
            throw new UserNotFoundException(usersDTO.getUsername());
        }
        Users user = OptUser.get();
        user.setUsername(usersDTO.getUsername());
        user.setPhoto(usersDTO.getPhoto());
        // TODO : password
        userRepository.save(user);
        System.out.println("User " + user.getIdUser() + " is updated");

    }

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UsersDTO getUserDTOById(Long id) throws UserNotFoundException {

        Optional<Users> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        } else {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setUsername(userEntity.getUsername());
            usersDTO.setEmail(userEntity.getEmail());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getIdAddress(), userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return usersDTO;
        }
    }

    public UsersDTO getUserDTOByName(String name) throws UserNotFoundException {
        Optional<Users> user = userRepository.findByUsername(name);
        if (user.isEmpty()) {
            throw new UserNotFoundException(name);
        } else {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setUsername(userEntity.getUsername());
            usersDTO.setEmail(userEntity.getEmail());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return usersDTO;
        }
    }

    public UsersDTO getUserDTOByEmail(String email) throws UserNotFoundException {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(email);
        } else {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setUsername(userEntity.getUsername());
            usersDTO.setEmail(userEntity.getEmail());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return usersDTO;
        }
    }

    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UsersDTO> getSharedUsers(GetSharedUsersDTO getSharedUsersDTO, Users users) throws PlanningNotFoundException, ShareNotFoundException, UserNotOwnerException {
        Optional<Planning> optPlanning = planningRepository.findById(getSharedUsersDTO.getIdPlanning());
        if (optPlanning.isEmpty()) {
            throw new PlanningNotFoundException(getSharedUsersDTO.getIdPlanning());
        }
        Planning planning = optPlanning.get();
        boolean isAuthorized = !isOwner(planning, users) && !shareService.shareExists(planning, users);

        if (planning.getShare().isEmpty()) {
            return List.of();
        }
        return planning.getShare().stream().map((share -> {
            try {
                return getUserDTOById(share.getUsers().getIdUser());
            } catch (UserNotFoundException e) {
                return null;
            }
        })).collect(Collectors.toList());
    }

    public boolean isOwner(Planning planning, Users users) throws UserNotOwnerException {
        boolean isOwner = userRepository.existsUsersByEmailAndPlanning(users.getEmail(), planning);
        if (!isOwner) {
            throw new UserNotOwnerException(users.getEmail(), planning.getNamePlanning());
        }
        return true;
    }
}
