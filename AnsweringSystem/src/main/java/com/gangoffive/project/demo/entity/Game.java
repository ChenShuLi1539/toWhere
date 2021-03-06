package com.gangoffive.project.demo.entity;

import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

@Data
public class Game {
    private List<Role> roles;
    private List<Card> cards;
    private List<Card> usedCards=new ArrayList<>();
    private List<Player> players=new ArrayList<>();
    private int playerNum;
    private int chosenNum=0;
    private int year=0;//0表示小学四年级，之后每+1表示增加一年
    private int redTeamScore=0;
    private int blueTeamScore=0;

    public Game () {
        roleInit();
        cardInit();
    }

    public void roleInit () {
        this.roles=new ArrayList<>();
        List<Skill> skills=new ArrayList<>();
        skills.add(new Skill("幕后黑手","在你使用【计策牌】后，你的心情值+0.5",true));
        skills.add(new Skill("无谋之乐","在其他人弃牌阶段弃牌时，如果丢弃的牌中存在【计策牌】，那么你的心情值+0.3",true));
        this.roles.add(new Role("张天舒",1,1,2,0,3,2,3,
                "园林大师","自然-园艺熟练度达到A",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("勇壮","在游戏开始时，你获得2点勇敢和1点坚韧",true));
        skills.add(new Skill("重整旗鼓","出牌阶段，你可以丢弃3张牌，清除场上一名玩家的所有负面效果，并使其抽一张牌",false));
        this.roles.add(new Role("兰文俊",1,3,3,3,0,3,2,
                "武术大师","体育-武术熟练度达到A",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("珍视","出牌阶段，你可以选择一张牌进行收藏，每回合限一次",false));
        skills.add(new Skill("回忆","出牌阶段，消耗3.0心情值，使你本回合的所有天性临时+1，并取出你收藏的所有牌",false));
        this.roles.add(new Role("吴童",1,2,3,3,2,0,1,
                "世界和平","全场的平均心情值不低于12.0",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("天翻地覆","出牌阶段，你可以丢弃两张牌并消耗2.0心情值，获得一张额外的【投资】，每回合限一次",false));
        this.roles.add(new Role("徐东东",1,2,2,3,2,1,1,
                "发大财","财力值达到15",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("触类旁通","在你制造出名品之后，你对所有项目的熟练度增加2.0",true));
        skills.add(new Skill("文思泉涌","在你的回合开始时，如果你的心情值达到了15及以上，从剩余的牌堆中抽取一张【名品牌】",true));
        this.roles.add(new Role("陈树力",1,2,1,2,1,3,2,
                "实现梦想","制造出一件名品",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("容光焕发","摸牌阶段，如果你的心情值高于全场的平均值，你本回合的抽牌数+1",true));
        skills.add(new Skill("否极泰来","弃牌阶段，如果你丢弃了超过三张牌，你的所有天性+1",true));
        this.roles.add(new Role("廖纪童",1,2,0,1,2,1,2,
                "随心所欲","累计打出40张牌",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("再生力","你的回合开始时，如果你的心情值低于全场的平均值，你的心情值+0.5",true));
        skills.add(new Skill("熟能生巧","出牌阶段，你可以消耗1.0心情值，对场上任意一名玩家使用，使其下回合通过【学习牌】获得的熟练度+1",false));
        this.roles.add(new Role("杨礼政",1,2,2,0,2,3,2,
                "世界和平","全场的平均心情值不低于12.0",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("醉生梦死","出牌阶段，你可以丢弃一定数量的牌，根据丢弃的牌的数量决定接下来效果发生的次数：使你下一位玩家的心情值减少0.3，如果还丢弃有牌，则使该角色的往后第二位玩家心情值增加0.6，如果还丢弃有牌，则是该角色的往后第三位玩家心情值降低0.9，以此类推。每回合限一次",false));
        this.roles.add(new Role("覃思宁",1,3,1,2,1,1,1,
                "生而活","回合结束时至少有两个项目熟练度达到C及以上",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("容光焕发","摸牌阶段，如果你的心情值高于全场的平均值，你本回合的抽牌数+1",true));
        skills.add(new Skill("失意","出牌阶段，你可以丢弃两张牌，对场上任意一名玩家使用，使其下回合通过【学习牌】获得的心情值减少1.0",false));
        this.roles.add(new Role("王文捷",1,2,2,1,3,2,2,
                "兼济天下","回合结束时，你的心情值为全场最高并且所有人的平均心情值不低于12",8.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("锋芒毕露","当你学习成功时，使场上除你以外的所有男性角色降低0.3心情值",true));
        this.roles.add(new Role("韦承洋",1,3,1,2,2,0,1,
                "发大财","财力值达到15",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("失心","出牌阶段，你可以丢弃一张牌，对场上任意一名玩家使用，有你的顽皮/(对方勇敢+你的顽皮)%概率使其下一回合在出牌阶段无法使用技能",false));
        this.roles.add(new Role("庞卓凡",0,3,3,3,2,1,0,
                "随心所欲","累计打出40张牌",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("钱庄","游戏开始时，你拥有5点财力值",true));
        skills.add(new Skill("紧急救援","出牌阶段，消耗1点财力值，对场上任意一名玩家使用，使其下回合的抽牌数+1",false));
        skills.add(new Skill("炉边聚会","出牌阶段，消耗3点财力值，使我方全体下回合学习成功时获得的心情值+0.5",false));
        this.roles.add(new Role("许悦",0,1,3,2,2,1,1,
                "永远开心","心情值达到20及以上",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("博闻强识","当你学习失败时，你本回合通过【学习牌】获得的熟练度+1",true));
        skills.add(new Skill("孜孜不倦","你每个回合可学习的次数+1",true));
        this.roles.add(new Role("李浪",1,3,1,3,3,2,2,
                "实现梦想","制造出一件名品",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("降智打击","回合开始时，如果你的心情值低于全场的平均值，那么抽牌阶段，你的抽牌数+2，但是心情值-2.0",true));
        this.roles.add(new Role("李昱辰",1,1,1,3,2,2,1,
                "发大财","财力值达到15",5.0,skills));
    }

    public void cardInit () {
        this.cards=new ArrayList<>();
        for (int i=0;i<6;i++) {
            cards.add(new Card("学习体育",0,"学习体育兴趣的卡牌"));
            cards.add(new Card("学习音乐",0,"学习音乐兴趣的卡牌"));
            cards.add(new Card("学习文学",0,"学习文学兴趣的卡牌"));
            cards.add(new Card("学习美术",0,"学习美术兴趣的卡牌"));
            cards.add(new Card("学习自然",0,"学习自然兴趣的卡牌"));
            cards.add(new Card("学习生活",0,"学习生活兴趣的卡牌"));
        }
        for (int i=0;i<1;i++) {
            cards.add(new Card("制作独立音乐",2,"需要玩家的音乐-乐器熟练度达到C才能使用。获得名品“独立音乐”。"));
            cards.add(new Card("制作玩具模型",2,"需要玩家的美术-绘画、生活-手工达到D以上才能使用。获得名品“玩具模型”。"));
        }
        for (int i=0;i<2;i++) {
            cards.add(new Card("恶作剧",3,"需要玩家的顽皮天性>4才能使用。对场上一名玩家使用，使其下个回合的抽牌数-1。"));
            cards.add(new Card("整蛊",3,"需要玩家的顽皮天性>7才能使用。对场上一名玩家使用，使其下个回合的抽牌数-1，你自己抽一张牌。"));
            cards.add(new Card("振奋",3,"需要玩家的勇敢天性>1才能使用。对场上一名玩家使用，清除其身上的所有负面效果。"));
            cards.add(new Card("共渡难关",3,"需要玩家的勇敢天性>6才能使用。对场上除你之外一名玩家使用，对方增加2.0心情值，你增加1.0心情值。"));
            cards.add(new Card("灵感",3,"需要玩家的聪颖天性>4才能使用。对场上一名玩家使用，使其下个回合抽牌数+1。"));
            cards.add(new Card("无独有偶",3,"需要玩家的聪颖天性>6才能使用。你抽两张牌。"));
            cards.add(new Card("底力爆发",3,"需要玩家的坚韧天性>2才能使用。如果你的心情值低于5.0，那么你立即增加3.0心情值。"));
            cards.add(new Card("柳暗花明",3,"需要玩家的坚韧天性>8才能使用。对场上一名玩家使用，如果其心情值低于8.0，那么立刻将其心情值增加至8.0。"));
            cards.add(new Card("深呼吸",3,"需要玩家的细腻天性>5才能使用。对场上一名玩家使用，使其下一回合学习成功时获得的心情值增加0.3。"));
            cards.add(new Card("赠人玫瑰",3,"需要玩家的细腻天性>7才能使用。对场上除你之外一名玩家使用，将你当前的手牌转移给对方，每转移一张牌，你的心情值增加0.5。"));
            cards.add(new Card("留一手",3,"需要玩家的谨慎天性>3才能使用。对场上一名玩家使用，使其下回合学习失败时获得的期待值增加0.5。"));
            cards.add(new Card("潜心修学",3,"需要玩家的谨慎天性>8才能使用。你的谨慎天性每比顽皮天性高出1点，抽一张牌。"));
            cards.add(new Card("投资",3,"消耗2点财力值，有你的聪颖/（全场勇敢平均值+全场聪颖平均值）%概率获得5点财力值。"));
            cards.add(new Card("借贷",3,"消耗2点财力值，使场上除你以外的一名玩家财力值+2。"));
            cards.add(new Card("5毛零食",3,"消耗1点财力值，你的心情值+1.0。"));
        }
        for (int i=0;i<1;i++) {
            cards.add(new Card("提升聪颖",4,"聪颖+1。"));
            cards.add(new Card("提升勇敢",4,"勇敢+1。"));
            cards.add(new Card("提升顽皮",4,"顽皮+1。"));
            cards.add(new Card("提升细腻",4,"细腻+1。"));
            cards.add(new Card("提升坚韧",4,"坚韧+1。"));
            cards.add(new Card("提升谨慎",4,"谨慎+1。"));
            cards.add(new Card("提升兴趣",4,"使随机一个兴趣上升一层。"));
            cards.add(new Card("飞来横财",4,"财力值+2。"));
        }
        Collections.shuffle(cards);
    }

    public void gameInit (int num,List<Roommate> roommates) {
        playerNum=num;
        for (int i=0;i<num;i++) {
            players.add(new Player(roommates.get(i).getName(),roommates.get(i).getId()));
        }
    }

    public Map<String,String> gameChooseRole () {
        Map<String,String> maps=new HashMap<>();
        maps.put("type","chooseRole");
        Collections.shuffle(roles);
        for(int i=0;i<playerNum;i++) {
//            String str=new String();
            List<Role> tempRole = new ArrayList<>();
            tempRole.add(roles.get(i*3));
            tempRole.add(roles.get(i*3+1));
            tempRole.add(roles.get(i*3+2));
//            str+=JSONObject.fromObject(roles.get(i*2));
//            tempRole.add(roles.get(i*2));
//            tempRole.add(roles.get(i*2+1));
//            maps.put(String.valueOf(players.get(i).getId()), JSONArray.fromObject(tempRole).toString());
            maps.put(String.valueOf(players.get(i).getId()), JSONArray.fromObject(tempRole).toString());
        }
        return maps;
    }

    public Player selectPlayerById (int id) {
        for (Player e:players) {
            if (e.getId()==id)
                return e;
        }
        return null;
    }

    public Role selectRoleByName (String name) {
        for (Role e:roles) {
            if (e.getName().equals(name))
                return e;
        }
        return null;
    }

    public List<Integer> randomProject () {
        //用于产生随机数来判断喜欢和讨厌的项目
        int sum=0;
        for(BigProject e:players.get(1).getBigProjects()) {
            sum+=e.getSmallProjects().size();
        }
        List<Integer> list=new ArrayList<>();
        while (list.size()!=6) {
            int i=1+(int)(Math.random()*sum);
            boolean existed=false;
            for(Integer e:list) {
                if(i==e) {
                    existed=true;
                    break;
                }
            }
            if(!existed) {
                list.add(i);
            }
        }
        return list;
    }

    public void randomEagerness (Player player,List<Integer> list) {
        for (int i=0;i<6;i++) {
            int num=list.get(i);
            for (BigProject e:player.getBigProjects()) {
                if(num>e.getSmallProjects().size())
                    num-=e.getSmallProjects().size();
                else {
                    switch (i) {
                        case 0:e.getSmallProjects().get(num-1).setEagerness(3);break;
                        case 1:
                        case 2:e.getSmallProjects().get(num-1).setEagerness(2);break;
                        default:e.getSmallProjects().get(num-1).setEagerness(0);break;
                    }
                    break;
                }
            }
        }
    }

    public boolean chooseRole (int id,String name) {
        chosenNum++;
        selectPlayerById(id).setRole(selectRoleByName(name));//玩家挑选自己的角色
        randomEagerness(selectPlayerById(id),randomProject());
        return chosenNum == playerNum;
    }

    public double averageMood () {
        double ave=0.0;
        for(Player e:players) {
            ave+=e.getMood();
        }
        ave/=players.size();
        return ave;
    }

    public void drawCard (Player player) {
        if(cards.size()<1) {
            cards.addAll(usedCards);
            Collections.shuffle(cards);
        }
        player.getCards().add(cards.get(0));
        cards.remove(0);
    }

    public void disCard (Player player) {
        usedCards.addAll(player.getCards());
        player.getCards().clear();
    }

    public SmallProject selectSmallProjectByName (Player player,String name) {
        for (BigProject E:player.getBigProjects()) {
            for (SmallProject e:E.getSmallProjects()) {
                if(e.getName().equals(name))
                    return e;
            }
        }
        return null;
    }

    public double mathRound (double a) {
        return (double)Math.round(a*10)/10;
    }

    public double caculateMatery (Player player,String name) {
        SmallProject smallProject=selectSmallProjectByName(player,name);
        double mastery=smallProject.getMastery();
        int eagerness=smallProject.getEagerness();
        //
        //这里先单独计算加减
        if (eagerness==0) mastery-=5.0;
        //
        //
        //这里计算兴趣是否会上升等级
        //
        //
        //这里计算百分比
        switch (eagerness) {
            case 0:mastery*=0.85;mastery=mathRound(mastery);break;
            case 2:mastery*=1.1;mastery=mathRound(mastery);break;
            case 3:mastery*=1.3;mastery=mathRound(mastery);break;
        }
        //
        return mastery;
    }

    public void caculateExpectation (Player player,SmallProject smallProject,int level,boolean isSuccess) {
        double expectation=smallProject.getExpectation();
        if (isSuccess) {
            switch (level) {
                case 0:expectation-=0.8;break;
                case 1:expectation-=1.5;break;
                case 2:expectation-=2.0;break;
                case 3:expectation-=3.1;break;
                case 4:expectation-=4.3;break;
                case 5:expectation-=5.0;break;
            }
            //其他影响期待值的技能
            if (expectation<0.0)
                expectation=0.0;
        }else {
            switch (level) {
                case 0:expectation+=0.8;break;
                case 1:expectation+=0.9;break;
                case 2:expectation+=1.2;break;
                case 3:expectation+=1.9;break;
                case 4:expectation+=2.6;break;
                case 5:expectation+=3.0;break;
            }
            //其他影响期待值的技能
            if(haveThisBuff(player,"不服输"))
                expectation+=0.5;
            if (expectation>5.0)
                expectation=5.0;
        }
        smallProject.setExpectation(expectation);

    }

    public void caculateMasteryUp (Player player,SmallProject smallProject,int level) {
        double mastery=smallProject.getMastery();
        switch (level) {
            case 0:mastery+=2.0;break;
            case 1:mastery+=3.0;break;
            case 2:mastery+=4.0;break;
            case 3:mastery+=5.0;break;
            case 4:mastery+=6.0;break;
            case 5:mastery+=7.0;break;
        }
        //其他影响熟练度的技能
        for (Buff buff:player.getBuffs()) {
            switch (buff.getName()) {
                case "熟练":mastery++;break;
                default:break;
            }
        }
        if (mastery>50.0)
            mastery=50.0;
        smallProject.setMastery(mastery);
    }

    public void studySuccess (Player player,String name,int level) {
        SmallProject smallProject=selectSmallProjectByName(player,name);
        //先计算心情值
        switch (level) {
            case 0:
                player.setMood(mathRound(player.getMood()+0.8*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;
            case 1:
                player.setMood(mathRound(player.getMood()+1.8*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;
            case 2:
                player.setMood(mathRound(player.getMood()+3.1*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;
            case 3:
                player.setMood(mathRound(player.getMood()+4.0*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;
            case 4:
                player.setMood(mathRound(player.getMood()+5.0*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;
            case 5:
                player.setMood(mathRound(player.getMood()+6.0*(1.0+smallProject.getExpectation()/10)));
                caculateExpectation(player,smallProject,level,true);
                break;

        }
        if(haveThisBuff(player,"愉悦"))
            player.setMood(mathRound(player.getMood()+0.3));
        if(haveThisBuff(player,"聚会余兴"))
            player.setMood(mathRound(player.getMood()+0.5));
        //后计算熟练度
        caculateMasteryUp(player,smallProject,level);
    }

    public void studyFail (Player player,String name,int level) {
        SmallProject smallProject=selectSmallProjectByName(player,name);
        caculateExpectation(player,smallProject,level,false);
    }

    public boolean studyCard (Player player1,Player player2,String name,int level) {
        switch (level) {
            //0~5表示F~A
            case 0:
                player1.setMood(mathRound(player1.getMood()-0.5));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=0) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            case 1:
                player1.setMood(mathRound(player1.getMood()-1.5));
                player2.setMood(mathRound(player2.getMood()-0.5));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=10) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            case 2:
                player1.setMood(mathRound(player1.getMood()-2.7));
                player2.setMood(mathRound(player2.getMood()-1.0));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=30) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            case 3:
                player1.setMood(mathRound(player1.getMood()-3.5));
                player2.setMood(mathRound(player2.getMood()-2.0));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=55) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            case 4:
                player1.setMood(mathRound(player1.getMood()-4.4));
                player2.setMood(mathRound(player2.getMood()-3.0));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=75) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            case 5:
                player1.setMood(mathRound(player1.getMood()-5.3));
                player2.setMood(mathRound(player2.getMood()-4.0));
                if(caculateMatery(player1,name)+caculateMatery(player2,name)>=90) {
                    studySuccess(player1,name,level);
                    studySuccess(player2,name,level);
                    return true;
                }else {
                    studyFail(player1,name,level);
                    studyFail(player2,name,level);
                    return false;
                }
            default:return false;
        }
    }

    public boolean haveThisBuff (Player player,String name) {
        for (Buff e:player.getBuffs()) {
            if (e.getName().equals(name))
                return true;
        }
        return false;
    }

}
