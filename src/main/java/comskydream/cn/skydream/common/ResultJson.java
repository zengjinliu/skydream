package comskydream.cn.skydream.common;

import lombok.Data;

/**
 * @author Jayson
 * @date 2020/9/5  18:11
 */
@Data
public class ResultJson<T> {

    private Integer code;

    private String msg;

    private T datas;

}
