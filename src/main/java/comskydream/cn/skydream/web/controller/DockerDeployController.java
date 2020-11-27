package comskydream.cn.skydream.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试docker部署
 * @author Jayson
 * @date 2020/10/20 13:39
 */
@RestController
public class DockerDeployController {

    @RequestMapping("/docker")
    public String docker(){
        return "Docker deploy has success! Congratulations!!!";
    }


}
