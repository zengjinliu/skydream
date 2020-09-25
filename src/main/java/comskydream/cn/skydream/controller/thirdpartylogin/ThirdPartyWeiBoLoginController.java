package comskydream.cn.skydream.controller.thirdpartylogin;

import com.google.gson.JsonObject;
import comskydream.cn.skydream.model.vo.ComprehensiveVo;
import comskydream.cn.skydream.service.thirdservice.ThirdPartyWeiBoComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 第三方登录控制器
 * @author Jayson
 * @date 2020/9/20  16:55
 */
@Controller
public class ThirdPartyWeiBoLoginController {


    @Value("${weibo.redirect.url}")
    private String redirectUrl;

    @Autowired
    private ThirdPartyWeiBoComponent weiBoComponent;


    @RequestMapping(value = "/third/weibo/success",method = RequestMethod.GET)
    public void success(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        //获取access,微博授权成功的回调地址
        ComprehensiveVo vo = weiBoComponent.build(code);
        JsonObject res = new JsonObject();
        res.addProperty("token",vo.getToken());
        res.addProperty("username",vo.getName());
        res.addProperty("userId",vo.getUserId());
        response.sendRedirect(redirectUrl+res);
    }




}
