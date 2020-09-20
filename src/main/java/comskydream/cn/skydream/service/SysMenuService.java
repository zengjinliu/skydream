package comskydream.cn.skydream.service;

import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.model.vo.SysMenuVo;

import java.util.List;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/7 17:21
 */
public interface SysMenuService {


    /**
     * 根据用户角色查询出所有的菜单以及权限，只限菜但，没有操作按钮
     * @param userId
     * @return
     */
    List<SysMenu> queryAllMenu(String userId);

    /**
     * 查询所有的菜单
     * @return
     */
    List<SysMenu> queryTreeMenu();

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

    /**
     * 条件查询
     * @param sysMenu
     * @return
     */
    List<SysMenu> queryList(SysMenu sysMenu);

    /**
     * 删除菜单,如果有下级菜单则不能删除，应先删除下级菜单
     * @param menuId
     * @return true 删除成功， false删除失败
     */
    Boolean deleteById(String menuId);

    /**
     * 新增菜单
     * @param sysMenuVo
     */
    void save(SysMenuVo sysMenuVo);

    /**
     * 根据主键查询
     * @param menuId
     * @return
     */
    SysMenuVo selectById(String menuId);

    /**
     * 更新
     * @param sysMenuVo
     */
    void update(SysMenuVo sysMenuVo);
}
