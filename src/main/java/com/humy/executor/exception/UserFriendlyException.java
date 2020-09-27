package com.humy.executor.exception;

/**
 * @author Humy
 * @date 2020/9/15 23:35
 */
public class UserFriendlyException extends RuntimeException {

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserFriendlyException(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.msg = resultStatusEnum.getMsg();
    }
}
