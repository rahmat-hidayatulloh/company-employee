package com.exmcs.transaction_service.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IsExistEmployeeDto {

    private boolean isExistEmployee;
}
