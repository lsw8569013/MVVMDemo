package com.ironant.common.exception;

import java.io.Serializable;

/**
 * 服务器返回的异常信息Bean
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 * @date :2020-02-13 18:02
 */
public class ServerErrorInfo implements Serializable {

    /**
     * message : Request resource not found.
     * status : 404
     */

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
