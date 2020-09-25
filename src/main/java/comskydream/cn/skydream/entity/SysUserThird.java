package comskydream.cn.skydream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Jayson
 * @date 2020/9/25 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SysUserThird {
    private String id;

    private String uid;

    private String accessToken;

    private String name;

    private String pic;

    private Long expiresIn;

    private String userId;

}
