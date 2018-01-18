package com.kc.common.common;

/**
 * @author 邱永恒
 * @time 2017/12/15  11:54
 * @desc ${TODD}
 */

public class HttpConfig {
    public static final int MAX_AGE_ONLINE = 60;//默认最大在线缓存时间（秒）
    public static final int MAX_AGE_OFFLINE = 24 * 60 * 60;//默认最大离线缓存时间（秒）
    public static final int MAX_OFFLINE_SIZE = 1024 * 1024 * 50;//最大离线缓存空间(50M)
    public static final int CONNECT_TIMEOUT = 30;//连接超时(秒)
    public static final int READ_TIMEOUT = 30;//读取超时(秒)
    public static final int WRITE_TIMEOUT = 30;//写超时(秒)
    public static final String BASE_URL = "";//写超时(秒)
    public static final String REQUEST_SERVER_URL = "填入接口地址";

}
