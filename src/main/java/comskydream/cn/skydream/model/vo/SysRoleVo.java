package comskydream.cn.skydream.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import comskydream.cn.skydream.common.PageModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/15 15:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysRoleVo extends PageModel {

    private String roleId;

    private String roleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    private String remark;

    private List<String> menuIds;
}
