package com.tekarch.user_managementMS.services;

import com.tekarch.user_managementMS.DTO.AccountDTO;
import com.tekarch.user_managementMS.models.PersonalInfo;
import com.tekarch.user_managementMS.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     User createUser(User user);
     List<User> getAllUsers();
     Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
     User updateUser(Long userId, User user);
     public void deleteUser(Long userId);
     PersonalInfo updatePersonalInfo(Long userId, PersonalInfo updatedPersonalInfo);
     Optional<PersonalInfo> getPersonalInfoByUserId(Long userId);
    PersonalInfo createPersonalInfo(Long userId, PersonalInfo personalInfo);
    List<AccountDTO> getLinkedAccounts(Long userId);
    public AccountDTO getAccountDetails(Long userId, Long accountId);
    public void addAccount(Long userId, AccountDTO accountDTO);
    public void updateAccount(Long userId, Long accountId, AccountDTO accountDTO);
    public void deleteAccount(Long userId, Long accountId);
}
