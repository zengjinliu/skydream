package comskydream.cn.skydream.component;

import comskydream.cn.skydream.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jayson
 * @date 2020/9/19  16:58
 */
@Configuration
@EnableConfigurationProperties(value = MessageAppProperties.class)
public class MessageSendConfiguration {

    final private HttpUtils httpUtils;

    final private MessageAppProperties messageAppProperties;

    public MessageSendConfiguration(MessageAppProperties messageAppProperties, HttpUtils httpUtils) {
        this.messageAppProperties = messageAppProperties;
        this.httpUtils = httpUtils;
    }


    public void sendMsg(String phone, String msgCode) {
        Map<String, Object> headers = new HashMap<>(2);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + messageAppProperties.getAppCode());
        Map<String, Object> querys = new HashMap<>(5);
        querys.put("receive", phone);
        querys.put("tag", msgCode);
        querys.put("templateId", messageAppProperties.getTemplateId());
        try {
            HttpResponse response = httpUtils.doPost(messageAppProperties.getHost(), messageAppProperties.getPath(), "POST", headers, querys, null);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
