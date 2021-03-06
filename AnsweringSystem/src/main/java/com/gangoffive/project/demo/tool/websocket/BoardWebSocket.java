package com.gangoffive.project.demo.tool.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/board/{sid}")
@Component
public class BoardWebSocket {
    private static AtomicInteger onlineNum = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools=new ConcurrentHashMap<>();

    //    向每一个用户发送信息
    public void sendMessage(Session mysession,String message) throws IOException {
        try {
            for(Session session:sessionPools.values()){
                if(session != mysession){
                    session.getAsyncRemote().sendText(message);
                }

            }
        }catch (Exception e){
            e.getStackTrace();
        }

    }

    @OnOpen
    public void onOpen(Session session,@PathParam(value = "sid") int userId){
        addOnlineCount();
        System.out.println(userId+"加入白板，当前人数为"+onlineNum);
        sessionPools.put(String.valueOf(userId),session);
    }

    @OnMessage
    public void onMessage(Session session,String message) throws IOException {
        System.out.println(message);
        sendMessage(session,message);
    }

    @OnClose
    public void onClose(@PathParam(value = "sid") int userId){
        sessionPools.remove(String.valueOf(userId));
        subOnlineCount();
        System.out.println(userId+"离开白板，当前人数为"+onlineNum);
    }

    @OnError
    public void onError(Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){onlineNum.incrementAndGet();}
    public static void subOnlineCount(){onlineNum.decrementAndGet();}
}
