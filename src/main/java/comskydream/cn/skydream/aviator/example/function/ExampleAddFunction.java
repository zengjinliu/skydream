package comskydream.cn.skydream.aviator.example.function;

import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import comskydream.cn.skydream.annotation.SkyAviatorFunction;
import comskydream.cn.skydream.utils.DoubleCalUtils;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * @author Jayson
 * @date 2020/10/10 14:49
 */
@SkyAviatorFunction(value = "add",description = "测试第一个自定义函数是否生效")
@Scope
public class ExampleAddFunction extends AbstractSkyFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject d1,AviatorObject d2) {
        System.out.println("--------------------->add function test");
        double a = d2.numberValue(env).doubleValue();
        double b = d1.numberValue(env).doubleValue();
        Double add = DoubleCalUtils.add(a, b);
        return AviatorDouble.valueOf(add);
    }
}
