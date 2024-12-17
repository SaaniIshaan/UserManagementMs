package com.tekarch.user_managementMS.controllers;

import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;
import com.tekarch.user_managementMS.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

private UserServiceImpl userServiceImpl;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userServiceImpl.createUser(user), HttpStatus.CREATED);
    //    return ResponseEntity.ok(userServiceImpl.createUser(user));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userServiceImpl.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody User user) {
        User updatedUser = userServiceImpl.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
       userServiceImpl.deleteUser(userId);
       return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@PathVariable Long userId, @RequestBody PersonalInfo personalInfo) {
        return ResponseEntity.ok(userServiceImpl.updatePersonalInfo(userId, personalInfo));
    }

    @GetMapping("/{userId}/personal-info")
    public ResponseEntity<PersonalInfo> getPersonalInfo(@PathVariable Long userId) {
        return userServiceImpl.getPersonalInfoByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/personal-info")
    public ResponseEntity<?> createPersonalInfo(@PathVariable Long userId, @RequestBody PersonalInfo personalInfo) {
        try {
            userServiceImpl.createPersonalInfo(userId, personalInfo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

}
