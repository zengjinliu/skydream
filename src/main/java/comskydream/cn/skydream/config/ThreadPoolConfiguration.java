package comskydream.cn.skydream.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 并且开启异步注解，亦可以使用CompletableFuture做异步编排处理
 * @author Jayson
 * @date 2020/9/22 16:50
 */
@Configuration
@EnableAsync
public class ThreadPoolConfiguration {


    @Lazy(false)
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(300);
        executor.setDaemon(false);
        executor.setKeepAliveSeconds(3000);
        executor.setThreadNamePrefix("sky-dream-");
        executor.setRejectedExecutionHandler(newInstanceExecutionHandler());
        return executor;
    }

    private RejectedExecutionHandler newInstanceExecutionHandler() {
        //默认处理策略
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        try {

            Class<?> hander = Class.forName("java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy");
            Object object = hander.newInstance();
            if (object instanceof RejectedExecutionHandler) {
                rejectedExecutionHandler = (RejectedExecutionHandler) object;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rejectedExecutionHandler;
    }

}
