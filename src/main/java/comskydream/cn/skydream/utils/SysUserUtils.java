package comskydream.cn.skydream.utils;

import comskydream.cn.skydream.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

/**
 * 系统工具类获取当前登录的用户信息
 * @author Jayson
 * @date 2020/9/5  22:29
 */
@Slf4j
public class SysUserUtils {

    public static SysUser getUser(){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return sysUser;
    }

    public static String getUserId(){
        return getUser().getUserId();
    }



}
