package comskydream.cn.skydream.aviator.example.function;

import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import comskydream.cn.skydream.annotation.SkyAviatorFunction;
import comskydream.cn.skydream.utils.DoubleCalUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Jayson
 * @date 2020/10/10 17:33
 */

@SkyAviatorFunction(value = "sub",description = "测试自定义函数中在套一个")
@Scope
public class ExampleSubFunction extends AbstractSkyFunction{


    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject d1, AviatorObject d2) {
        double a = d1.numberValue(env).doubleValue();
        double b = d2.numberValue(env).doubleValue();
        Double sub = DoubleCalUtils.sub(a, b);
        return AviatorDouble.valueOf(sub);
    }
}


