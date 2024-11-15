package com.exmcs.transaction_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_fee")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "amount_fee", precision = 10, scale = 2)
    private BigDecimal amountFee;

    @Column(name = "tgl_fee")
    private LocalDate tglFee;

}
