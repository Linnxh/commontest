package com.spring.socket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MyHandler extends TextWebSocketHandler {
    //在线用户列表
    private static final Map<Integer, WebSocketSession> users;
    //用户标识
    private static final String CLIENT_ID = "userId";

    public static ConcurrentHashMap<WebSocketSession, MyHandler> webSocketMap = new ConcurrentHashMap<WebSocketSession, MyHandler>();

    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        session.sendMessage(new TextMessage("成功建立socket连接"));
        System.out.println("==session====" + session);
        webSocketMap.put(session, this);


//            new Runnable() {
//                @Override
//                public void run() {
//
//                    while (true) {
//                        //向前端推送数据，一秒一次推送，格式：1,2,3,4,5.....
//                        try {
//                            session.sendMessage(new TextMessage("当前时间" + new Date()));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            Thread.sleep(1000 );
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }.run();


    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        System.out.println("handleTextMessage--" + message.getPayload());

        //推送给单个客户端, 将equals去掉就是推动给所有用户
        for (WebSocketSession s : webSocketMap.keySet()) {
            if (s.equals(session)) {
                MyHandler item = (MyHandler) webSocketMap.get(s);
                try {
                    String msg = "嗨，这是返回的信息--" + message + "--ssionId--" + s.getId() + "---" + new Date();
                    WebSocketMessage message1 = new TextMessage("server:" + msg);
                    item.sendMessageToUser(s, message1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    /**
     * 发送信息给指定用户
     *
     * @param message
     * @return
     */
    public boolean sendMessageToUser(WebSocketSession s, WebSocketMessage message) throws IOException {


        s.sendMessage(message);
//        if (users.get(clientId) == null) return false;
//        WebSocketSession session = users.get(clientId);
//        System.out.println("sendMessage:" + session);
//        if (!session.isOpen()) return false;
//        try {
//            session.sendMessage(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<Integer> clientIds = users.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getClientId(session));
        webSocketMap.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        users.remove(getClientId(session));
        webSocketMap.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
//            Integer clientId = (Integer) session.getId().get(CLIENT_ID);
            return 1;
        } catch (Exception e) {
            return null;
        }
    }
}
