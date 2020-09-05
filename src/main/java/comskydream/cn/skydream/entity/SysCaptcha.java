package comskydream.cn.skydream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统验证码
 * @author Jayson
 * @date 2020/9/5  16:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SysCaptcha {
    /**主键*/
    private String uuid;

    /**验证码*/
    private String code;

    /**过期时间*/
    private LocalDateTime expireTime;

}
