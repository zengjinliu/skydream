package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.entity.SysRole;
import comskydream.cn.skydream.model.SysRoleVo;
import comskydream.cn.skydream.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


}