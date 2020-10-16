package comskydream.cn.skydream;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jayson
 * @date 2020/10/15 13:38
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testPublisher(){
        //生产者发送消息
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "direct exchange test demo ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("d-jayson", "d-rk-jayson", map);
    }
    @Test
    public void testPublisher1(){
        //生产者发送消息
        String messageId = String.valueOf(UUID.randomUUID());
        rabbitTemplate.convertAndSend("topic:jayson", "usr.news", messageId);
    }
    @Test
    public void testPublisher2(){
        //生产者发送消息
        String messageId = String.valueOf(UUID.randomUUID());
        rabbitTemplate.convertAndSend("topic:jayson", "usr.1", messageId);
    }

    @Test
    public void testElasticSearch(){

    }

}
