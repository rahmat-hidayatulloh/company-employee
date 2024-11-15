package com.exmcs.transaction_service.model.request;

import com.exmcs.transaction_service.common.base.BaseRequest;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostTransactionRequest extends BaseRequest {

    private MultipartFile file;

}
