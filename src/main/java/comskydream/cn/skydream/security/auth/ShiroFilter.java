package comskydream.cn.skydream.security.auth;

import com.google.gson.Gson;
import comskydream.cn.skydream.common.ResultJson;
import comskydream.cn.skydream.utils.HttpContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * @author Jayson
 * @date 2020/9/5  21:15
 */
public class ShiroFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取token
        String token = this.getRequestToken((HttpServletRequest) request);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        return new Auth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token,如果token不存在直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isEmpty(token)){
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Credentials","true");
            res.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            String json = new Gson().toJson(ResultJson.error(HttpStatus.UNAUTHORIZED.value(), "invalid token"));
            res.getWriter().print(json);
            return false;
        }
        return executeLogin(request,response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //登录失败
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            ResultJson resultJson = ResultJson.error(HttpStatus.UNAUTHORIZED.value(), throwable.getMessage());
            String json = new Gson().toJson(resultJson);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private String getRequestToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        return token;
    }



}
