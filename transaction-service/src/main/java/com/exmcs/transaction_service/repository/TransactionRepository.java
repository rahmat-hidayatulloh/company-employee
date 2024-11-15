package com.exmcs.transaction_service.repository;

import com.exmcs.transaction_service.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.employeeId = :employeeId")
    BigDecimal findTotalAmountByEmployeeId(Long employeeId);
}
