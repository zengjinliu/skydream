package comskydream.cn.skydream.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.converter.SysRoleConverter;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.entity.SysRoleMenu;
import comskydream.cn.skydream.mapper.SysRoleMapper;
import comskydream.cn.skydream.mapper.SysRoleMenuMapper;
import comskydream.cn.skydream.model.SysRoleVo;
import comskydream.cn.skydream.service.SysRoleService;
import comskydream.cn.skydream.utils.SysUserUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jayson
 * @date 2020/9/15 15:42
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleConverter roleConverter;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public ResultPage<List<SysRoleVo>> page(SysRoleVo sysRoleVo) {
        SysRole sysRole = roleConverter.toPo(sysRoleVo);
        PageHelper.startPage(sysRoleVo.getPage(), sysRoleVo.getRows());
        List<SysRole> list = sysRoleMapper.list(sysRole);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return roleConverter.toPageVo(pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysRoleVo sysRoleVo) {
        SysRole sysRole = roleConverter.toPo(sysRoleVo);
        sysRole.setRoleId(UuidUtils.id()).setCreateTime(LocalDateTime.now())
                .setCreateUserId(SysUserUtils.getUserId());
        this.sysRoleMapper.insertSelective(sysRole);
        //插入角色-菜单表
        List<SysRoleMenu> list = this.build(sysRole.getRoleId(), sysRoleVo.getMenuIds());
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(sysRoleMenuMapper::insertSelective);
        }
    }


    private List<SysRoleMenu> build(String roleId, List<String> menuIds) {
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<SysRoleMenu> collect = menuIds.stream().map(e -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setId(UuidUtils.id()).setMenuId(e)
                        .setRoleId(roleId);
                return sysRoleMenu;
            }).collect(Collectors.toList());
            return collect;
        }
        return null;
    }
}
