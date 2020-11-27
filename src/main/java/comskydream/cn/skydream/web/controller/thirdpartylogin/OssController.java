package comskydream.cn.skydream.web.controller.thirdpartylogin;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.model.dto.OssPolicyDto;
import comskydream.cn.skydream.web.service.thirdservice.OssClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jayson
 * @date 2020/9/27 15:00
 */
@RestController
public class OssController {

    @Autowired
    private OssClientService ossClientService;

    @RequestMapping(value = "/oss/policy",method = RequestMethod.GET)
    public ResultJson<OssPolicyDto> getPolicy(){
        OssPolicyDto dto = ossClientService.policy();
        return ResultJson.success(dto);
    }
}
