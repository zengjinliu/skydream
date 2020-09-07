package comskydream.cn.skydream.service;

import comskydream.cn.skydream.model.SysMenuVo;
import java.util.List;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/7 17:21
 */
public interface SysMenuService {


    /**
     * 根据用户角色查询出所有的菜单以及权限
     * @param userId
     * @return
     */
    List<SysMenuVo> queryAllMenuId(String userId);

}
