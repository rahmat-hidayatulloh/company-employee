package com.exmcs.log_service.common.base;

public interface BaseService<T extends BaseRequest, V extends BaseResponse>{
    V execute(T input);
}