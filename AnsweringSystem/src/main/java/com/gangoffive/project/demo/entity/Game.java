package com.gangoffive.project.demo.entity;

import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

@Data
public class Game {
    private List<Role> roles;
    private List<Player> players=new ArrayList<>();
    private int playerNum;
    private int chosenNum=0;
    private int year=0;//0表示小学四年级，之后每+1表示增加一年

    public Game () {
        this.roles=new ArrayList<>();
        List<Skill> skills=new ArrayList<>();
        skills.add(new Skill("幕后黑手","在你使用【计策牌】后，你的心情值+0.5"));
        skills.add(new Skill("无谋之乐","在其他人弃牌阶段弃牌时，如果丢弃的牌中存在【计策牌】，那么你的心情值+0.3"));
        this.roles.add(new Role("张天舒",1,1,2,0,3,2,3,
                "园林大师","自然-园艺熟练度达到A",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("勇壮","在游戏开始时，你获得2点勇敢和1点坚韧"));
        skills.add(new Skill("重整旗鼓","出牌阶段，你可以丢弃3张牌，清除场上一名玩家的所有负面效果，并使其下回合抽牌数+1"));
        this.roles.add(new Role("兰文俊",1,3,3,3,0,3,2,
                "武术大师","体育-武术熟练度达到A",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("珍视","弃牌阶段，你可以选择你将要丢弃的牌中的一张牌进行收藏"));
        skills.add(new Skill("回忆","出牌阶段，取出你收藏的所有牌"));
        this.roles.add(new Role("吴童",1,2,3,3,2,0,1,
                "世界和平","在你的最后一个回合结束时场上没有人因为心情值降为0而出局",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("青梅竹马/铁哥们","游戏开始时，选择一名玩家与其绑定成“青梅竹马/铁哥们”的关系，在之后的游戏过程中你可以与其培养“关系值”"));
        skills.add(new Skill("纯爱战士/挚友英灵","当你与“青梅竹马/铁哥们”一起学习时，双方对该项目的感兴趣程度临时上升一级（但不会超过上限）。学习成功后，你与对方的关系值+1"));
        this.roles.add(new Role("徐东东",1,2,2,3,2,1,1,
                "心灵伴侣/情同手足","在游戏结算时，与你绑定了“青梅竹马/铁哥们”关系的玩家达成了人生目标并且你与对方的关系值不低于36",5.0,skills));
        skills=new ArrayList<>();
        skills.add(new Skill("再接再厉","在你制造出名品之后，你对所有项目的期待值增加2.0"));
        skills.add(new Skill("文思泉涌","在你的回合开始时，如果你的心情值达到了15及以上，从剩余的牌堆中抽取一张【名品牌】"));
        this.roles.add(new Role("陈树力",1,2,1,2,1,3,2,
                "实现梦想","在你的最后一个回合结束时你至少拥有一件名品",5.0,skills));
    }

    public void gameInit (int num,List<String> names,List<Integer> ids) {
        playerNum=num;
        for (int i=0;i<num;i++) {
            players.add(new Player(names.get(i),ids.get(i)));
        }
    }

    public Map<String,String> gameChooseRole () {
        Map<String,String> maps=new HashMap<>();
        Collections.shuffle(roles);
        for(int i=0;i<playerNum;i++) {
//            String str=new String();
            List<Role> tempRole = new ArrayList<>();
//            tempRole.add(roles.get(i*3));
//            tempRole.add(roles.get(i*3+1));
//            tempRole.add(roles.get(i*3+2));
//            str+=JSONObject.fromObject(roles.get(i*2));
            tempRole.add(roles.get(i*2));
            tempRole.add(roles.get(i*2+1));
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
}
