package comskydream.cn.skydream.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Jayson
 * @date 2020/9/5  18:11
 */
@Data
public class ResultJson<T> {

    private Integer code;

    private String msg;

    private T datas;

    public static <T> ResultJson<T> error() {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.value(),null,null);
    }
    public static <T> ResultJson<T> error(String msg) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.value(),msg,null);
    }

    public static <T> ResultJson<T> error(T data) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.value(),null,data);
    }

    public static <T> ResultJson<T> error(T data,String msg) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.value(),msg,data);
    }

    public static <T> ResultJson<T> error(Integer code,String msg) {
        return result(code,msg,null);
    }

    public static <T> ResultJson<T> success(T data) {
        return result(HttpStatus.OK.value(), null, data);
    }


    public static <T> ResultJson<T> success(T data, String msg) {
        return result(HttpStatus.OK.value(), msg, data);
    }


    public static <T> ResultJson<T> success() {
        return result(HttpStatus.OK.value(), null, null);
    }

    private static <T> ResultJson<T> result(Integer code, String msg, T data) {
        //没有数据，只返回状态码和消息
        ResultJson<T> resultJson = new ResultJson<T>();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        resultJson.setDatas(data);
        return resultJson;
    }


}
