package comskydream.cn.skydream.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据格式化工具类会有多线程影响
 * @author Jayson
 * @date 2020/9/25 16:15
 */
public final class DecimalFormatUtils {

    private static final DecimalFormatUtils decimalFormat = new DecimalFormatUtils();

    /**
     * 每个线程存放不同的模板
     */
    private ThreadLocal<Map<String, DecimalFormat>> decimalFormatMap = ThreadLocal.withInitial(() -> new HashMap<>(16));

    private DecimalFormatUtils() {
    }

    public static DecimalFormatUtils instance() {
        return decimalFormat;
    }

    /**
     * 获取模板
     *
     * @param pattern
     * @return
     */
    private DecimalFormat getDf(final String pattern) {
        Map<String, DecimalFormat> tl = decimalFormatMap.get();
        DecimalFormat df = tl.get(pattern);
        if (df == null) {
            df = new DecimalFormat(pattern);
            tl.put(pattern, df);
        }
        return df;
    }

    /**
     * 格式化
     *
     * @param obj     待格式化
     * @param pattern 格式
     * @return 格式好的数字
     */
    public String format(Object obj, String pattern) {
        return getDf(pattern).format(obj);
    }

    /**
     * 反格式化
     *
     * @param num
     * @param pattern
     * @return Number
     * @throws ParseException
     */
    public Number parse(String num, String pattern) throws ParseException {
        return getDf(pattern).parse(num);
    }

}
