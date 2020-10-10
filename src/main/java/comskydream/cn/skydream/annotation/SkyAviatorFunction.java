package comskydream.cn.skydream.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义函数 注解<br>
 * value 不能为null, value 的值为该自定义函数的名称<br>
 *  description 该函数的用途。详细说明该表达式的用法，返回值，变量，逻辑要点。
 * @author Jayson
 * @date 2020/10/10 8:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SkyAviatorFunction {

    /**
     * 自定义函数名称,同时也是spring bean 的名称
     * @return
     */
    String value() default "";

    /**
     * 详细说明该表达式的用法，返回值，变量，逻辑要点。
     * @return
     */
    String description() default "";


}

