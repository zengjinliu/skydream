package comskydream.cn.skydream.mapper;


import comskydream.cn.skydream.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    /**
     * 通过主键删除
     * @param menuId 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String menuId);


    /**
     * 新增数据插入有数据的列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insertSelective(SysMenu record);

    /**
     * 通过主键查询
     * @param menuId 主键id
     * @return 主键符合的唯一Po记录
     */
    SysMenu selectByPrimaryKey(String menuId);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysMenu record);


    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryParentId(String parentId);

    /**
     * 查询所有的根目录
     * @return
     */
    List<SysMenu> queryRoots();

    /**
     * 条件查询
     * @param sysMenu
     * @return
     */
    List<SysMenu> list(SysMenu sysMenu);
}