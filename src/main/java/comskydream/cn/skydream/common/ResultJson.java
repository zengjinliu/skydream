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

    public static ResultJson error(Integer code,String msg){
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static ResultJson error(String msg){
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static <T> ResultJson<T> success(String msg){
        ResultJson<T>  resultJson = new  ResultJson<T> ();
        resultJson.setCode(HttpStatus.OK.value());
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static <T> ResultJson<T> success(String msg,T data){
        ResultJson<T>  resultJson = new  ResultJson<T> ();
        resultJson.setCode(HttpStatus.OK.value());
        resultJson.setMsg(msg);
        resultJson.setDatas(data);
        return resultJson;
    }
    public static <T> ResultJson<T> success(Integer code,String msg){
        ResultJson<T>  resultJson = new  ResultJson<T> ();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        return resultJson;
    }
    public static <T> ResultJson<T> success(Integer code,String msg,T data){
        ResultJson<T> resultJson = success(code, msg);
        resultJson.setDatas(data);
        return resultJson;
    }

}
