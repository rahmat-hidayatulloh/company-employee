package com.exmcs.transaction_service.repository;

import com.exmcs.transaction_service.model.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee, Long> {
}
