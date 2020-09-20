package comskydream.cn.skydream.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jayson
 * @date 2020/9/20  18:05
 */
@Data
public class WeiBoDto implements Serializable {
    private static final long serialVersionUID = 5083641552399854777L;

    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;
}
