package comskydream.cn.skydream.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;

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

   @DurationFormat(value = DurationStyle.ISO8601)
    private String name;

    private String token;

    /**
     * 第三方登陆对象
     */
    private T t;

}
