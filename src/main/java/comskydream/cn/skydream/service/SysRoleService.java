package comskydream.cn.skydream.service;

import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.model.SysRoleVo;

import java.util.List;

/**
 * @Description
 * @Author Jayson
 * @Date 2020/9/15 15:41
 */
public interface SysRoleService {

    /**
     * 分页
     * @param sysRoleVo
     * @return
     */
    ResultPage<List<SysRoleVo>> page(SysRoleVo sysRoleVo);

    /**
     * 新增
     * @param sysRoleVo
     */
    void save(SysRoleVo sysRoleVo);

    /**
     * 批量删除
     * @param roleId
     */
    void deleteById(List<String> roleId);

    /**
     * 根据主键查询
     * @param roleId
     * @return
     */
    SysRoleVo queryById(String roleId);

    /**
     * 更新角色
     * @param sysRoleVo
     */
    void update(SysRoleVo sysRoleVo);

    /**
     * 条件查询，并返回所有的菜单
     * @param sysRoleVo
     * @return
     */
    List<SysRoleVo> list(SysRoleVo sysRoleVo);
}
