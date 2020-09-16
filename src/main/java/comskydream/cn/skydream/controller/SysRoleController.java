package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.model.SysRoleVo;
import comskydream.cn.skydream.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/15 15:34
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public ResultPage page(@RequestBody SysRoleVo sysRole){
        ResultPage<List<SysRoleVo>> page = sysRoleService.page(sysRole);
        return page;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultJson add(@RequestBody SysRoleVo sysRoleVo){
        sysRoleService.save(sysRoleVo);
        return ResultJson.success();
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultJson del(@RequestBody List<String> roleId){
        sysRoleService.deleteById(roleId);
        return ResultJson.success();
    }

    @RequestMapping(value = "/queryById",method = RequestMethod.GET)
    public ResultJson queryById(@RequestParam("roleId")String roleId){
        SysRoleVo vo = sysRoleService.queryById(roleId);
        return ResultJson.success(vo);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultJson update(@RequestBody SysRoleVo sysRoleVo){
        sysRoleService.update(sysRoleVo);
        return ResultJson.success();
    }


}
