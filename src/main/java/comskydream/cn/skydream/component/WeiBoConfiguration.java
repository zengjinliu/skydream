package comskydream.cn.skydream.component;

import comskydream.cn.skydream.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/20  17:19
 */
@Configuration
@EnableConfigurationProperties(value = WeiBoProperties.class)
public class WeiBoConfiguration {

    final private WeiBoProperties weiBoProperties;

    final private HttpUtils httpUtils;

    public WeiBoConfiguration(WeiBoProperties weiBoProperties, HttpUtils httpUtils) {
        this.weiBoProperties = weiBoProperties;
        this.httpUtils = httpUtils;
    }

    /**
     * 通过code码换取accessToken
     * @param code 授权成功的回调地址
     * @return accessToken
     */
    public String getAccessToken(String code) throws Exception {
        //        https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
        String apiUrl = "https://api.weibo.com";
        String path ="/oauth2/access_token";
        Map<String,Object> params = new HashMap<>(16);
        params.put("client_id",weiBoProperties.getAppKey());
        params.put("client_secret",weiBoProperties.getAppSecret());
        params.put("grant_type","authorization_code");
        params.put("redirect_uri",weiBoProperties.getRedirectUri());
        params.put("code",code);
        HttpResponse post = httpUtils.doPost(apiUrl, path, "post", new HashMap<>(), new HashMap<>(), params);
        HttpEntity entity = post.getEntity();
        return EntityUtils.toString(entity);
    }

}
