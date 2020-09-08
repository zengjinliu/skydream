package comskydream.cn.skydream.service.impl;

import comskydream.cn.skydream.constant.SysConstant;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.mapper.SysUserMapper;
import comskydream.cn.skydream.model.PasswordVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Jayson
 * @date 2020/9/5  22:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuService sysMenuService;

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
        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId.equals(SysConstant.ADMIN)){
            List<SysMenu> menuList = sysMenuService.queryList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public List<String> queryAllMenuIds(String userId) {
        return sysUserMapper.getAllMenuIds(userId);
    }

    @Override
    public void updatePwd(PasswordVo passwordVo) {
        SysUser user = SysUserUtils.getUser();
        String npwd = new Sha256Hash(passwordVo.getNpwd(),user.getSalt()).toHex();
        SysUser sysUser = SysUser.builder().userId(user.getUserId()).password(npwd).build();
        this.sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }
}
