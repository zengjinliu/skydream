//package comskydream.cn.skydream.rabbitmq;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.annotation.RabbitListeners;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
///**
// * @author Jayson
// * @date 2020/10/15 14:27
// */
//@Component
//@RabbitListeners(value = {
//        @RabbitListener(queues = "usa.news1"),
//})
//public class Demo1 {
//
//    @RabbitHandler
//    public void onMessage2(String message) throws IOException {
////        long deliveryTag = message.getMessageProperties().getDeliveryTag();
////        channel.basicAck(deliveryTag,false);
//        System.out.println("TopicTotalReceiver消费者收到消息1---->  : "+message);
//    }
//
//}
