package com.tekarch.user_managementMS.repositories;

import com.tekarch.user_managementMS.models.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    PersonalInfo findByUserUserId(Long userId);
}
