package org.accountmanagement.repo;

import org.accountmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findByAccountNumber(Integer accountNumber);
}
