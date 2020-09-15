package comskydream.cn.skydream.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.converter.SysRoleConverter;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.mapper.SysRoleMapper;
import comskydream.cn.skydream.model.SysRoleVo;
import comskydream.cn.skydream.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public ResultPage<List<SysRoleVo>> page(SysRoleVo sysRoleVo) {
        SysRole sysRole = roleConverter.toPo(sysRoleVo);
        PageHelper.startPage(sysRoleVo.getPage(),sysRoleVo.getRows());
        List<SysRole> list = sysRoleMapper.list(sysRole);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return roleConverter.toPageVo(pageInfo);
    }
}
