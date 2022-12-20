package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.models.AddressDTO;
import com.example.frontoffice_planning.controller.models.UsersDTO;
import com.example.frontoffice_planning.entity.Address;
import com.example.frontoffice_planning.entity.Planning;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.repository.AddressRepository;
import com.example.frontoffice_planning.repository.PlanningRepository;
import com.example.frontoffice_planning.repository.RoleRepository;
import com.example.frontoffice_planning.repository.UserRepository;
import com.example.frontoffice_planning.service.AddressService;
import com.example.frontoffice_planning.service.PlanningService;
import com.example.frontoffice_planning.service.UserService;
import com.example.frontoffice_planning.utils.Password_Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final PlanningRepository planningRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    private final UserService userService;
    private final PlanningService planningService;

    public UserController(UserRepository userRepository, PlanningRepository planningRepository, RoleRepository roleRepository, AddressRepository addressRepository, AddressService addressService, UserService userService, PlanningService planningService) {
        this.userRepository = userRepository;
        this.planningRepository = planningRepository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.addressService = addressService;
        this.userService = userService;
        this.planningService = planningService;
    }


    // Need to verify if user is current session to get password, otherwise don't take password here
    @GetMapping("/users/id/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") long id) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setPseudo(userEntity.getPseudo());
            usersDTO.setEmail(userEntity.getEmail());
            //   usersDTO.setPassword(userEntity.getPassword());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/users/name/{name}")
    public ResponseEntity<UsersDTO> getUserByName(@PathVariable("name") String name) {
        Optional<Users> user = userRepository.findByPseudoEquals(name);

        if (user.isPresent()) {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setPseudo(userEntity.getPseudo());
            usersDTO.setEmail(userEntity.getEmail());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable("email") String email) {
        Optional<Users> user = userRepository.findByEmailEquals(email);

        if (user.isPresent()) {
            Users userEntity = user.get();
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setIdUser(userEntity.getIdUser());
            usersDTO.setPseudo(userEntity.getPseudo());
            usersDTO.setEmail(userEntity.getEmail());
            usersDTO.setPhoto(userEntity.getPhoto());
            usersDTO.setAddressDTO(new AddressDTO(userEntity.getAddress().getCity(), userEntity.getAddress().getPostalCode()));
            usersDTO.setPlanningId(userEntity.getPlanning().getIdPlanning());
            usersDTO.setSharedPlanningId(userEntity.getShare().stream().map(share -> share.getPlanning().getIdPlanning()).collect(Collectors.toList()));
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@RequestBody UsersDTO usersDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional<Users> verifyUser = userRepository.findByEmailEquals(usersDTO.getEmail());
        if (verifyUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Users newUser = new Users();
        newUser.setPhoto(usersDTO.getPhoto());
        newUser.setEmail(usersDTO.getEmail());
        newUser.setPseudo(usersDTO.getPseudo());
        // Hash password in client or server ?
        Password_Hasher hasheur = new Password_Hasher();
        newUser.setPassword(hasheur.h_password(usersDTO.getPassword()));
        newUser.setActivated(true);
        newUser.addRole(roleRepository.findById(1L).get());

        Address addressDTO = new Address(usersDTO.getAddressDTO().getCity(), usersDTO.getAddressDTO().getPostalCode());

        Address address = addressService.createAddress(addressDTO);
        newUser.setAddress(address);

        Planning planning = planningService.createPlanning(newUser.getPseudo());

        newUser.setPlanning(planning);

        userService.createUser(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}
