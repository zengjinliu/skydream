package comskydream.cn.skydream.service;

import comskydream.cn.skydream.entity.SysMenu;
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
    List<SysMenu> queryAllMenu(String userId);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     * @return
     */
    List<SysMenu> queryListByParentId(String parentId, List<String> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @return
     */
    List<SysMenu> queryMenuByParentId(String parentId);

}
