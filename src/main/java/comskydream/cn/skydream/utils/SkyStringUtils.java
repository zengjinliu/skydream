package comskydream.cn.skydream.utils;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Jayson
 * @date 2020/9/22 17:54
 */
public class SkyStringUtils extends StringUtils {

    private static Pattern PATTERN = Pattern.compile(",");

    /**
     * 分割逗号工具类
     * @param string
     * @return
     */
    public static List<String> splitToList(String string){
        if(isEmpty(string)){
            return Collections.emptyList();
        }
        List<String> list = PATTERN.splitAsStream(string).collect(Collectors.toList());
        return  list;
    }


}
