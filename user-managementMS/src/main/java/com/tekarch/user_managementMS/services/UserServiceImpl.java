package com.tekarch.user_managementMS.services;

import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;
import com.tekarch.user_managementMS.repositories.AccountRepository;
import com.tekarch.user_managementMS.repositories.PersonalInfoRepository;
import com.tekarch.user_managementMS.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
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
    public User updateUser(Long userId, User updatedUser ) {
        User existingUser = userRepository.findById(userId)
               .orElseThrow(() -> new RuntimeException("User not found"));
    //    update the fields
        existingUser.setUser_name(updatedUser.getUser_name());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone_number(updatedUser.getPhone_number());
        existingUser.setKyc_status(updatedUser.getKyc_status());
        existingUser.setCreated_at(updatedUser.getCreated_at());
        return userRepository.save(existingUser);
    }

//    @Override
//    public User updateUser(User user) {
//        return userRepository.save(user);
//    }

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

    }

    @Override
    public Optional<PersonalInfo> getPersonalInfoByUserId(Long userId) {
        return Optional.ofNullable(personalInfoRepository.findByUserUserId(userId));
    }

    @Override
    public void createPersonalInfo(Long userId, PersonalInfo personalInfo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id"));
        PersonalInfo personalInfo1 = new PersonalInfo();
        personalInfo1.setUser(user);
        personalInfo1.setCountry(personalInfo1.getCountry());
        personalInfo1.setAddress(personalInfo1.getAddress());
        personalInfo1.setCity(personalInfo1.getCity());
        personalInfo1.setState(personalInfo1.getState());
        personalInfo1.setZipCode(personalInfo1.getZipCode());
        personalInfoRepository.save(personalInfo1);
    }


}




