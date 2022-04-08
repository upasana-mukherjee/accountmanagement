package com.anz.accountmanagement.repository;

import com.anz.accountmanagement.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);


}
