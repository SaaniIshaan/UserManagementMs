package com.tekarch.user_managementMS.repositories;

import com.tekarch.user_managementMS.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
