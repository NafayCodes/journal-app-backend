package com.abdulnafay.journal.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abdulnafay.journal.entities.UserDTO;
import com.abdulnafay.journal.exceptions.CreateNewAdminException;
import com.abdulnafay.journal.exceptions.CreateNewUserException;
import com.abdulnafay.journal.exceptions.UpdateUserException;
import com.abdulnafay.journal.entities.User;
import com.abdulnafay.journal.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private static final PasswordEncoder passwordEncorder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveNewUser(UserDTO authRequest) {
        try {
            User user = new User();
            user.setUserName(authRequest.getUserName());
            user.setPassword(passwordEncorder.encode(authRequest.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occured for {} when creating a new user: ", authRequest.getUserName(), e);
            throw new CreateNewUserException();
        }
    }

    @Transactional
    public void saveAdmin(UserDTO authRequest) {
        try {
            User user = new User();
            user.setUserName(authRequest.getUserName());
            authRequest.setPassword(passwordEncorder.encode(authRequest.getPassword())); // Encode the password
            user.setRoles(Arrays.asList("USER", "ADMIN"));
            userRepository.save(user);
        } catch (Exception e) {
            throw new CreateNewAdminException();
        }
    }

    public void saveNewUser(User user) {
        try {
            user.setPassword(passwordEncorder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occured for {} when creating a new user: ", user.getUserName(), e);
            throw new UpdateUserException();
        }
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
