package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.model.MenuTreeVo;
import comskydream.cn.skydream.model.SysMenuVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/7 16:47
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/getNavList")
    public ResultJson<List<SysMenu>> getNavList(){
        List<SysMenu> sysMenus = sysMenuService.queryAllMenu(SysUserUtils.getUserId());
        return ResultJson.success(sysMenus);
    }

    @RequestMapping("/getTreeMenu")
    public ResultJson<List<SysMenu>> getTreeMenu(){
       List<SysMenu> vos = sysMenuService.queryTreeMenu();
        return ResultJson.success(vos);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultJson add(@RequestBody SysMenuVo sysMenuVo){

        return ResultJson.success();
    }

}
