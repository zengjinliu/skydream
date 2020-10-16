package comskydream.cn.skydream.aviator.example;

import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Jayson
 * @date 2020/10/10 16:33
 */
@Component
@Slf4j
public class SkyAviatorAsyncExecute {

    private final SkyCompileExample compileExample;

    public SkyAviatorAsyncExecute(SkyCompileExample compileExample) {
        this.compileExample = compileExample;
    }


    /**
     * 异步计算该表达式，并且确定返回String
     *
     * @param env 表达式
     * @param exp 值
     * @return 计算结果 String 类型值
     */
    @Async
    public Future<String> aviatorExecuteResultStr(final Map<String, Object> env, String exp) {
        Expression expression = compileExample.buildExpressionResultStr(exp);
        String debugInfo = "run %s expression ,script name %s ,env= %s";
        log.debug(String.format(debugInfo, exp, expression.toString(), env.toString()));
        String s = (String) expression.execute(env);
        return new AsyncResult<>(s);
    }

    /**
     * 异步计算该表达式，并且确定返回object
     *
     * @param env 表达式
     * @param exp 值
     * @return 计算结果 String 类型值
     */
    @Async
    public Future<Object> aviatorExecuteResult(final Map<String, Object> env, String exp) {
        System.out.println("当前线程名字:aviatorExecuteResult" + Thread.currentThread().getName());
        Expression expression = compileExample.buildExpression(exp);
        String debugInfo = "run %s expression ,script name %s ,env= %s";
        log.debug(String.format(debugInfo, exp, expression.toString(), env.toString()));
        Object o = expression.execute(env);
        return new AsyncResult<>(o);
    }


}
