package comskydream.cn.skydream.config;

import comskydream.cn.skydream.security.ParamEncodeProperty;
import comskydream.cn.skydream.security.RequestDecryptFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Jayson
 * @date 2020/11/25 8:53
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private final Environment env;

    public MvcConfiguration(Environment env) {
        this.env = env;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        registration.setFilter(new RequestDecryptFilter());
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public ParamEncodeProperty paramEncodeProperty(){
        String rsaPrivateKey = env.getProperty("web.filter.rsaPrivateKey");
        boolean isEncode = env.getProperty("web.filter.isEncode", Boolean.TYPE);
        String privilegeURI = env.getProperty("web.filter.privilegeURIs");
        ParamEncodeProperty property = new ParamEncodeProperty();
        property.setEncodeType(isEncode);
        property.setPrivilegeURI(privilegeURI);
        property.setRsaPrivateKey(rsaPrivateKey);
        return property;
    }
}
