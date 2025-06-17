package com.abdulnafay.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdulnafay.journal.entities.UserDTO;
import com.abdulnafay.journal.services.UserDetailsServiceImpl;
import com.abdulnafay.journal.services.UserService;
import com.abdulnafay.journal.utils.JwtUtil;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    @Autowired
    public PublicController(UserService userService, AuthenticationManager authenticationManager,
            UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody UserDTO authRequest) {
        userService.saveNewUser(authRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody UserDTO authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
