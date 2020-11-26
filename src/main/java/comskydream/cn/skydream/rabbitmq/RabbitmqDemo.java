//package comskydream.cn.skydream.rabbitmq;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * direct exchange
// * fanout exchange
// * topic exchange
// * @author Jayson
// * @date 2020/10/15 14:48
// */
//@Configuration
//public class RabbitmqDemo {
//    private static final String DIRECT_EXCHANGE = "d-jayson";
//    private static final String DIRECT_QUEUE = "d-q-jayson";
//    private static final String DIRECT_KEY = "d-rk-jayson";
//
//    private static final String TOPIC_EXCHANGE = "topic:jayson";
//    private static final String TOPIC_QUEUE = "usa.news";
//    private static final String TOPIC_QUEUE1 = "usa.news1";
//
//
//    /**=========================================direct exchange*/
//    @Bean
//    public DirectExchange directExchange(){
//        //交换机名字，声明持久交换，如果服务器在不再使用该交换时应删除该交换
//        return new DirectExchange(DIRECT_EXCHANGE);
//    }
//    @Bean
//    public Queue dQueue(){
//        return new Queue(DIRECT_QUEUE);
//    }
//    @Bean
//    public Binding dBinding(){
//        return BindingBuilder.bind(dQueue()).to(directExchange()).with(DIRECT_KEY);
//    }
//
//    /***===================================topic exchange========*/
//
//    @Bean
//    public TopicExchange topicExchange(){
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//    @Bean
//    public Queue queue(){
//        return new Queue(TOPIC_QUEUE);
//    }
//    @Bean
//    public Queue queue1(){
//        return new Queue(TOPIC_QUEUE1);
//    }
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(queue()).to(topicExchange()).with("usr.news");
//    }
//    @Bean
//    public Binding binding1(){
//        //*代表一个词，#代表多个词
//        return BindingBuilder.bind(queue1()).to(topicExchange()).with("usr.#");
//    }
//}
