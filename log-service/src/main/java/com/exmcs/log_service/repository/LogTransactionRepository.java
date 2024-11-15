package com.exmcs.log_service.repository;

import com.exmcs.log_service.model.entity.LogTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTransactionRepository extends JpaRepository<LogTransaction, Long> {
}
