package comskydream.cn.skydream.controller;

import comskydream.cn.skydream.service.SysCaptchaService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Jayson
 * @date 2020/9/5  16:45
 */
@Controller
public class SysLoginController {


    @Autowired
    private SysCaptchaService sysCaptchaService;

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


}
