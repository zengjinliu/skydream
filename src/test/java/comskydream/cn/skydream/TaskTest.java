package comskydream.cn.skydream;

import comskydream.cn.skydream.aviator.example.SkyAviatorAsyncExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Jayson
 * @date 2020/10/10 18:12
 */
@Component
public class TaskTest {

    @Autowired
    private SkyAviatorAsyncExecute aviatorAsyncExecute;


    @Async("taskExecutor")
    public Future<Boolean> task(){
        System.out.println("当前现场名字" + Thread.currentThread().getName());
        int i = 1;
        int flag = i /1;
        return AsyncResult.forValue(i == flag);
    }

    @Async("taskExecutor")
    public Future<Object> task1() throws Exception{
        System.out.println("当前线程名字:task1-->" + Thread.currentThread().getName());
        //测试自定义表达式成功
        Map<String, Object> env = new HashMap<>();
        env.put("a", 1);
        env.put("b", 3);
        //支持嵌套函数
        String exp = "add(sub(sub(a,b),b),b)";
        Future<Object> task = aviatorAsyncExecute.aviatorExecuteResult(env, exp);
        Object value = task.get(2, TimeUnit.MINUTES);
        TimeUnit.SECONDS.sleep(3);
        //TODO 在用一个类中调用自己内部的异步方法会失效，应为是代理模式，为当前类生成了代理类，
        // 类似于springboot中@transactional注解失效一样。需要重新生成新的代理类才会生效
        this.task3();
        return AsyncResult.forValue(value);
    }

    @Async("taskExecutor")
    public void task3(){
        System.out.println("当前线程名字:task3-->" + Thread.currentThread().getName());
    }

}

