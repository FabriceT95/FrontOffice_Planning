package com.example.frontoffice_planning.controller;

import com.example.frontoffice_planning.controller.exception.user.UserAlreadyExistException;
import com.example.frontoffice_planning.controller.models.SigninRequest;
import com.example.frontoffice_planning.controller.models.SignupRequest;
import com.example.frontoffice_planning.entity.Users;
import com.example.frontoffice_planning.security.jwt.JwtResponse;
import com.example.frontoffice_planning.security.jwt.JwtUtils;
import com.example.frontoffice_planning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Basic signup. Creates a User based on SignupRequest
     *
     * @param signupRequest SignupRequest object with all data needed to create a new User
     * @return HttpStatus 201
     */
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody SignupRequest signupRequest) {
        try {
            userService.signup(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Basic signin. User logs in and retrives a JWT for all interactions on this API
     *
     * @param signinRequest SigninRequest object with Email and Password
     * @return JwtResponse object with Users email and token
     */

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(auth);
        } catch (Exception e) {
            e.getStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a JSON Web Token
        String generatedToken = jwtUtils.generateJwt(authentication);

        // TODO: return username from Authentication SecurityContextHolder
        Users connectedUser = (Users) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(connectedUser.getEmail(), generatedToken);

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}
