package com.ulxsfrank.business.data.rest.result;

public class FallBack<T> {

    private static final String ERR_CODE = StatusCode.ERROR_06_ServerUnavailable;
    private static final String ERR_MSG = "远程服务调用失败";

    public NoResult getNoResult() {
        NoResult result = new NoResult();
        result.setMessage(ERR_MSG);
        result.setStatus(ERR_CODE);
        result.setResult(Boolean.FALSE);
        return result;
    }

    public PageResult<T> getPageResult() {
        PageResult<T> result = new PageResult<T>();
        result.setMessage(ERR_MSG);
        result.setStatus(ERR_CODE);
        return result;
    }

    public SingleResult<T> getSingleResult() {
        SingleResult<T> result = new SingleResult<T>();
        result.setMessage(ERR_MSG);
        result.setStatus(ERR_CODE);
        return result;
    }
}
