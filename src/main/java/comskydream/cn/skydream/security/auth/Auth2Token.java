package comskydream.cn.skydream.security.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Jayson
 * @date 2020/9/5  21:27
 */
public class Auth2Token implements AuthenticationToken {

    private String token;

    public Auth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
