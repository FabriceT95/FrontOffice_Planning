package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.exception.UserNotFoundException;
import com.example.frontoffice_planning.controller.models.UsersDTO;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Getting the user logged in from his token.
     *
     * @param users Getting authenticated user from the auth filter
     * @return UserDTO, simple representation of a User
     */
    @GetMapping("/users")
    public ResponseEntity<UsersDTO> getUser(@RequestAttribute("user") Users users) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getLoggedUser(users));
    }

    /**
     * Getting any user by his ID. Auth filter only allows authenticated users to get there
     *
     * @param id User id who is looked for
     * @return UserDTO, simple representation of a User
     */
    @GetMapping("/users/id/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") long id) {
        try {
            UsersDTO usersDTO = userService.getUserDTOById(id);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Getting any user by his username. Auth filter only allows authenticated users to get there
     *
     * @param name Username who is looked for
     * @return UserDTO, simple representation of a User
     */
    @GetMapping("/users/name/{name}")
    public ResponseEntity<UsersDTO> getUserByName(@PathVariable("name") String name) {
        try {
            UsersDTO usersDTO = userService.getUserDTOByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /**
     * Getting any user by his email. Auth filter only allows authenticated users to get there
     *
     * @param email User email who is looked for
     * @return UserDTO, simple representation of a User
     */
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UsersDTO> getUserByEmail(@PathVariable("email") String email) {
        try {
            UsersDTO usersDTO = userService.getUserDTOByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    /**
     * Authenticated user updating his profile. Auth filter only allows authenticated users to get there
     *
     * @param usersDTO simple representation of a User with updated data to apply on a User entity
     * @return UserDTO updated, simple representation of a User
     */
    @PutMapping("/users")
    public ResponseEntity<UsersDTO> updateUser(@Valid @RequestBody UsersDTO usersDTO) {
        try {
            userService.updateUser(usersDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
    }

}
