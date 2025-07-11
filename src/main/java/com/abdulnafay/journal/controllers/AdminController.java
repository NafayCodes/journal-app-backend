package com.abdulnafay.journal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdulnafay.journal.entities.UserDTO;
import com.abdulnafay.journal.entities.User;
import com.abdulnafay.journal.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDTO authRequest) {
        if (authRequest.getUserName() == null || authRequest.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.saveAdmin(authRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
