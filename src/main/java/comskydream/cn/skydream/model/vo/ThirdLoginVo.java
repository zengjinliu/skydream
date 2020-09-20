package comskydream.cn.skydream.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Jayson
 * @date 2020/9/20  20:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ThirdLoginVo {

    /**系统用户名*/
    private String username;

    /**系统用户id*/
    private String userId;

    /**token*/
    private String token;

    /**第三方用户名*/
    private String thirdName;
}
