package comskydream.cn.skydream.service.impl;

import comskydream.cn.skydream.constant.SysConstant;
import comskydream.cn.skydream.converter.SysMenuConverter;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.mapper.SysMenuMapper;
import comskydream.cn.skydream.mapper.SysRoleMenuMapper;
import comskydream.cn.skydream.model.vo.SysMenuVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.utils.SysUserUtils;
import comskydream.cn.skydream.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import reactor.util.annotation.Nullable;

import java.util.ArrayList;
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
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysMenuConverter menuConverter;

    @Override
    public List<SysMenu> queryAllMenu(String userId) {
        if(SysConstant.ADMIN.equals(userId)) {
            return getAllMenus(null);
        }
        //查出用户角色所对应的菜单id
        List<String> menuIds = sysUserService.queryAllMenuIds(userId);
        return getAllMenus(menuIds);
    }

    @Override
    public List<SysMenu> queryTreeMenu() {
        //有待改进 TODO
        List<SysMenu> sysMenus = this.queryAllMenu(SysUserUtils.getUserId());
        List<SysMenu> tree = this.tree(sysMenus);
        return tree;
    }


    @Override
    public List<SysMenu> queryMenuByParentId(String parentId) {
        return sysMenuMapper.queryParentId(parentId);
    }

    @Override
    public List<SysMenu> queryList(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuMapper.list(sysMenu);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(String menuId) {
        List<SysMenu> sysMenus = sysMenuMapper.queryParentId(menuId);
        if(!CollectionUtils.isEmpty(sysMenus)){
            return false;
        }
        //删除菜单
        this.sysMenuMapper.deleteByPrimaryKey(menuId);
        //删除角色菜单表
        roleMenuMapper.deleteByMenuId(menuId);
        return true;
    }

    @Override
    public void save(SysMenuVo sysMenuVo) {
        //新增菜单
        SysMenu menu = menuConverter.toPo(sysMenuVo);
        menu.setMenuId(UuidUtils.id());
        this.sysMenuMapper.insertSelective(menu);
    }

    @Override
    public SysMenuVo selectById(String menuId) {
        SysMenu menu = this.sysMenuMapper.selectByPrimaryKey(menuId);
        SysMenuVo sysMenuVo = menuConverter.toVo(menu);
        return sysMenuVo;
    }

    @Override
    public void update(SysMenuVo sysMenuVo) {
        SysMenu menu = menuConverter.toPo(sysMenuVo);
        this.sysMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public List<SysMenu> queryListByParentId(String parentId, List<String> menuIdList) {
        List<SysMenu> menuList = queryMenuByParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }
        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    private List<SysMenu> getAllMenus(@Nullable List<String> menuIds){
        //查询根菜单列表
        List<SysMenu> menuList = this.queryListByParentId("0", menuIds);
        //递归获取子菜单
        this.getMenuTreeList(menuList, menuIds);
        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menus, List<String> menuIds){
        List<SysMenu> subMenuList = new ArrayList<>();
        menus.forEach(e ->{
            if(SysConstant.CATALOG.equals(e.getType())){
                e.setChilds(getMenuTreeList(queryListByParentId(e.getMenuId(), menuIds), menuIds));
            }
            subMenuList.add(e);
        });
        return subMenuList;
    }

    private List<SysMenu> tree(List<SysMenu> list){
        for (SysMenu menu : list) {
            List<SysMenu> sysMenus = sysMenuMapper.queryParentId(menu.getMenuId());
            if(!CollectionUtils.isEmpty(sysMenus)){
                menu.setChilds(sysMenus);
                this.tree(sysMenus);
            }
        }
        return list;
    }


}
