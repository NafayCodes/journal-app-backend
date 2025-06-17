package com.abdulnafay.journal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abdulnafay.journal.entities.UserDTO;
import com.abdulnafay.journal.exceptions.NullUserDTOException;
import com.abdulnafay.journal.entities.User;
import com.abdulnafay.journal.repositories.UserRepository;
import com.abdulnafay.journal.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> getAll(@RequestParam String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authentication.getName();
        if (authenticatedUserName.equals(userName)) {
            return new ResponseEntity<>(userService.findByUserName(authenticatedUserName), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserDTO authRequest) {
        // Whenever a user is authenticated, its details are stored in
        // SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        if (authRequest.getUserName() == null
                || authRequest.getUserName().equals("") || authRequest.getPassword() == null
                || authRequest.getPassword().equals("")) {
            throw new NullUserDTOException();
        }

        User userInDB = userService.findByUserName(userName);
        userInDB.setUserName(authRequest.getUserName());
        userInDB.setPassword(authRequest.getPassword());
        userService.saveNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
