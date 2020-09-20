package comskydream.cn.skydream.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jayson
 * @date 2020/9/19  16:41
 */

@ConfigurationProperties(prefix = "message.app")
public class MessageAppProperties {

    private String host;

    private String path;

    private String appCode;

    private String templateId;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
