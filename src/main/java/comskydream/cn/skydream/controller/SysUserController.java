package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.model.PasswordVo;
import comskydream.cn.skydream.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayson
 * @date 2020/9/8  23:24
 */
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
    public ResultJson updatePwd(@RequestBody PasswordVo passwordVo){
        //更新密码
        sysUserService.updatePwd(passwordVo);
        return ResultJson.success();
    }

}
