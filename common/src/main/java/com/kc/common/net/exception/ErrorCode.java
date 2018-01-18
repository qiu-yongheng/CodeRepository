package com.kc.common.net.exception;

/**
 * @author 邱永恒
 * @time 2017/11/21  14:36
 * @desc 网络通用状态码定义: XSnow专用
 */

public class ErrorCode {
    /**
     * 对应HTTP的状态码
     */
    public static class Http {
        public static final int UNAUTHORIZED = 401; // 没有登录
        public static final int FORBIDDEN = 403; // 权限不足
        public static final int NOT_FOUND = 404; // 没有找到
        public static final int REQUEST_TIMEOUT = 408;
        public static final int INTERNAL_SERVER_ERROR = 500;
        public static final int BAD_GATEWAY = 502;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int GATEWAY_TIMEOUT = 504;
    }

    /**
     * Request请求码
     */
    public static class Request {
        //未知错误
        public static final int UNKNOWN = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议出错
        public static final int HTTP_ERROR = 1003;
        //证书出错
        public static final int SSL_ERROR = 1005;
        //连接超时
        public static final int TIMEOUT_ERROR = 1006;
        //调用错误
        public static final int INVOKE_ERROR = 1007;
        //类转换错误
        public static final int CONVERT_ERROR = 1008;
    }
}