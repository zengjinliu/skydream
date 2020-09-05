package comskydream.cn.skydream.utils;

import java.util.UUID;

/**
 * @author Jayson
 * @date 2020/9/5  16:55
 */
public class UuidUtils {

    public static String id(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
