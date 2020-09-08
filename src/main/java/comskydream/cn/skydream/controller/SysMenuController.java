package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/7 16:47
 */
@RestController
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/getNavList")
    public ResultJson<List<SysMenu>> getNavList(){
        List<SysMenu> sysMenus = sysMenuService.queryAllMenu(SysUserUtils.getUserId());
        return ResultJson.success(sysMenus);
    }



}
