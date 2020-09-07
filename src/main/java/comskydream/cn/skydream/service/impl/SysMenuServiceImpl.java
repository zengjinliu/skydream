package comskydream.cn.skydream.service.impl;

import comskydream.cn.skydream.common.SysConstamt;
import comskydream.cn.skydream.mapper.SysMenuMapper;
import comskydream.cn.skydream.model.SysMenuVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/7 17:24
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenuVo> queryAllMenuId(String userId) {
        if(SysConstamt.ADMIN.equals(userId)) {
            return getAllMenus(null);
        }
        //查出用户角色所对应的菜单id
        List<String> menuIds = sysUserService.queryAllMenuIds(userId);
        return getAllMenus(menuIds);
    }

    private List<SysMenuVo> getAllMenus(@Nullable List<String> menuId){

        //先查出所有parentid为0的菜单
        //递归查询

        return null;
    }

}
