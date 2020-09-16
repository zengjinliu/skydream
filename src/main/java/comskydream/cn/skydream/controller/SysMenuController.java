package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.entity.SysMenu;
import comskydream.cn.skydream.model.SysMenuVo;
import comskydream.cn.skydream.service.SysMenuService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultJson<List<SysMenu>> getNavList() {
        List<SysMenu> sysMenus = sysMenuService.queryAllMenu(SysUserUtils.getUserId());
        return ResultJson.success(sysMenus);
    }

    @RequestMapping("/getTreeMenu")
    public ResultJson<List<SysMenu>> getTreeMenu() {
        List<SysMenu> vos = sysMenuService.queryTreeMenu();
        return ResultJson.success(vos);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultJson add(@RequestBody SysMenuVo sysMenuVo) {
        sysMenuService.save(sysMenuVo);
        return ResultJson.success();
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultJson del(@RequestParam("menuId") String menuId) {
        Boolean flag = sysMenuService.deleteById(menuId);
        return flag ? ResultJson.success() : ResultJson.error("请先删除子菜单");
    }

    @RequestMapping(value = "/queryById",method = RequestMethod.GET)
    public ResultJson queryById(@RequestParam("menuId")String menuId){
        SysMenuVo sysMenuVo = sysMenuService.selectById(menuId);
        return ResultJson.success(sysMenuVo);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultJson update(@RequestBody SysMenuVo sysMenuVo){
        sysMenuService.update(sysMenuVo);
        return ResultJson.success();
    }

}
