package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.LoginUserFormVo;
import comskydream.cn.skydream.model.SysUserVo;
import comskydream.cn.skydream.service.SysCaptchaService;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.service.SysUserTokenService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Jayson
 * @date 2020/9/5  16:45
 */
@RestController
public class SysLoginController {


    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse res, @RequestParam("uuid") String uuid) throws IOException {
        res.setHeader("Cache-Control", "no-store, no-cache");
        res.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = res.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultJson doLogin(@RequestBody LoginUserFormVo formVo){
        //验证码校验
        boolean validate = sysCaptchaService.validate(formVo.getUuid(), formVo.getCaptcha());
        if(!validate){
            return  ResultJson.error("验证码错误");
        }
        //校验用户信息
        SysUser user = sysUserService.getOne(SysUser.builder().username(formVo.getUsername()).build());
        if(user == null || !user.getPassword().equals(new Sha256Hash(formVo.getPassword(), user.getSalt()).toHex())) {
            return ResultJson.error("账号或密码不正确");
        }
        //生成token 并存入数据库(也可以存入第三方缓存数据库redis)
        String token = sysUserTokenService.createToken(user.getUserId());
        return ResultJson.success(token);
    }

    @RequestMapping("/logout")
    public ResultJson logout(){
        sysUserTokenService.logout(SysUserUtils.getUserId());
        return ResultJson.success("ok");
    }

    /**
     * 短信登陆接口
     * @param formVo
     * @return
     */
    @RequestMapping(value = "/msg/login",method = RequestMethod.POST)
    public ResultJson doMsgLogin(@RequestBody LoginUserFormVo formVo){
        SysUserVo sysUserVo = sysUserService.msgLogin(formVo.getPhone(), formVo.getMsgCode());
        //生成token 并存入数据库(也可以存入第三方缓存数据库redis)
        String token = sysUserTokenService.createToken(sysUserVo.getUserId());
        return ResultJson.success(sysUserVo);
    }

}
