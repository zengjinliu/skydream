package comskydream.cn.skydream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author Jayson
 * @date 2020/10/10 18:12
 */
@Component
public class TaskTest {


    @Async("taskExecutor")
    public Future<Boolean> task(){
        System.out.println("当前现场名字" + Thread.currentThread().getName());
        int i = 1;
        int flag = i /1;
        return AsyncResult.forValue(i == flag);
    }

}
