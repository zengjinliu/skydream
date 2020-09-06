package comskydream.cn.skydream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统用户Token
 *
 * @author Jayson
 * @date 2020/09/05 22:07:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SysUserToken {
    /**
     * 
     */
    private String userId;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}