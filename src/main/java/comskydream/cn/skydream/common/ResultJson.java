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
    public static <T> ResultJson<T> success(T data){
        ResultJson<T>  resultJson = new  ResultJson<T> ();
        resultJson.setCode(HttpStatus.OK.value());
        resultJson.setMsg(HttpStatus.OK.getReasonPhrase());
        resultJson.setDatas(data);
        return resultJson;
    }
    public static <T> ResultJson<T> success(Integer code,String msg){
        //没有数据，只返回状态码和消息
        ResultJson<T>  resultJson = new  ResultJson<T> ();
        resultJson.setCode(code);
        resultJson.setMsg(msg);
        return resultJson;
    }


}
