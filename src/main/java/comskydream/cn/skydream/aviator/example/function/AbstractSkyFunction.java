package comskydream.cn.skydream.aviator.example.function;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import org.springframework.util.Assert;

/**
 * @author Jayson
 * @date 2020/10/10 14:36
 */
public abstract class AbstractSkyFunction extends AbstractFunction {
    /**
     * 函数名称
     */
    private String name;


    protected AbstractSkyFunction(String name){
        Assert.notNull(name,"null name");
        this.name = name;
    }
    protected AbstractSkyFunction(){}


    /**
     * 设置函数名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 实现getName，
     * @return
     */
    @Override
    public String getName(){
        return name;
    }
}
