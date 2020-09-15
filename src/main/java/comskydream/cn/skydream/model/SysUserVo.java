package comskydream.cn.skydream.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import comskydream.cn.skydream.common.PageModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/14 14:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SysUserVo extends PageModel {

    private String userId;

    private String username;

    private String password;

    private String phone;

    private String pic;

    private List<String> roles;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

}
