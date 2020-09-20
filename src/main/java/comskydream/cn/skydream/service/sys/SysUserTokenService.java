package comskydream.cn.skydream.service.sys;

import comskydream.cn.skydream.entity.SysUserToken;

/**
 * @author Jayson
 * @date 2020/9/5  22:11
 */
public interface SysUserTokenService {

    /**
     * 生成token
     * @param userId
     * @return
     */
    String createToken(String userId);

    /**
     * 退出登录
     * @param userId 用户Id
     */
    void logout(String userId);

    /**
     * 条件查询返回单个数据
     * @param userToken
     * @return
     */
    SysUserToken getOne(SysUserToken userToken);
}
