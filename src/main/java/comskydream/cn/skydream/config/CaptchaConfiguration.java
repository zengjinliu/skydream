package comskydream.cn.skydream.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Jayson
 * @date 2020/9/5  16:47
 */
@Configuration
public class CaptchaConfiguration {

    @Bean
    public Producer producer(){
        Properties properties = new Properties();
        properties.put("kaptcha.border","no");
        properties.put("kaptcha.textproducer.font.color","black");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.textproducer.char.space","5");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
