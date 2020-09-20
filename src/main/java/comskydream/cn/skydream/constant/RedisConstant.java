package comskydream.cn.skydream.constant;

/**
 * @author Jayson
 * @date 2020/9/20  0:22
 */
public class RedisConstant {

    /**验证码缓存前缀*/
    public static final String SMS_CODE_CACHE_PREFIX ="sms:code";

    /**验证码有效期*/
    public static final long SMS_CODE_VALID_PERIOD = 60 * 1000;
}
