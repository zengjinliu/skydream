package comskydream.cn.skydream.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置类
 * ①消息推送到server，但是在server里找不到交换机 ---->这种情况触发的是 ConfirmCallback 回调函数。
 * ②消息推送到server，找到交换机了，但是没找到队列 --->这种情况触发的是 ConfirmCallback和ReturnCallback两个回调函数
 * ③消息推送到sever，交换机和队列啥都没找到  --->这种情况触发的是 ConfirmCallback 回调函数。
 * ④消息推送成功 --->这种情况触发的是 ConfirmCallback 回调函数
 *
 * @author Jayson
 * @date 2020/10/15 11:41
 */
@Configuration
public class RabbitMqConfiguration {

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory);
//        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                System.out.println("ReturnCallback:     " + "消息：" + message);
//                System.out.println("ReturnCallback:     " + "回应码：" + replyCode);
//                System.out.println("ReturnCallback:     " + "回应信息：" + replyText);
//                System.out.println("ReturnCallback:     " + "交换机：" + exchange);
//                System.out.println("ReturnCallback:     " + "路由键：" + routingKey);
//            }
//        });
//
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
//                System.out.println("ConfirmCallback:     " + "确认情况：" + ack);
//                System.out.println("ConfirmCallback:     " + "原因：" + cause);
//            }
//        });
//        return rabbitTemplate;
//    }

}
