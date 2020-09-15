package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.PasswordVo;
import comskydream.cn.skydream.model.SysUserVo;
import comskydream.cn.skydream.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jayson
 * @date 2020/9/8  23:24
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public ResultPage page(@RequestBody SysUserVo sysUserVo){
        //分页查询
        ResultPage<List<SysUserVo>> list = sysUserService.pageList(sysUserVo);
        return list;
    }

    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public ResultJson updatePwd(@RequestBody PasswordVo passwordVo){
        //更新密码
        sysUserService.updatePwd(passwordVo);
        return ResultJson.success();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultJson add(@RequestBody SysUserVo sysUser){
        sysUserService.save(sysUser);
        return ResultJson.success();
    }

    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public ResultJson del(@RequestBody List<String> userIds){
        sysUserService.delete(userIds);
        return ResultJson.success();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultJson update(@RequestBody SysUserVo sysUser){
        sysUserService.update(sysUser);
        return ResultJson.success();
    }

    @RequestMapping(value = "/checkNameExist",method = RequestMethod.GET)
    public ResultJson<Boolean> checkNameExist(@RequestParam("username")String username){
        Boolean flag = sysUserService.checkNameExist(username);
        return ResultJson.success(flag);
    }

    @RequestMapping(value = "/queryById",method = RequestMethod.GET)
    public ResultJson<SysUser> queryById(@RequestParam("userId") String userId){
        SysUser user = sysUserService.getOne(SysUser.builder().userId(userId).build());
        return ResultJson.success(user);
    }

}
