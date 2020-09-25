package comskydream.cn.skydream.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Jayson
 * @date 2020/9/25 11:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ComprehensiveVo<T> implements Serializable {

    private static final long serialVersionUID = 1023021334894440278L;

    private String userId;

    private String username;

    private String name;

    private String token;

    /**
     * 第三方登陆对象
     */
    private T t;

}
