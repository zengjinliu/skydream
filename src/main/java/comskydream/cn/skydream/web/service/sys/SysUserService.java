package comskydream.cn.skydream.web.service.sys;

import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.common.ResultPage;
import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.model.vo.PasswordVo;
import comskydream.cn.skydream.model.vo.SysUserVo;

import java.util.List;
import java.util.Set;

/**
 * @author Jayson
 * @date 2020/9/5  21:58
 */
public interface SysUserService {

    /**
     * 分页查询用户信息
     * @param sysUserVo
     * @return
     */
    ResultPage<List<SysUserVo>> pageList(SysUserVo sysUserVo);

    /**
     * 条件查找用户返回单个用户
     * @param sysUser
     * @return
     */
    SysUser getOne(SysUser sysUser);

    /**
     * 条件查询返会多个
     * @param sysUser
     * @return
     */
    List<SysUser> list(SysUser sysUser);

    /**
     * 查询用户的所有权限
     * @param userId
     * @return
     */
    Set<String> queryAllPermission(String userId);

    /**
     * 查询用户所有的菜单Id
     * @param userId 用户id
     * @return
     */
    List<String> queryAllMenuIds(String userId);

    /**
     * 更新密码
     * @param passwordVo
     */
    void updatePwd(PasswordVo passwordVo);

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    int save(SysUserVo sysUser);

    /**
     * 删除
     * @param userIds
     * @return
     */
    void delete(List<String> userIds);

    /**
     * 更新用户信息
     * @param sysUser
     * @return
     */
    int update(SysUserVo sysUser);

    /**
     * 检查用户名是否存在
     * @param username
     * @return true 存在， false不存在
     */
    Boolean checkNameExist(String username);

    /**
     * 短信登陆，不管用户存不存在，都将为当前手机号创建一个用户，
     * 注意验证码防刷的形式
     * @param phone 手机号
     * @param msgCode 验证码
     * @return 返回用户信息
     */
    ResultJson msgLogin(String phone, String msgCode);

    /**
     * 发送短信验证码
     * @param phone 电话号码
     * @return true发送成功，false发送失败
     */
    Boolean sendMsgCode(String phone);
}
