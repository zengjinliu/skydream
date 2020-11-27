package comskydream.cn.skydream.config;

import comskydream.cn.skydream.security.auth.AuthRealm;
import comskydream.cn.skydream.security.auth.ShiroFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/5  21:04
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public SecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager bean = new DefaultWebSecurityManager();
        bean.setRealm(authRealm);
        bean.setRememberMeManager(null);
        return bean;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new HashMap<>(4);
        filters.put("auth", new ShiroFilter());
        bean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/captcha", "anon");
        filterChainDefinitionMap.put("/msg/requireCode", "anon");
        filterChainDefinitionMap.put("/msg/login", "anon");
        filterChainDefinitionMap.put("/third/weibo/success", "anon");
        filterChainDefinitionMap.put("/docker", "anon");
        filterChainDefinitionMap.put("/**", "auth");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        //开启注解通知
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
