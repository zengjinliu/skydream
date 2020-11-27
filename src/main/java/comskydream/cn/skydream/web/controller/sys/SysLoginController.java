package comskydream.cn.skydream.web.controller.sys;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.vo.LoginUserFormVo;
import comskydream.cn.skydream.model.vo.SysUserVo;
import comskydream.cn.skydream.web.service.sys.SysCaptchaService;
import comskydream.cn.skydream.web.service.sys.SysUserService;
import comskydream.cn.skydream.web.service.sys.SysUserTokenService;
import comskydream.cn.skydream.utils.SysUserUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    public ResultJson<SysUserVo> doLogin(@RequestParam LoginUserFormVo formVo){
        SysUserVo vo = new SysUserVo();
        //验证码校验
        boolean validate = sysCaptchaService.validate(formVo.getUuid(), formVo.getCaptcha());
        if(!validate){
            return  ResultJson.error("验证码错误");
        }
        //校验用户信息
        SysUser user = sysUserService.getOne(SysUser.builder().username(formVo.getUsername()).build());
//        if(user == null || !user.getPassword().equals(new Sha256Hash(formVo.getPassword(), user.getSalt()).toHex())) {
//            return ResultJson.error("账号或密码不正确");
//        }
        //生成token 并存入数据库(也可以存入第三方缓存数据库redis)
        String token = sysUserTokenService.createToken(user.getUserId());
        BeanUtils.copyProperties(user,vo);
        vo.setToken(token);
        return ResultJson.success(vo);
    }

    @RequestMapping("/logout")
    public ResultJson logout(){
        sysUserTokenService.logout(SysUserUtils.getUserId());
        return ResultJson.success();
    }

    /**
     * 短信登陆接口
     * @param formVo
     * @return
     */
    @RequestMapping(value = "/msg/login",method = RequestMethod.POST)
    public ResultJson doMsgLogin(@RequestBody LoginUserFormVo formVo){
        ResultJson resultJson = sysUserService.msgLogin(formVo.getPhone(), formVo.getMsgCode());
        return resultJson;
    }

    /**
     * 请求验证码
     * @return
     */
    @RequestMapping(value = "/msg/requireCode",method = RequestMethod.POST)
    public ResultJson requireCode(@RequestBody LoginUserFormVo formVo){
        Boolean flag = sysUserService.sendMsgCode(formVo.getPhone());
        return flag? ResultJson.success():ResultJson.error("请求频率太高，请稍后在试");
    }

}
