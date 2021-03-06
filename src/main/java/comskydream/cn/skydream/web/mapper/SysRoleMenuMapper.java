package comskydream.cn.skydream.web.mapper;

import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuMapper {
    /**
     * 通过主键删除
     * @param id 主键id
     * @return 删除成功数量
     */
    int deleteByPrimaryKey(String id);



    /**
     * 新增数据插入有数据的列
     * @param record 新增记录参数
     * @return 新增成功数量
     */
    int insertSelective(SysRoleMenu record);

    /**
     * 通过主键查询
     * @param id 主键id
     * @return 主键符合的唯一Po记录
     */
    SysRoleMenu selectByPrimaryKey(String id);

    /**
     * 通过主键修改有数据的列
     * @param record 修改记录参数
     * @return 修改成功数量
     */
    int updateByPrimaryKeySelective(SysRoleMenu record);

    /**
     * 根据角色id删除数据
     * @param roleId
     * @return
     */
    int deleteByRoleId(String roleId);

    /**
     * 根据角色id查询所有的菜单id
     * @param roleId
     * @return
     */
    List<String> selectByRoleId(String roleId);

    /**
     * 根据菜单id删除数据
     * @param menuId
     */
    void deleteByMenuId(String menuId);
}