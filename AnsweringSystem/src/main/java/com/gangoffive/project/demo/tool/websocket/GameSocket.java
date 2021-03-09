package com.gangoffive.project.demo.tool.websocket;

import com.gangoffive.project.demo.entity.Game;
import com.gangoffive.project.demo.entity.Player;
import com.gangoffive.project.demo.entity.Skill;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/game/{sid}")
@Component
public class GameSocket {
    private static AtomicInteger onlineNum = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools=new ConcurrentHashMap<>();
    private Game game= new Game();

    //    向每一个用户发送信息
    public void sendMessage(String message) throws IOException {
        try {
            for(Session session:sessionPools.values()){
                session.getAsyncRemote().sendText(message);
            }
        }catch (Exception e){
            e.getStackTrace();
        }

    }

    public void  accident () {

    }

    public void turnStartStage (int id) {
        Player player=game.selectPlayerById(id);
        if(game.getPlayers().indexOf(player)==0) {
            game.setYear(game.getYear()+1);
            accident();//发生特殊事件
        }
        if(player.getMood()<=0.0) {
            //这个玩家已经出局，则直接进入下一个玩家的回合开始阶段
            turnStartStage(game.getPlayers().get((game.getPlayers().indexOf(player)+1)%game.getPlayers().size()).getId());
        }
        if(game.getYear()==13) {
            //大学毕业，游戏结算
        }else {
            //向前端询问这个人物是否有主动技能
        }
    }

    public void sendAllPlayerMessage () {
        //向前端发送更新后的游戏信息
    }

    public void drawCard (Player player) {
        game.drawCard(player);
        //发送抽牌信息
    }

    public void disCard (Player player) {
        game.disCard(player);
        //发送弃牌信息
    }

    public void drawCardStage (int id) {
        Player player=game.selectPlayerById(id);
        for (Skill e:player.getRole().getSkills()) {
            switch (e.getName()) {
                case "容光焕发":if (player.getMood()>game.averageMood()) {drawCard(player);}
            }
        }
        drawCard(player);
        drawCard(player);
    }

    public void disCardStage (int id) {
        Player player=game.selectPlayerById(id);
        disCard(player);
    }

    public void studyCard (Player player1,Player player2,String name,int level) {
        boolean isSuccess=game.studyCard(player1,player2,name,level);
        if (isSuccess){
            //发送成功信息
        } else {
            //发送失败信息
        }
        sendAllPlayerMessage();
    }

    @OnOpen
    public void onOpen(Session session,@PathParam(value = "sid") int userId){
        addOnlineCount();
        System.out.println(userId+"加入直播课堂，当前人数为"+onlineNum);
        sessionPools.put(String.valueOf(userId),session);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println(message);
        switch (message) {
            case "gameStart": game.gameInit(0,null,null);break;
            case "chooseRole":boolean AllChosen=game.chooseRole(0,null);
                if (AllChosen) turnStartStage(game.getPlayers().get(0).getId());break;
            case "turnStartStage": break;
            case "drawCardsStage": break;
            case "disCardStage": break;
            case "学习牌":studyCard(null,null,"项目直接的名字",0);break;
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "sid") int userId){
        sessionPools.remove(String.valueOf(userId));
        subOnlineCount();
        System.out.println(userId+"离开直播课堂，当前人数为"+onlineNum);
    }

    @OnError
    public void onError(Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){onlineNum.incrementAndGet();}
    public static void subOnlineCount(){onlineNum.decrementAndGet();}
}
