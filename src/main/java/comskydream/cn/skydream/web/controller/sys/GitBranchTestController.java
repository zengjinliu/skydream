package comskydream.cn.skydream.web.controller.sys;

import comskydream.cn.skydream.common.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzj
 * @date 2022/9/6
 */
@RestController
public class GitBranchTestController {



    @GetMapping("/hello1")
    public ResultJson hello(){
        return ResultJson.success();
    }

}
