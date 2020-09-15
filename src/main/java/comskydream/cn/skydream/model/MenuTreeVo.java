package comskydream.cn.skydream.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/15 16:43
 */
@Data
@Accessors(chain = true)
public class MenuTreeVo {

    private String id;

    private String label;

    private List<MenuTreeVo> children;
}
