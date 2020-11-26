package comskydream.cn.skydream.security;

import comskydream.cn.skydream.constant.ActionConstant;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 加密返回的json
 *
 * @author Jayson
 * @date 2020/11/20 17:27
 */
@Order(2)
@ControllerAdvice
public class JacksonViewResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        HttpServletRequest servletRequest = servletServerHttpRequest.getServletRequest();

        Object isEncodeObj = servletRequest.getAttribute(ActionConstant.RESULT_JSON_ENCRYPTION_ATT);
        Object aesKeyObj = servletRequest.getAttribute(ActionConstant.RESULT_JSON_ENCRYPTION_AES_KEY_ATT);
        boolean isEncodeJson = isEncodeObj != null ? (boolean) isEncodeObj : false;
        String  aesKey =  aesKeyObj != null ? (String)aesKeyObj:null;
        EncodeInfo info = new EncodeInfo();
        info.setEncodeType(isEncodeJson);
        info.setAesKey(aesKey);
        info.setVo(body);
        return info;
    }


}
