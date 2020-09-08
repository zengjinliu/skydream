package comskydream.cn.skydream.service;

import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.PasswordVo;

import java.util.List;
import java.util.Set;

/**
 * @author Jayson
 * @date 2020/9/5  21:58
 */
public interface SysUserService {

    /**
     * 条件查找用户返回单个用户
     * @param sysUser
     * @return
     */
    SysUser getOne(SysUser sysUser);

    /**
     * 条件查询返会多个
     * @param sysUser
     * @return
     */
    List<SysUser> list(SysUser sysUser);

    /**
     * 查询用户的所有权限
     * @param userId
     * @return
     */
    Set<String> queryAllPermission(String userId);

    /**
     * 查询用户所有的菜单Id
     * @param userId 用户id
     * @return
     */
    List<String> queryAllMenuIds(String userId);

    /**
     * 更新密码
     * @param passwordVo
     */
    void updatePwd(PasswordVo passwordVo);
}
