package com.ironant.common.exception;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.ironant.common.utils.ApiCode;
import com.ironant.common.utils.GsonUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * @Description: API异常统一管理
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-30 17:59
 */
public class ApiException extends Exception {

    private  int code;
    private String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ApiException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDisplayMessage() {
        return message + "(code:" + code + ")";
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            try {
                String msg = body.string();
//                LogUtil.e(msg);
                if(!TextUtils.isEmpty(msg)){
                    //TODO 根据服务器返回的数据 生产异常信息重写此信息
                    // {"message":"Request resource not found.","status":404}
                    ServerErrorInfo info = GsonUtil.gson().fromJson(msg,ServerErrorInfo.class);
                    ex = new ApiException(e, ApiCode.Request.HTTP_ERROR);
                    ex.code = info.getStatus();
                    ex.message = info.getMessage();
                    return ex;
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ApiCode.Request.HTTP_ERROR);
            switch (httpException.code()) {
                case ApiCode.Http.UNAUTHORIZED:
                    ex.code = ApiCode.Http.UNAUTHORIZED;
                    return ex;
                case ApiCode.Http.FORBIDDEN:
                    ex.code = ApiCode.Http.FORBIDDEN;
                    return ex;
                case ApiCode.Http.NOT_FOUND:
                    ex.code = ApiCode.Http.NOT_FOUND;
                    return ex;
                case ApiCode.Http.REQUEST_TIMEOUT:
                    ex.code = ApiCode.Http.REQUEST_TIMEOUT;
                    return ex;
                case ApiCode.Http.GATEWAY_TIMEOUT:
                    ex.code = ApiCode.Http.GATEWAY_TIMEOUT;
                    return ex;
                case ApiCode.Http.INTERNAL_SERVER_ERROR:
                    ex.code = ApiCode.Http.UNAUTHORIZED;
                    return ex;
                case ApiCode.Http.BAD_GATEWAY:
                    ex.code = ApiCode.Http.BAD_GATEWAY;
                    return ex;
                case ApiCode.Http.SERVICE_UNAVAILABLE:
                    ex.code = ApiCode.Http.SERVICE_UNAVAILABLE;
                    return ex;
                default:
                    ex.code = ApiCode.Request.NETWORK_ERROR;
                    ex.message = "NETWORK_ERROR";
                    return ex;
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ex = new ApiException(e, ApiCode.Request.PARSE_ERROR);
            ex.message = "PARSE_ERROR";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ApiCode.Request.NETWORK_ERROR);
            ex.message = "NETWORK_ERROR";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ApiCode.Request.SSL_ERROR);
            ex.message = "SSL_ERROR";
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ApiCode.Request.TIMEOUT_ERROR);
            ex.message = "TIMEOUT_ERROR";
            return ex;
        } else {
            ex = new ApiException(e, ApiCode.Request.UNKNOWN);
            ex.message = "未知异常 "+e.getMessage();
            return ex;
        }
    }

}
