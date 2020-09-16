package comskydream.cn.skydream.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.constant.SysConstant;
import comskydream.cn.skydream.converter.SysUserConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.entity.SysUserRole;
import comskydream.cn.skydream.mapper.SysUserMapper;
import comskydream.cn.skydream.mapper.SysUserRoleMapper;
import comskydream.cn.skydream.model.PasswordVo;
import comskydream.cn.skydream.model.SysUserVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.utils.SysUserUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jayson
 * @date 2020/9/5  22:00
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserConverter userConverter;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleMapper userRoleMapper;


    @Override
    public ResultPage<List<SysUserVo>> pageList(SysUserVo sysUserVo) {
        SysUser sysUser = userConverter.toPo(sysUserVo);
        PageHelper.startPage(sysUserVo.getPage(),sysUserVo.getRows());
        List<SysUser> list = sysUserMapper.list(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return userConverter.toPageVo(pageInfo);
    }

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(SysUserVo sysUser) {
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setUserId(UuidUtils.id()).setCreateTime(LocalDateTime.now())
                .setPassword(new Sha256Hash(sysUser.getPassword(),salt).toHex());
        SysUser po = userConverter.toPo(sysUser);
        po.setSalt(salt).setCreateBy(SysUserUtils.getUserId());
        //保存用户与角色的关系
        List<SysUserRole> build = this.build(po.getUserId(), sysUser.getRoleIds());
        if(!CollectionUtils.isEmpty(build)){
            build.forEach(userRoleMapper::insertSelective);
        }
        return sysUserMapper.insertSelective(po);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> userIds) {
        userIds.forEach(sysUserMapper::deleteByPrimaryKey);
        //删除用户与角色的关系
        userIds.forEach(userRoleMapper::deleteByUserId);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(SysUserVo sysUser) {
        SysUser po = userConverter.toPo(sysUser);
        //更新用户与角色的关系
        userRoleMapper.deleteByUserId(sysUser.getUserId());
        List<SysUserRole> list = this.build(sysUser.getUserId(), sysUser.getRoleIds());
        if(CollectionUtils.isEmpty(list)){
            list.forEach(userRoleMapper::insertSelective);
        }
        return sysUserMapper.updateByPrimaryKeySelective(po);
    }

    @Override
    public Boolean checkNameExist(String username) {
        SysUser user = this.getOne(SysUser.builder().username(username).build());
        return user != null;
    }

    private List<SysUserRole> build(String userId,List<String> roleIds){
        if(!CollectionUtils.isEmpty(roleIds)){
            List<SysUserRole> list = roleIds.stream().map(e -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setId(UuidUtils.id()).setRoleId(e).setUserId(userId);
                return sysUserRole;
            }).collect(Collectors.toList());
            return list;
        }
        return null;
    }
}
