package com.ulxsfrank.business.data.rest.result;

import org.apache.commons.lang.StringUtils;

public class Response<T> {

    private String message;
    private T data;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public Response<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean isSuccess() {
        if (StringUtils.isEmpty(getStatus())) {
            return Boolean.FALSE;
        }
        if (getStatus().startsWith("2")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isFailed() {
        return Boolean.valueOf(!isSuccess().booleanValue());
    }

    public Response<T> success() {
        setStatus(StatusCode.SUCCESS_00_Success);
        return this;
    }

    public Response<T> failed() {
        setStatus(StatusCode.ERROR_00_Failed);
        return this;
    }

}
