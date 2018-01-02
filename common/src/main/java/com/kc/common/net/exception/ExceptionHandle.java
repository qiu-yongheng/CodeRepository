package com.kc.common.net.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @author 邱永恒
 * @time 2017/11/21  14:34
 * @desc 异常统一处理
 */

public class ExceptionHandle {
    public static String handleException(Throwable e) {
        e.printStackTrace();
        String errorInfo;

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case ErrorCode.Http.UNAUTHORIZED:
                    errorInfo = "请先登录账号";
                    break;
                case ErrorCode.Http.FORBIDDEN:
                    errorInfo = "您没有权限使用该功能";
                    break;
                case ErrorCode.Http.NOT_FOUND:
                    errorInfo = "没有找到资源";
                    break;
                case ErrorCode.Http.REQUEST_TIMEOUT:
                case ErrorCode.Http.GATEWAY_TIMEOUT:
                case ErrorCode.Http.INTERNAL_SERVER_ERROR:
                    errorInfo = "服务器异常";
                    break;
                case ErrorCode.Http.BAD_GATEWAY:
                case ErrorCode.Http.SERVICE_UNAVAILABLE:
                default:
                    errorInfo = "网络连接不稳定, 请稍后重试";
                    break;
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            errorInfo = "数据解析异常";
        } else if (e instanceof ConnectException) {
            errorInfo = "网络连接超时, 请稍后重试";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            errorInfo = "SSL证书异常";
        } else if (e instanceof SocketTimeoutException) {
            errorInfo = "网络连接超时, 请稍后重试";
        } else {
            errorInfo = e.getMessage();
        }

        return errorInfo;
    }

    public static int getHttpExceptionCode(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            return httpException.code();
        } else {
            return 0;
        }
    }
}
