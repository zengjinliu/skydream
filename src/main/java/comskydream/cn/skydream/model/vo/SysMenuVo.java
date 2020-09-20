package comskydream.cn.skydream.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/7 16:48
 */
@Data
@Accessors(chain = true)
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

    private List<SysMenuVo> childs;
}
