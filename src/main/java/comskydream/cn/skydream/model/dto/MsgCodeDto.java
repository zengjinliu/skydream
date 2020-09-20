package comskydream.cn.skydream.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jayson
 * @date 2020/9/20  0:39
 */
@Data
@Builder
public class MsgCodeDto implements Serializable {


    private static final long serialVersionUID = -2938908716461981718L;

    /**生成的验证码*/
    private String msgCode;

    /**存入redis时间*/
    private Long time;
}
