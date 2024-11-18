package com.exmcs.login_service.common.base;

public interface BaseService<T extends BaseRequest, V extends BaseResponse>{
    V execute(T input);
}