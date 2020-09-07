package comskydream.cn.skydream.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/7 16:48
 */
@Data
@Accessors(chain = true)
@Builder
public class SysMenuVo {
    private String menuId;

    private String parentId;

    private String parentName;

    private String name;

    private String url;

    private String perms;

    private int type;

    private String icon;

    private int orderNum;

    private String open;

    private List<String> permissions;

    private List<SysMenuVo> list;
}
