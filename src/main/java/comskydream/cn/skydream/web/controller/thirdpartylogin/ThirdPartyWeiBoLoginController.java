package comskydream.cn.skydream.web.controller.thirdpartylogin;

import com.google.gson.JsonObject;
import comskydream.cn.skydream.model.vo.ComprehensiveVo;
import comskydream.cn.skydream.web.service.thirdservice.ThirdPartyWeiBoComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

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


    @RequestMapping(value = "/third/weibo/success",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public void success(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        //获取access,微博授权成功的回调地址
        ComprehensiveVo vo = weiBoComponent.build(code);
        JsonObject res = new JsonObject();
        res.addProperty("token",vo.getToken());
        res.addProperty("username", URLEncoder.encode(vo.getName(),"UTF-8"));
        res.addProperty("userId",vo.getUserId());
        response.sendRedirect(redirectUrl+res);
    }




}
