package comskydream.cn.skydream.service.thirdservice;

import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/25 13:30
 */
public abstract class AbstractThirdUserInfo implements ComprehensiveService{


    /**
     * 根据uid和accessToken获取第三方姓名和图片
     * @param uid 用户id
     * @param accessToken  令牌码
     * @return name->,pic->
     */
    protected abstract Map<String,String> queryName(String uid,String accessToken);
}
