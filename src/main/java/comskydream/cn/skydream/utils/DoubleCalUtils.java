package comskydream.cn.skydream.utils;

import java.math.BigDecimal;

/**
 * double计算工具类
 *
 * @author Jayson
 * @date 2020/9/25 16:31
 */
public class DoubleCalUtils {

    private DoubleCalUtils() {
    }


    /**
     * 取余
     * @param value 值
     * @param scale 小数位数
     * @param roundingMode 取余方式0-7
     * @return
     */
    public static Double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

    /**
     * 四舍五入
     * @param value
     * @param scale
     * @return
     */
    public static Double round(double value,int scale){
        return round(value,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法
     * @param d1
     * @param d2
     * @return
     */
    public static Double add(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 减法
     * @param d1
     * @param d2
     * @return
     */
    public static Double sub(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static Double mul(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 除法，四舍五入
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static Double div(double d1,double d2,int scale){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
