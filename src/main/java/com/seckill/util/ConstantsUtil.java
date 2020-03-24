package com.seckill.util;

/***
 * @description:  常量工具类
 * @author pikav
 * @date 2020-3-15
 */

public class ConstantsUtil {

    public static final String SUCCESS = "成功";

    public static final String FAIL = "失败";

    public static final String SUCCESS_T = "T";

    public static final String FAIL_F = "F";

    // redis中存储的过期时间60s
    public static final int EXPIRE_TIME = 60;

    public static final String CACHE_KEY_COMMODITY = "commodity";

    // md5盐值字符串， 用于混淆md5
    public static final String SLAT = "ajsljflazzcoi3905723878934j21jfalsf89y12j";

    /**
     * @description: 创建一个36位的ID号
     * @return
     * @author pikav
     */
    public static String getID(){
        return java.util.UUID.randomUUID().toString();
    }

}
