package com.exmcs.login_service.common.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {
}
