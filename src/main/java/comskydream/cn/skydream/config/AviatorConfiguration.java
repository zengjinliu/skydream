package comskydream.cn.skydream.config;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.spring.SpringContextFunctionLoader;
import comskydream.cn.skydream.utils.DoubleCalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 计算规则引擎交给spring管理
 * @author Jayson
 * @date 2020/10/10 8:54
 */
@Configuration
public class AviatorConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    private SpringContextFunctionLoader springContextFunctionLoader(){
        return new SpringContextFunctionLoader(applicationContext);
    }

    @Bean("aviatorEvaluatorInstance")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AviatorEvaluatorInstance aviatorEvaluatorInstance() throws NoSuchMethodException, IllegalAccessException {
        AviatorEvaluatorInstance instance = AviatorEvaluator.newInstance();
        instance.addFunctionLoader(springContextFunctionLoader());
//        instance.addStaticFunctions("dao", DoubleCalUtils.class);
        return instance;
    }

}
