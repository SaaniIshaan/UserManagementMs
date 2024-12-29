package com.tekarch.user_managementMS.controllers;

import com.tekarch.user_managementMS.DTO.AccountDTO;
import com.tekarch.user_managementMS.configs.EmailAlreadyExistsException;
import com.tekarch.user_managementMS.configs.UserNotFoundException;
import com.tekarch.user_managementMS.configs.UsernameAlreadyExistsException;
import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;
import com.tekarch.user_managementMS.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

private UserServiceImpl userServiceImpl;

    // Create a new user
    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
      //  if (user.getEmail() == null || user.getEmail().isEmpty()) {
      //      throw new IllegalArgumentException("Email cannot be null or empty");
     //   }
     //   return new ResponseEntity<User>(userServiceImpl.createUser(user), HttpStatus.CREATED);
      //  return ResponseEntity.ok(userServiceImpl.createUser(user));
        try {
            User createdUser = userServiceImpl.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    //Get All users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }
    // Get a user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userServiceImpl.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userServiceImpl.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(username)); // Use the exception here
    }

    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userServiceImpl.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(email)); // Use the exception here
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

    // Update personal info for a user
    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<PersonalInfo> updatePersonalInfo(@PathVariable Long userId, @RequestBody PersonalInfo personalInfo) {
        return ResponseEntity.ok(userServiceImpl.updatePersonalInfo(userId, personalInfo));
    }

    // Get personal info for a user
    @GetMapping("/{userId}/personal-info")
    public ResponseEntity<PersonalInfo> getPersonalInfo(@PathVariable Long userId) {
        return userServiceImpl.getPersonalInfoByUserId(userId)
               .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

     //   Optional<PersonalInfo> personalInfo = personalInfoService.getPersonalInfoByUserId(userId)
     //           .orElseThrow(() -> new PersonalInfoNotFoundException(userId));
     //   return ResponseEntity.ok(personalInfo);

    }

    // Create personal info for a user
    @PostMapping("/{userId}/personal-info")
    public ResponseEntity<PersonalInfo> createPersonalInfo(@PathVariable Long userId, @RequestBody PersonalInfo personalInfo) {
     //   try {
      //      userServiceImpl.createPersonalInfo(userId, personalInfo);
      //      return ResponseEntity.status(HttpStatus.CREATED).build();
     //   } catch (Exception e) {
      //      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
     //   }
        personalInfo.setUserId(userId); // Associate Personal Info with User ID
        PersonalInfo createdPersonalInfo = userServiceImpl.createPersonalInfo(userId, personalInfo);
        return new ResponseEntity<>(createdPersonalInfo, HttpStatus.CREATED);

    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountDTO>> getLinkedAccounts(@PathVariable Long userId) {
        List<AccountDTO> accounts = userServiceImpl.getLinkedAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{userId}/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getAccountDetails(@PathVariable Long userId, @PathVariable Long accountId) {
        AccountDTO account = userServiceImpl.getAccountDetails(userId, accountId);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> addAccount(@PathVariable Long userId, @RequestBody AccountDTO accountDTO) {
        userServiceImpl.addAccount(userId, accountDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/accounts/{accountId}")
    public ResponseEntity<Void> updateAccount(@PathVariable Long userId, @PathVariable Long accountId, @RequestBody AccountDTO accountDTO) {
        userServiceImpl.updateAccount(userId, accountId, accountDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        userServiceImpl.deleteAccount(userId, accountId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<String> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
        logger.error("Error serializing JSON", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }


}
