package comskydream.cn.skydream.aviator.example;

import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Jayson
 * @date 2020/10/10 14:40
 */
@Component
public class SkyCompileExample {

    private final AviatorEvaluatorInstance aviatorEvaluatorInstance;

    protected SkyCompileExample(@Qualifier("aviatorEvaluatorInstance") AviatorEvaluatorInstance aviatorEvaluatorInstance) {
        this.aviatorEvaluatorInstance = aviatorEvaluatorInstance;
    }

    /**
     * 建立表达式对象，该表达式会缓存
     *
     * @param expression 表达式
     * @return Expression 编译后的表达式对象
     */
    protected Expression buildExpression(String expression) {
        Expression compiledExp = aviatorEvaluatorInstance.compile(expression, true);
        return compiledExp;

    }

    /**
     * 建立表达式对象，该表达式会缓存
     *
     * @param expression 表达式
     * @return Expression 编译后的表达式对象,该对象计算的结果为string类型
     */
    protected Expression buildExpressionResultStr(String expression) {
        StringBuffer sb = new StringBuffer("str(");
        sb.append(expression);
        sb.append(")");
        return buildExpression(sb.toString());
    }
}
