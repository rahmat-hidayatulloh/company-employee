package com.exmcs.transaction_service.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SourceOfLogTransaction {

    private String csvFilename;

    private Integer totalRecord;

    private Integer totalRecordFailed;

    private Integer totalRecordSuccess;

    private String failedIdNotes;

}
