package com.humy.executor.exception;

/**
 * @author Humy
 * @date 2020/9/15 23:40
 */
public enum ResultStatusEnum {

    SUCCESS(200, "success"),

    PASSWORD_ERR(201, "密码错误"),

    PARAM_ERR(202, "参数错误"),

    SYSTEM_ERR(300, "系统错误"),

    FILE_NULL(301, "上传文件为空"),

    FILE_NOT_EXIST(302, "文件不存在");

    private int code;

    private String msg;

    ResultStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
