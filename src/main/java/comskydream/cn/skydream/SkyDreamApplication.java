package comskydream.cn.skydream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("comskydream.cn.skydream.mapper")
public class SkyDreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyDreamApplication.class, args);
    }

}
