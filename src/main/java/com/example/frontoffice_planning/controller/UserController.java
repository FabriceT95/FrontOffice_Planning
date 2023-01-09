package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.exception.planning.PlanningNotFoundException;
import com.example.frontoffice_planning.controller.exception.share.ShareNotFoundException;
import com.example.frontoffice_planning.controller.exception.user.UserNotFoundException;
import com.example.frontoffice_planning.controller.exception.user.UserNotOwnerException;
import com.example.frontoffice_planning.controller.exception.user.UserUpdateDeniedException;
import com.example.frontoffice_planning.controller.models.User.GetSharedUsersDTO;
import com.example.frontoffice_planning.controller.models.Share.SharedUsersDTO;
import com.example.frontoffice_planning.controller.models.User.UpdateUserDTO;
import com.example.frontoffice_planning.controller.models.User.UsersDTO;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") long id) throws UserNotFoundException {
        try {
            UsersDTO usersDTO = userService.getUserDTOById(id);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(id);
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

    @PostMapping("/users/shared")
    public ResponseEntity<List<SharedUsersDTO>> getSharedUsers(@RequestAttribute("user") Users users, @Valid @RequestBody GetSharedUsersDTO getSharedUsersDTO) {
        try {
            List<SharedUsersDTO> usersDTOList = userService.getSharedUsers(getSharedUsersDTO, users);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTOList);
        } catch (ShareNotFoundException | UserNotOwnerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PlanningNotFoundException e) {
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
    public ResponseEntity<UsersDTO> updateUser(@RequestAttribute("user") Users users, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        try {
            UsersDTO usersDTO = userService.updateUser(users, updateUserDTO);
            return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserUpdateDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }


}
