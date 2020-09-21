package comskydream.cn.skydream.controller.thirdpartylogin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Jayson
 * @date 2020/9/21  22:28
 */
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 用户id
     */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        //加入回话成功的连接集合中去
        webSocketSet.add(this);
        //回话在线数增1操作
        addOnlineCount();
        log.info("有新的用户连接成功,用户标识为:{}; 当前在线人数为:{}", userId, getOnlineCount());
        this.userId = userId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为:{}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法,客户端发送的消息可以进行群发，
     * 也可以客户端带上用户的唯一标识发给指定的用户，
     * 特殊情景特殊处理
     *
     * @param message 客户端发送过来的消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        log.info("收到来自窗口{}，消息内容是{}", userId, message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生异常时的会掉
     *
     * @param session 每次连接的session回话
     * @param error   异常
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误，错误消息{}", error.getMessage());
    }

    /**
     * 服务器主动推送消息给客户端
     *
     * @param message 服务器发送消息个客户端：消息具体内容
     * @throws IOException 异常
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送消息
     *
     * @param message
     * @param sid
     * @throws IOException
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息给用户：{}, 推送内容为:{}", sid, message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.userId.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * @return 当前回话个数
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 每次连接成功，回话个数增加一个
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 断开连接时，回话个数减一操作
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
