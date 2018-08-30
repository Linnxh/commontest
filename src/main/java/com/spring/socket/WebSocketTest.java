package com.spring.socket;


import com.sun.istack.internal.logging.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
public class WebSocketTest {
    private static Logger logger = Logger.getLogger(WebSocketTest.class);
    //线程安全的静态变量，表示在线连接数
    private static volatile int onlineCount = 0;

    //用来存放每个客户端对应的WebSocketTest对象，适用于同时与多个客户端通信
    public static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
    //若要实现服务端与指定客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<Session, Object> webSocketMap = new ConcurrentHashMap<Session, Object>();

    //与某个客户端的连接会话，通过它实现定向推送(只推送给某个用户)
    private Session session;
    public static CopyOnWriteArraySet<String> capWbSockets = new CopyOnWriteArraySet<String>(); //此处定义静态变量，以在其他方法中获取到所有连接


    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */


    //定义一个全局的初始化值count=0
    private static int count = 0;

    @OnOpen
    public void onOpen(final Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        webSocketMap.put(session, this); //加入map中
        addOnlineCount();    //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());


//        new Runnable() {
//            @Override
//            public void run() {
//
//                while(true){
//                    //向前端推送数据，一秒一次推送，格式：1,2,3,4,5.....
//                    try {
//                        sendMessage(session,"当前时间"+new Date());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    count++;
//                    try {
//                        Thread.sleep(1000*60);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.run();

        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                try {
                    sendMessage(session,"当前时间"+new Date());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task,1000);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session closeSession) {
        webSocketSet.remove(this); //从set中删除
        webSocketMap.remove(closeSession); //从map中删除
        subOnlineCount();          //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session mySession) throws Exception {

        logger.info("来自客户端的消息====:" + message);
        //--------------群发消息(多用于聊天室场景)
       /* for (WebSocketTest item : webSocketSet) {
            try {
                item.sendAllMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }*/

        //推送给单个客户端
        for (Session session : webSocketMap.keySet()) {
            if (session.equals(mySession)) {
                WebSocketTest item = (WebSocketTest) webSocketMap.get(mySession);
                try {
                    String msg = "嗨，这是返回的信息--" + message + "---" + new Date();
                    item.sendMessage(mySession, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("发生错误");
        // error.printStackTrace();
    }


    //给所有客户端发送信息
    public void sendAllMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    //定向发送信息
    public void sendMessage(Session mySession, String message) throws IOException {
        synchronized (this) {
            try {
                if (mySession.isOpen()) {//该session如果已被删除，则不执行发送请求，防止报错
                    //this.session.getBasicRemote().sendText(message);
                    mySession.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }


}
