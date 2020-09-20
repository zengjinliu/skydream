package comskydream.cn.skydream.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jayson
 * @date 2020/9/20  17:15
 */
@ConfigurationProperties(prefix = "weibo.app")
public class WeiBoProperties {
    /**微博应用Id(clientId)*/
    private String appKey;

    /**微博app秘钥*/
    private String appSecret;

    /**授权成功后的回调地址*/
    private String redirectUri;

    /**授权失败后的回调地址*/
    private String cancelUri;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getCancelUri() {
        return cancelUri;
    }

    public void setCancelUri(String cancelUri) {
        this.cancelUri = cancelUri;
    }
}
