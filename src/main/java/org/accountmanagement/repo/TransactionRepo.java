package org.accountmanagement.repo;

import org.accountmanagement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByStatus(String s);
}
