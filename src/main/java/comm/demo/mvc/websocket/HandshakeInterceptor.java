package comm.demo.mvc.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import comm.demo.mvc.bo.CustomerBo;

import java.util.Map;

/**
 * @author 小K
 * @since 2018/4/10.
 */
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
    private static final Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);

    public static final String WEBSOCKET_ONLINE_USER="WEBSOCKET_ONLINE_USER";
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.info("Before Handshake");
        //塞入登录信息
        String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
        String type = ((ServletServerHttpRequest) request).getServletRequest().getParameter("type");
        String location = ((ServletServerHttpRequest) request).getServletRequest().getParameter("location");
        CustomerBo customerBo = new CustomerBo();
        customerBo.setUserId(userId);
        customerBo.setType(type);
        customerBo.setLocation(location);
        attributes.put(WEBSOCKET_ONLINE_USER,customerBo);
        return super.beforeHandshake(request,response,wsHandler,attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        logger.info("After Handshake");
        super.afterHandshake(request,response,wsHandler,ex);
    }
}
