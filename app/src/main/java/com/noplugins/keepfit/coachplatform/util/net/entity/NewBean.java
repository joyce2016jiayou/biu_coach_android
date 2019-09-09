package com.noplugins.keepfit.coachplatform.util.net.entity;

public class NewBean<T> {
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;
    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
