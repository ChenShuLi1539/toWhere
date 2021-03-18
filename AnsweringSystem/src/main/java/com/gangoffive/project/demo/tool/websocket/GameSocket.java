package com.gangoffive.project.demo.tool.websocket;

import com.gangoffive.project.demo.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/game/{sid}")
@Component
public class GameSocket {
    private static AtomicInteger onlineNum = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools=new ConcurrentHashMap<>();
    private static Game game= new Game();
    private static List<Roommate> roommates=new ArrayList<>();

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

    public void caculateTolatScore () {
        for (Player e:game.getPlayers()) {
            int totalScore=0;

            //计算项目地熟练度分值
            for (BigProject bigProject:e.getBigProjects()) {
                for (SmallProject smallProject:bigProject.getSmallProjects()) {
                    if (smallProject.getMastery()>=45) {
                        totalScore+=10;
                    } else if (smallProject.getMastery()>=35) {
                        totalScore+=8;
                    } else if (smallProject.getMastery()>=25) {
                        totalScore+=6;
                    } else if (smallProject.getMastery()>=17) {
                        totalScore+=4;
                    } else if (smallProject.getMastery()>=10) {
                        totalScore+=2;
                    }
                }
            }
            e.setProjectScore(totalScore);
            totalScore=0;

            //心情值分值
            totalScore+=(int)(e.getMood()*1.5);
            e.setMoodScore(totalScore);
            totalScore=0;

            //使用牌数量分值
            totalScore+=(int)(e.getUsedCardsNum()*0.5);
            e.setCardScore(totalScore);
            totalScore=0;

            //名品分值
            e.setTreasureScore(totalScore);
            totalScore=0;

            e.setTotalScore(e.getProjectScore()+e.getMoodScore()+e.getCardScore()+e.getTreasureScore());
        }
    }

    static class SortHelper implements Comparator{
        @Override
        public int compare(Object a,Object b) {
            Player s1=(Player) a;
            Player s2=(Player) b;
            return s2.getTotalScore() - s1.getTotalScore();
        }
    }

    public void turnStartStage (int id) throws IOException {
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
            caculateTolatScore();
            game.getPlayers().sort(new SortHelper());
            Map<String,String> map1=new HashMap<>();
            map1.put("type","gameover");
            map1.put("data",JSONArray.fromObject(game.getPlayers()).toString());
            JSONObject jsonObject=JSONObject.fromObject(map1);
            sendMessage(jsonObject.toString());
            game=new Game();
        }else {
            if (haveThisSkill(player,"文思泉涌") && player.getMood()>=15.0) {
                Iterator<Card> iterator = game.getCards().iterator();
                boolean gotit = false;
                while (iterator.hasNext()) {
                    Card a = iterator.next();
                    if (a.getType()== 2 ) {
                        player.getCards().add(a);
                        iterator.remove();
                        gotit = true;
                        break;
                    }
                }
                if(gotit)
                    sendText(player.getRole().getName()+"发动‘文思泉涌’，从剩余的牌堆中抽取了一张【名品牌】");
                else
                    sendText(player.getRole().getName()+"发动‘文思泉涌’，但是剩余的牌堆中没有【名品牌】");
            }
            if (haveThisSkill(player,"再生力") && player.getMood()<game.averageMood()) {
                player.setMood(player.getMood()+0.5);
                sendText(player.getRole().getName()+"发动‘再生力’提高了自己的心情值");
            }
            sendAllPlayerMessage();
            //向前端询问这个人物是否有主动技能
             Map<String,String> map1=new HashMap<>();
            map1.put("type","turnStartStage");
            map1.put("id",id+"");
            JSONObject jsonObject=JSONObject.fromObject(map1);
            sendMessage(jsonObject.toString());
        }
    }

    public void sendAllPlayerMessage () throws IOException {
        //向前端发送更新后的游戏信息
        Map<String,String> map=new HashMap<>();
        map.put("type","playersData");
        map.put("data",JSONArray.fromObject(game.getPlayers()).toString());
        JSONObject jsonObject=JSONObject.fromObject(map);
        sendMessage(jsonObject.toString());
    }

    public void sendText (String message) throws IOException {
        Map<String,String> map=new HashMap<>();
        map.put("type","text");
        map.put("name","游戏信息");
        map.put("id","-1");
        map.put("context",message);
        JSONObject jsonObject=JSONObject.fromObject(map);
        sendMessage(jsonObject.toString());
    }

    public void drawCard (Player player) {
        game.drawCard(player);
        //发送抽牌信息
    }

    public void disCard (Player player) {
        game.disCard(player);
        //发送弃牌信息
    }

    public void drawCardStage (int id) throws IOException {
        Player player=game.selectPlayerById(id);
        int drawCardTimes=5;
        if(haveThisSkill(player,"容光焕发"))
            if(player.getMood()>game.averageMood()) {
                drawCardTimes++;
                sendText(game.selectPlayerById(id).getRole().getName()+ "发动技能‘容光焕发’多抽了一张牌");
            }
        if(haveThisBuff(player,"慌张"))
            drawCardTimes--;
        if(haveThisBuff(player,"怡然"))
            drawCardTimes++;
        for (int i=0;i<drawCardTimes;i++)
            drawCard(player);
        sendAllPlayerMessage();
    }

    public void disCardStage (int id) throws IOException {
        Player player=game.selectPlayerById(id);
        boolean haveTrickCard=false;
        for (Card e:player.getCards()) {
            if(e.getType()==3)
                haveTrickCard=true;
        }
        if(haveTrickCard) {
            for (Player e:game.getPlayers()) {
                if (e.getId()!=id && haveThisSkill(e,"无谋之乐")) {
                    e.setMood(game.mathRound(e.getMood()+0.3));
                    sendText(e.getRole().getName()+"因"+player.getRole().getName()+"丢弃了【计策牌】而增加了心情值");
                }
            }
        }
        if(player.getCards().size()>3 && haveThisSkill(player,"否极泰来")) {
            for (Nature e:player.getRole().getNatures()) {
                e.setLevel(e.getLevel()+1);
            }
            sendText(player.getRole().getName()+"因为丢弃了超过三张牌发动了技能‘否极泰来’");
        }
        disCard(player);
        sendAllPlayerMessage();
        Map<String,String> map1=new HashMap<>();
        map1.put("type","disCardStage");
        map1.put("id",id+"");
        JSONObject jsonObject=JSONObject.fromObject(map1);
        sendMessage(jsonObject.toString());
    }

    public void useCard (int id,String name) {
        Player player=game.selectPlayerById(id);
        Iterator<Card> iterator = player.getCards().iterator();
        while (iterator.hasNext()) {
            Card a = iterator.next();
            if (a.getName().equals(name) ) {
                game.getUsedCards().add(a);
                iterator.remove();
                break;
            }
        }
        player.setUsedCardsNum(player.getUsedCardsNum()+1);
    }

    public void studyCard (int  id1,int  id2,String cardName,String name,int level) throws IOException {
        useCard(id1,cardName);
        boolean isSuccess=game.studyCard(game.selectPlayerById(id1),game.selectPlayerById(id2),name,level);
        if (isSuccess){
            Map<String,String> map=new HashMap<>();
            map.put("type","studySuccess");
            map.put("text",game.selectPlayerById(id1).getRole().getName()+"与"+game.selectPlayerById(id2).getRole().getName()+"学习"+name+"成功");
            JSONObject jsonObject=JSONObject.fromObject(map);
            sendMessage(jsonObject.toString());
            sendText(game.selectPlayerById(id1).getRole().getName()+"与"+game.selectPlayerById(id2).getRole().getName()+"学习"+name+"成功");
        } else {
            Map<String,String> map=new HashMap<>();
            map.put("type","studyFail");
            map.put("text",game.selectPlayerById(id1).getRole().getName()+"与"+game.selectPlayerById(id2).getRole().getName()+"学习"+name+"失败");
            JSONObject jsonObject=JSONObject.fromObject(map);
            sendMessage(jsonObject.toString());
            sendText(game.selectPlayerById(id1).getRole().getName()+"与"+game.selectPlayerById(id2).getRole().getName()+"学习"+name+"失败");
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

    public boolean haveThisSkill (Player player,String name) {
        for (Skill e:player.getRole().getSkills()) {
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

    public void treasureCard (int id,String name) throws IOException {
        Player player=game.selectPlayerById(id);
        Iterator<Card> iterator = player.getCards().iterator();
        while (iterator.hasNext()) {
            Card a = iterator.next();
            if (a.getName().equals(name) ) {
                iterator.remove();
                break;
            }
        }
        player.setUsedCardsNum(player.getUsedCardsNum()+1);
        player.getTreasures().add(new Treasure(name.substring(2)));
        sendAllPlayerMessage();
        sendText(game.selectPlayerById(id).getRole().getName()+ "制造出了" + name.substring(2) + "！！！");
        if (haveThisSkill(player,"触类旁通")) {
            for (BigProject e:player.getBigProjects()) {
                for (SmallProject each:e.getSmallProjects())
                    each.setMastery(each.getMastery()+2.0);
            }
            sendText(game.selectPlayerById(id).getRole().getName()+ "因为制造出了名品而发动了‘触类旁通’");
        }
    }

    public void trickCard (int id1,int id2,String name) throws IOException {
        Player player1=game.selectPlayerById(id1);
        Player player2=game.selectPlayerById(id2);
        //
        //先把牌删除
        useCard(id1,name);
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
        if(haveThisSkill(player1,"幕后黑手")){
            player1.setMood(player1.getMood()+0.5);
            sendText(game.selectPlayerById(id1).getRole().getName()+"发动‘幕后黑手’技能增加了心情值");
        }
        sendAllPlayerMessage();
        if (name.equals("无独有偶") || name.equals("底力爆发") || name.equals("潜心修学")) {
            sendText(game.selectPlayerById(id1).getRole().getName()+ "使用了[" + name + "]");
        } else {
            sendText(game.selectPlayerById(id1).getRole().getName()+"对" + game.selectPlayerById(id2).getRole().getName() + "使用了[" + name + "]");
        }
    }

    public void luckyCard (int id,String name) throws IOException {
        Player player=game.selectPlayerById(id);
        useCard(id,name);
        switch (name) {
            case "提升聪颖":player.getRole().getNatures().get(0).setLevel(player.getRole().getNatures().get(0).getLevel()+1);break;
            case "提升勇敢":player.getRole().getNatures().get(1).setLevel(player.getRole().getNatures().get(1).getLevel()+1);break;
            case "提升顽皮":player.getRole().getNatures().get(2).setLevel(player.getRole().getNatures().get(2).getLevel()+1);break;
            case "提升细腻":player.getRole().getNatures().get(3).setLevel(player.getRole().getNatures().get(3).getLevel()+1);break;
            case "提升坚韧":player.getRole().getNatures().get(4).setLevel(player.getRole().getNatures().get(4).getLevel()+1);break;
            case "提升谨慎":player.getRole().getNatures().get(5).setLevel(player.getRole().getNatures().get(5).getLevel()+1);break;
            case "提升兴趣":int sum=0;
                for(BigProject e:game.getPlayers().get(1).getBigProjects()) {
                    sum+=e.getSmallProjects().size();
                }
                int i=1+(int)(Math.random()*sum);
                for (BigProject e:player.getBigProjects()) {
                    if(sum>e.getSmallProjects().size())
                        sum-=e.getSmallProjects().size();
                    else {
                        if(e.getSmallProjects().get(sum-1).getEagerness()!=3)
                            e.getSmallProjects().get(sum-1).setEagerness(e.getSmallProjects().get(sum-1).getEagerness()+1);
                        break;
                    }
                }break;
        }
        sendAllPlayerMessage();
        sendText(game.selectPlayerById(id).getRole().getName()+ "使用了[" + name + "]，对应的能力上升了");
    }

    public void useSkill (int id1,int id2,String name,List<Card> cards) throws IOException {
        Player player1=game.selectPlayerById(id1);
        Player player2=game.selectPlayerById(id2);
        switch (name) {
            case "熟能生巧":player1.setMood(player1.getMood()-1.0);
                        addBuff(player2,"熟练","下回合通过【学习牌】获得的熟练度+1",1,true);
                        sendText(player1.getRole().getName()+"使用了‘熟能生巧’为"+player2.getRole().getName()+"添加了buff");
                        break;
            default:break;
        }
        sendAllPlayerMessage();
    }

    public void turnOverStage (int id) throws IOException {
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
        sendAllPlayerMessage();
        Map<String,String> map1=new HashMap<>();
        map1.put("type","turnOverStage");
        map1.put("id",id+"");
        JSONObject jsonObject=JSONObject.fromObject(map1);
        sendMessage(jsonObject.toString());
    }

    @OnOpen
    public void onOpen(Session session,@PathParam(value = "sid") int userId){
        addOnlineCount();
        System.out.println(userId+"加入游戏房间，当前人数为"+onlineNum);
        sessionPools.put(String.valueOf(userId),session);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println(message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        String type=jsonObject.getString("type");
        switch (type) {
            case "waiting": roommates.add(new Roommate(jsonObject.getInt("id"),jsonObject.getString("name")));
                        if(roommates.size()==1)
                            roommates.get(0).setOwner(true);
                        Map<String,String> map=new HashMap<>();
                        map.put("type","waiting");
                        map.put("data", JSONArray.fromObject(roommates).toString());
                        JSONObject jsonObject1=JSONObject.fromObject(map);
                        sendMessage(jsonObject1.toString());break;
            case "gameStart": Collections.shuffle(roommates);
                    game.gameInit(roommates.size(),roommates);
                    Map<String,String> map1=new HashMap<>();
                    map1.put("type","gameStart");
                    JSONObject jsonObject2=JSONObject.fromObject(map1);
                    sendMessage(jsonObject2.toString());
                    jsonObject2=JSONObject.fromObject(game.gameChooseRole());
                    sendMessage(jsonObject2.toString());break;
            case "chooseRole":boolean AllChosen=game.chooseRole(jsonObject.getInt("id"),jsonObject.getString("name"));
                if (AllChosen) {
                    sendAllPlayerMessage();
                    map1=new HashMap<>();
                    map1.put("type","AllChosen");
                    jsonObject2=JSONObject.fromObject(map1);
                    sendMessage(jsonObject2.toString());
                    turnStartStage(game.getPlayers().get(0).getId());
                }break;
            case "turnStartStage": turnStartStage(jsonObject.getInt("id"));break;
            case "drawCardStage": drawCardStage(jsonObject.getInt("id"));break;
            case "disCardStage": disCardStage(jsonObject.getInt("id"));break;
            case "学习牌":studyCard(jsonObject.getInt("id"),jsonObject.getInt("id2"),jsonObject.getString("cardName"),jsonObject.getString("name"),jsonObject.getInt("level"));break;
            case "名品牌":treasureCard(jsonObject.getInt("id"),jsonObject.getString("name"));break;
            case "计策牌":trickCard(jsonObject.getInt("id"),jsonObject.getInt("id2"),jsonObject.getString("name"));break;
            case "机缘牌":luckyCard(jsonObject.getInt("id"),jsonObject.getString("name"));break;
            case "Skill":useSkill(jsonObject.getInt("id"),jsonObject.getInt("id2"),jsonObject.getString("name"),JSONArray.toList(jsonObject.getJSONArray("cards"),Card.class));break;
            case "turnOverStage":turnOverStage(jsonObject.getInt("id"));break;
            default:sendMessage(message);break;
        }
    }

    @OnClose
    public void onClose(@PathParam(value = "sid") int userId){
        sessionPools.remove(String.valueOf(userId));
        subOnlineCount();
        System.out.println(userId+"离开游戏房间，当前人数为"+onlineNum);
        Iterator<Roommate> iterator = roommates.iterator();
        boolean isOwner=false;
        while (iterator.hasNext()) {
            Roommate a = iterator.next();
            if (a.getId()==userId) {
                iterator.remove();//使用迭代器的删除方法删除
                if(a.isOwner())
                    isOwner=true;
            }
        }
        if (isOwner&&roommates.size()>0)
            roommates.get(0).setOwner(true);
        game=new Game();
    }

    @OnError
    public void onError(Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }

    public static void addOnlineCount(){onlineNum.incrementAndGet();}
    public static void subOnlineCount(){onlineNum.decrementAndGet();}
}
