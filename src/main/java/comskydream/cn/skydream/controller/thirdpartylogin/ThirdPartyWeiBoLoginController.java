package comskydream.cn.skydream.controller.thirdpartylogin;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.component.WeiBoConfiguration;
import comskydream.cn.skydream.model.dto.WeiBoDto;
import comskydream.cn.skydream.model.vo.ThirdLoginVo;
import comskydream.cn.skydream.service.thirdservice.ThirdPartyWeiBoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 第三方登录控制器
 * @author Jayson
 * @date 2020/9/20  16:55
 */
@Controller
public class ThirdPartyWeiBoLoginController {

    @Autowired
    private ThirdPartyWeiBoService weiBoService;


    @RequestMapping(value = "/third/weibo/success",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson success(@RequestParam("code") String code) throws Exception {
        //获取access,微博授权成功的回调地址
        WeiBoDto accessToken = weiBoService.getAccessToken(code);

        return ResultJson.success(accessToken);
    }

    @RequestMapping(value = "/third/require",method = RequestMethod.GET)
    @ResponseBody
    public ResultJson required() throws Exception {
        ThirdLoginVo loginVo = weiBoService.build();
        return ResultJson.success(loginVo);
    }



}
