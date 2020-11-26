package comskydream.cn.skydream.security;

import comskydream.cn.skydream.constant.ActionConstant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局拦截器，解密 Parameters。<br>
 * 该拦截器用来解密参数，并且重构参数,其大致思路如下<br>
 * 一对rsa公钥私钥，约定为AES对称式加密方案。公钥在客户端保存，私钥在服务端保存<br>
 * 客户端<br>
 * 1、客户端代码生成一个随机码为aesKey<br>
 * 2、用aesKey对请求的参数进行AES加密<br>
 * 3、利用公钥对aesKey进行rsa加密<br>
 * 4、将加密后的aesKey放在header中发起请求<br>
 * 服务端<br>
 * 5、服务端接收到请求，拦截其中取到加密的aesKey，利用私钥解密得到aesKey<br>
 * 6、利用aesKey对请求参数的所有value解密<br>
 * 7、返回数据时，再利用aesKey对返回的json串加密<br>
 * 客户端<br>
 * 8、客户端接收到数据时，用自己生成的aesKey进行解密<br>
 * 注意：因为使用setAttribute往下传值，而视图层需要使用aesKey 加密，所以，使用加密模式时，下层不可以重定向，只能请求分派。
 * 一旦重定向，则无法加密返回参数。
 * @author Jayson
 * @date 2020/11/20 17:17
 */
public class RequestDecryptFilter extends OncePerRequestFilter {


    private ParamEncodeProperty property;

    public RequestDecryptFilter() {
    }


    @Override
    protected void initFilterBean() {
        //获取参数加密解密属性
        ServletContext sc = this.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        property = applicationContext.getBean(ParamEncodeProperty.class);
    }


    /**
     * 解密被rsa 加密过的aes密钥
     *
     * @param iv 被rsa 加密过的aes密钥
     * @return 返回aes密钥
     * @date 2019/3/24 20:43
     */

    private String decryptAesKey(String iv) throws ServletException {
        try {
            return RSAUtils.privateDecrypt(iv, RSAUtils.getPrivateKey(property.getRsaPrivateKey()));
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);

        }
    }

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //判定是否需要解密,开启加密，并且该URL 是需要加密的URL
        if (property.getEncodeType() && property.uriIsDecrypt(requestURI)) {
            //获取被rsa加密的aes密钥
            String iv = request.getHeader("iv");
            //iv 是不是空
            if (!StringUtils.isEmpty(iv)) {
                String aesKey = decryptAesKey(iv);
                System.out.println(request.getReader().readLine());
                //替换HttpServletRequest|
                DecodeParametersWrapper decryptParametersWrapper = new DecodeParametersWrapper(request, aesKey);
                //往下层传递明文aes密钥，以及往下层传递需要加密返回参数的判定依据
                //返回数据需要加密
                request.setAttribute(ActionConstant.RESULT_JSON_ENCRYPTION_ATT, ActionConstant.RESULT_JSON_ENCRYPTION);
                decryptParametersWrapper.setAttribute(ActionConstant.RESULT_JSON_ENCRYPTION_AES_KEY_ATT, aesKey);
                filterChain.doFilter(decryptParametersWrapper, response);
            } else {
                //重定向
                response.sendRedirect("/sys/illegalAction");
            }
        } else {
            //返回数据不加密
            request.setAttribute(ActionConstant.RESULT_JSON_ENCRYPTION_ATT, ActionConstant.RESULT_JSON_NOT_ENCRYPTION);
            filterChain.doFilter(request, response);
        }
    }
}
