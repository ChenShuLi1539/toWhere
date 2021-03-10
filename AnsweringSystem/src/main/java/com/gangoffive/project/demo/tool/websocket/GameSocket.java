package com.gangoffive.project.demo.tool.websocket;

import com.gangoffive.project.demo.entity.Buff;
import com.gangoffive.project.demo.entity.Game;
import com.gangoffive.project.demo.entity.Player;
import com.gangoffive.project.demo.entity.Skill;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
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

    public void studyCard (int  id1,int  id2,String name,int level) {
        boolean isSuccess=game.studyCard(game.selectPlayerById(id1),game.selectPlayerById(id2),name,level);
        if (isSuccess){
            //发送成功信息
        } else {
            //发送失败信息
        }
        sendAllPlayerMessage();
    }

    public boolean haveThisBuff (Player player,String name) {
        for (Buff e:player.getBuffs()) {
            if (e.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addBuff (Player player,String name,String description,int lastTurns,boolean isBeneficial) {
        if (haveThisBuff(player,name)) {
            for (Buff e:player.getBuffs()) {
                if (e.getName().equals(name))
                    e.setLastTurns(lastTurns);
            }
        }else {
            player.getBuffs().add(new Buff(name,description,lastTurns,isBeneficial));
        }
    }

    public void trickCard (int id1,int id2,String name) {
        Player player1=game.selectPlayerById(id1);
        Player player2=game.selectPlayerById(id2);
        //
        //先把牌删除
        //
        player1.setUsedCardsNum(player1.getUsedCardsNum()+1);
        switch (name) {
            case "恶作剧":addBuff(player2,"慌张","抽牌数-1",1,false);break;
            case "整蛊":addBuff(player2,"慌张","抽牌数-1",1,false);drawCard(player1);break;
            case "振奋":Iterator<Buff> iterator = player2.getBuffs().iterator();
                    while (iterator.hasNext()) {
                        Buff a = iterator.next();
                        if (!a.isBeneficial()) {
                            iterator.remove();//使用迭代器的删除方法删除
                        }
                    }break;
            case "共渡难关":player2.setMood(player2.getMood()+2.0);player1.setMood(player1.getMood()+1.0);break;
            case "灵感":addBuff(player2,"怡然","抽牌数+1",1,true);break;
            case "无独有偶":drawCard(player1);drawCard(player1);break;
            case "底力爆发":if (player1.getMood()<5.0) player1.setMood(player1.getMood()+3.0);break;
            case "柳暗花明":if (player2.getMood()<8.0) player2.setMood(8.0);break;
            case "深呼吸":addBuff(player2,"愉悦","学习成功时获得的心情值+0.3",1,true);break;
            case "赠人玫瑰":player1.setMood(player1.getMood()+0.5*player1.getCards().size());player2.getCards().addAll(player1.getCards());player1.getCards().clear();break;
            case "留一手":addBuff(player2,"不服输","学习失败时获得的期待值+0.3",1,true);break;
            case "潜心修学":for (int i=0;i<player1.getRole().getNatures().get(5).getLevel()-player1.getRole().getNatures().get(2).getLevel();i++)
                drawCard(player1);break;
        }
        sendAllPlayerMessage();
    }

    public void turnOverStage (int id) {
        Player player=game.selectPlayerById(id);
        for (Buff e:player.getBuffs()) {
            e.setLastTurns(e.getLastTurns()-1);
        }
        Iterator<Buff> iterator = player.getBuffs().iterator();
        while (iterator.hasNext()) {
            Buff a = iterator.next();
            if (a.getLastTurns()<1) {
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
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
            case "turnStartStage": turnStartStage(0);break;
            case "drawCardsStage": drawCardStage(0);break;
            case "disCardStage": disCardStage(0);break;
            case "学习牌":studyCard(0,0,"项目直接的名字",0);break;
            case "计策牌":
            case "turnOverStage":turnOverStage(0);
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
