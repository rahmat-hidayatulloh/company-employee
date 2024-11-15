package com.exmcs.log_service.model.request;

import com.exmcs.log_service.common.base.BaseRequest;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostLogTransactionRequest extends BaseRequest {

    private String csvFilename;

    private Integer totalRecord;

    private Integer totalRecordFailed;

    private Integer totalRecordSuccess;

    private String failedIdNotes;
}
