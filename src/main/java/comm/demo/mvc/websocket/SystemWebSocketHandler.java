package comm.demo.mvc.websocket;

import com.alibaba.fastjson.JSON;

import comm.demo.mvc.bo.CustomerBo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 小K
 * @since 2018/4/10.
 */

@Component
public class SystemWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    //在线用户列表
    public static final Map<String, WebSocketSession> users;
    
    public static final String WEBSOCKET_ONLINE_USER="WEBSOCKET_ONLINE_USER";
    
    static {
        users = new HashMap<String,WebSocketSession>();
    }

    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connect to the websocket success......");
        
        //连接成功以后将用户的登录信息装载到自身map容器里
        CustomerBo client = getClient(session);
        if (client != null) {
            logger.info("userId:{} && session:{}", client.getUserId(), session);
            users.put(client.getUserId(), session);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("flag", "success");
            session.sendMessage(new TextMessage(JSON.toJSONString(map)));
        }else{
        	logger.info("session is null");
        }
    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String clientMsg = (String) message.getPayload();
        //心跳检查
        if(clientMsg.equals("HeartBeat")){
        	
            sendMessageToUsers("HB_OK");
        }
    }

    /**
     * 消息传输错误处理
     *
     * @param session
     * @param thrwbl
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable thrwbl) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.error("websocket connection closed......");
        users.remove(getClient(session).getUserId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus cs) throws Exception {
        logger.error("websocket connection closed......用户[{}]退出",getClient(session).getUserId());
        users.remove(getClient(session).getUserId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 广播消息
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(String message) {
        Set<String> clientIds = users.keySet();
        WebSocketSession session;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    TextMessage msg = new TextMessage(message);
                    session.sendMessage(msg);
                }else{
                    logger.error("session is closed...");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

    }

    /**
     * 给某个用户发送消息
     *  @param clientId
     * @param message
     */
    public void sendMessageToUser(String clientId, String message) {
        if (users.get(clientId) == null) return;
        WebSocketSession session = users.get(clientId);
        logger.info("sendMessage session:{}", session);
        try {
             if(session.isOpen()) {
                 TextMessage msg = new TextMessage(message);
                 session.sendMessage(msg);
                 logger.info("sendMessage:{}", message);
             }else{
                 logger.error("session is closed...");
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
	public CustomerBo getClient(WebSocketSession session){
    	
    	return (CustomerBo)session.getAttributes().get(WEBSOCKET_ONLINE_USER);
    }
}
