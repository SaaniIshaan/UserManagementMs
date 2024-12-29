package com.tekarch.user_managementMS.services;

import com.tekarch.user_managementMS.DTO.AccountDTO;
import com.tekarch.user_managementMS.configs.EmailAlreadyExistsException;
import com.tekarch.user_managementMS.configs.UserNotFoundException;
import com.tekarch.user_managementMS.configs.UsernameAlreadyExistsException;
import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;
import com.tekarch.user_managementMS.repositories.PersonalInfoRepository;
import com.tekarch.user_managementMS.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final RestTemplate restTemplate;
    private final String USER_SERVICE_URL = "http://localhost:8081/users/";
    private final String ACCOUNT_SERVICE_URL = "http://localhost:8082/accounts/";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
     //   user = new User();
        // Ensure username is set
     //   user.setUsername("desired_username_value");
     //   user.setCreated_at(LocalDateTime.now());
     //   userRepository.save(user);
     //   if (user.getUsername() == null || user.getUsername().isEmpty()) {
     //       throw new IllegalArgumentException("Username cannot be null or empty 123");
     //   }
     //   userRepository.save(user);
     //   return userRepository.save(user);
        // Check if username or email already exists
     //   if (userRepository.existsByUsername(user.getUsername())) {
     //       throw new UsernameAlreadyExistsException(user.getUsername());
     //   }
     //   if (userRepository.existsByEmail(user.getEmail())) {
     //       throw new EmailAlreadyExistsException(user.getEmail());
      //  }
      /*  String hashedPassword = passwordEncoder.encode(user.getPassword_hash());
        user.setPassword_hash(hashedPassword);

        if (user.getPassword_hash() != null) {
            String passwordHash = passwordEncoder.encode(user.getPassword_hash());
            user.setPassword_hash(passwordHash);
        }*/
     //   user.setUsername(" ");
     //   user.setEmail(" ");
     //   user.setCreated_at(LocalDateTime.now());
     //   return userRepository.save(user);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(Long userId, User updatedUser ) {
        User existingUser = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
    //    update the fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone_number(updatedUser.getPhone_number());
        existingUser.setKyc_status(updatedUser.getKyc_status());
        existingUser.setCreated_at(updatedUser.getCreated_at());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public PersonalInfo updatePersonalInfo(Long userId, PersonalInfo personalInfo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        personalInfo.setUser(user);
        return personalInfoRepository.save(personalInfo);

     /*   return personalInfoRepository.findByUserUserId(userId)
                .m(personalInfo -> {
                    personalInfo.s(updatedPersonalInfo.getPhoneNumber()); // Update phone number
                    return personalInfoRepository.save(personalInfo);
                })
                .orElseThrow(() -> new PersonalInfoNotFoundException(userId)); // Custom exception
        */
    }

    @Override
    public Optional<PersonalInfo> getPersonalInfoByUserId(Long userId) {
        return Optional.ofNullable(personalInfoRepository.findByUserUserId(userId));
    }

    @Override
    public PersonalInfo createPersonalInfo(Long userId, PersonalInfo personalInfo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id:" + userId));
    //    PersonalInfo personalInfo1 = new PersonalInfo();
     //   personalInfo1.setUser(user);
    //    personalInfo1.setCountry(personalInfo1.getCountry());
    //    personalInfo1.setAddress(personalInfo1.getAddress());
    //    personalInfo1.setCity(personalInfo1.getCity());
    //    personalInfo1.setState(personalInfo1.getState());
    //    personalInfo1.setZipCode(personalInfo1.getZipCode());

        return personalInfoRepository.save(personalInfo);
    }

    @Override
    public List<AccountDTO> getLinkedAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String url = ACCOUNT_SERVICE_URL+"?userId="+userId;
        return List.of(restTemplate.getForObject(url, AccountDTO[].class));
    }

    @Override
    public AccountDTO getAccountDetails(Long userId, Long accountId) {
        String url = ACCOUNT_SERVICE_URL+accountId;
        return restTemplate.getForObject(url, AccountDTO.class);
    }

    @Override
    public void addAccount(Long userId, AccountDTO accountDTO) {
        String url = ACCOUNT_SERVICE_URL;
        accountDTO.setUserId(userId);
        restTemplate.postForObject(url, accountDTO, AccountDTO.class);
    }

    @Override
    public void updateAccount(Long userId, Long accountId, AccountDTO accountDTO) {
        String url = ACCOUNT_SERVICE_URL+accountId;
        restTemplate.put(url, accountDTO);
    }

    @Override
    public void deleteAccount(Long userId, Long accountId) {
        String url = ACCOUNT_SERVICE_URL+accountId;
        restTemplate.delete(url);
    }
}




