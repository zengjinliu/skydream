package comskydream.cn.skydream.service.thirdservice;

import comskydream.cn.skydream.model.dto.WeiBoDto;
import comskydream.cn.skydream.model.vo.ThirdLoginVo;

/**
 * @author Jayson
 * @date 2020/9/20  17:10
 */
public interface ThirdPartyWeiBoService {

    /**
     * 通过code码换取accessToken
     *
     * @param code 授权回调的code码
     * @return accessToken
     */
    WeiBoDto getAccessToken(String code);

    /**
     * 第三方授权登录回调成功后构建数据库实体对象
     * @return
     * @throws Exception
     */
    ThirdLoginVo build() throws Exception;
}
