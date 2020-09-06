package comskydream.cn.skydream.service;

import comskydream.cn.skydream.entity.SysUser;

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

}
