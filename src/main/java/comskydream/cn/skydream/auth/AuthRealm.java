package comskydream.cn.skydream.auth;

import comskydream.cn.skydream.entity.SysUser;
import comskydream.cn.skydream.entity.SysUserToken;
import comskydream.cn.skydream.service.SysUserService;
import comskydream.cn.skydream.service.SysUserTokenService;
import comskydream.cn.skydream.utils.DateUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * @author Jayson
 * @date 2020/9/5  21:09
 */
@Component
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Auth2Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //授权认证时调用
        SysUser user = (SysUser)principals.getPrimaryPrincipal();
        String userId = user.getUserId();
        //用户权限列表
        Set<String> permsSet = sysUserService.queryAllPermission(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登录认证时调用
        String accessToken = (String) token.getPrincipal();
        //根据accessToken，查询用户信息
        SysUserToken userToken = sysUserTokenService.getOne(SysUserToken.builder().token(accessToken).build());
        //token失效
        if(Objects.isNull(userToken) || DateUtils.getMillis(userToken.getExpireTime()) < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //查询用户信息
        SysUser user = sysUserService.getOne(SysUser.builder().userId(userToken.getUserId()).build());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
