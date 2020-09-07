package comskydream.cn.skydream.service.impl;

import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.mapper.SysUserMapper;
import comskydream.cn.skydream.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Jayson
 * @date 2020/9/5  22:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getOne(SysUser user) {
        return sysUserMapper.getOne(user);
    }

    @Override
    public List<SysUser> list(SysUser sysUser) {
        return sysUserMapper.list(sysUser);
    }

    @Override
    public Set<String> queryAllPermission(String userId) {
        //TODO 查询用户的所欲去权限
        return null;
    }

    @Override
    public List<String> queryAllMenuIds(String userId) {
        return sysUserMapper.getAllMenuIds(userId);
    }
}
