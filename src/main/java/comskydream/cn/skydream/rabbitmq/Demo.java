//package comskydream.cn.skydream.rabbitmq;
//
//import com.google.gson.JsonObject;
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.annotation.RabbitListeners;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * @author Jayson
// * @date 2020/10/15 14:27
// */
//@Component
//@RabbitListeners(value = {
//        @RabbitListener(queues = "usa.news"),
//})
//public class Demo {
//
//
////    @RabbitHandler
////    public void onMessage(Map<String,Object> map, Message message, Channel channel) throws IOException {
////        long deliveryTag = message.getMessageProperties().getDeliveryTag();
////        channel.basicAck(deliveryTag,false);
////        System.out.println("TopicTotalReceiver消费者收到消息  : " + map.toString());
////    }
//
////    @RabbitHandler
////    public void onMessage1(String message) throws IOException {
//////        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//////        channel.basicAck(deliveryTag,false);
////        System.out.println("TopicTotalReceiver消费者收到消息---->  : "+message);
////    }
//
//}
