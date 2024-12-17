package com.tekarch.user_managementMS.services;

import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     User createUser(User user);
     List<User> getAllUsers();
     Optional<User> getUserById(Long userId);
     User updateUser(Long userId, User user);
 //    User updateUser(User user);
     public void deleteUser(Long userId);
     PersonalInfo updatePersonalInfo(Long userId, PersonalInfo personalInfo);
     Optional<PersonalInfo>getPersonalInfoByUserId(Long userId);
    public void createPersonalInfo(Long userId, PersonalInfo personalInfo);



}
